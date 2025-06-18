import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Alerta implements EntidadBd {
    private int idAlerta;
    private Producto producto;
    private java.util.Date fecha;
    private String estado;

    public Alerta(int idAlerta, Producto producto, java.util.Date fecha, String estado) {
        this.idAlerta = idAlerta;
        this.producto = producto;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO alerta (idAlerta, idProducto, fecha, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlerta);
            stmt.setInt(2, producto.getIdProducto());
            stmt.setDate(3, new Date(fecha.getTime()));
            stmt.setString(4, estado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar la alerta", e);
        }
    }


    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE alerta SET idProducto=?, fecha=?, estado=? WHERE idAlerta=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProducto());
            stmt.setDate(2, new Date(fecha.getTime()));
            stmt.setString(3, estado);
            stmt.setInt(4, idAlerta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar la alerta", e);
        }
    }


    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM alerta WHERE idAlerta=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlerta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar la alerta", e);
        }
    }


    public String toString() {
        return "Alerta{" +
                "idAlerta=" + idAlerta +
                ", producto=" + producto +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                '}';
    }
}