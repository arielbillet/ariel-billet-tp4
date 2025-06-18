import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Reporte implements EntidadBd {
    private int idReporte;
    private String tipo;
    private java.util.Date fecha;
    private Administrador admin;

    public Reporte(int idReporte, String tipo, java.util.Date fecha, Administrador admin) {
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.fecha = fecha;
        this.admin = admin;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }


    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO reporte (idReporte, tipo, fecha, usuarioAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            stmt.setString(2, tipo);
            stmt.setDate(3, new Date(fecha.getTime()));
            stmt.setString(4, admin.getUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar el reporte", e);
        }
    }


    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE reporte SET tipo=?, fecha=?, usuarioAdmin=? WHERE idReporte=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setDate(2, new Date(fecha.getTime()));
            stmt.setString(3, admin.getUsuario());
            stmt.setInt(4, idReporte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar el reporte", e);
        }
    }


    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM reporte WHERE idReporte=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar el reporte", e);
        }
    }


    public String toString() {
        return "Reporte{" +
                "idReporte=" + idReporte +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                ", admin=" + admin +
                '}';
    }
}