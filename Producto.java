import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Producto implements EntidadBd {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private int stock;
    private int stockMinimo;
    private double precio;

    public Producto(int idProducto, String nombre, String descripcion, double precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = 5;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean hayStock(int cantidadSolicitada) {
        return stock >= cantidadSolicitada;
    }

    public boolean necesitaReposicion() {
        return stock < stockMinimo;
    }

    public void disminuirStock(int cantidad) {
        if (hayStock(cantidad)) {
            stock -= cantidad;
        }
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }

    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO producto (id_producto, nombre, descripcion, precio, stock, stock_minimo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);
            stmt.setDouble(4, precio);
            stmt.setInt(5, stock);
            stmt.setInt(6, stockMinimo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar el producto: " + e.getMessage(), e);
        }
    }
    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE producto SET nombre=?, descripcion=?, precio=?, stock=?, stock_minimo=? WHERE id_producto=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setInt(4, stock);
            stmt.setInt(5, stockMinimo);
            stmt.setInt(6, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar el producto", e);
        }
    }

    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM producto WHERE id_producto=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new BaseDeDatosExcepcion("No se encontró el producto con ID: " + idProducto);
            }
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar el producto: " + e.getMessage(), e);
        }
    }

    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                ", precio=" + precio +
                '}';
    }
}