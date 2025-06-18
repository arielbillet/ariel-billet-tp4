import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Movimiento implements EntidadBd {
    private int idMovimiento;
    private String tipo;
    private Producto producto;
    private int cantidad;
    private java.util.Date fecha;

    public Movimiento(int idMovimiento, String tipo, Producto producto, int cantidad, java.util.Date fecha) {
        this.idMovimiento = idMovimiento;
        this.tipo = tipo;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO movimiento (tipo, cantidad, fecha, id_producto) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, tipo);
            stmt.setInt(2, cantidad);
            stmt.setDate(3, new Date(fecha.getTime()));
            stmt.setInt(4, producto.getIdProducto());
            stmt.executeUpdate();

            if (tipo.equalsIgnoreCase("ingreso")) {
                producto.aumentarStock(cantidad);
            } else if (tipo.equalsIgnoreCase("egreso")) {
                producto.disminuirStock(cantidad);
            }

            producto.actualizar();

        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar el movimiento", e);
        }
    }

    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE movimiento SET tipo=?, id_producto=?, cantidad=?, fecha=? WHERE id_movimiento=?";
        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, tipo);
            stmt.setInt(2, producto.getIdProducto());
            stmt.setInt(3, cantidad);
            stmt.setDate(4, new Date(fecha.getTime()));
            stmt.setInt(5, idMovimiento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar el movimiento", e);
        }
    }

    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM movimiento WHERE id_movimiento=?";
        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, idMovimiento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar el movimiento", e);
        }
    }

    public String toString() {
        return "Movimiento{" +
                "idMovimiento=" + idMovimiento +
                ", tipo='" + tipo + '\'' +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                '}';
    }
}