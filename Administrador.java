import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Administrador implements EntidadBd {
    private String nombre;
    private String usuario;
    private String contrasenia;


    public Administrador(String nombre, String usuario, String contrasenia) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }


    public boolean login(String usuario, String contrasenia) {
        return this.usuario.equals(usuario) && this.contrasenia.equals(contrasenia);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }



    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO administrador (nombre, usuario, contrasenia) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, usuario);
            stmt.setString(3, contrasenia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar administrador", e);
        }
    }


    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE administrador SET nombre=?, contrasenia=? WHERE usuario=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, contrasenia);
            stmt.setString(3, usuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar administrador", e);
        }
    }


    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM administrador WHERE usuario=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar administrador", e);
        }
    }


    public String toString() {
        return "Administrador{" +
                "nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}