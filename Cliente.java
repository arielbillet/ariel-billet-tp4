import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cliente implements EntidadBd {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;

    public Cliente(String nombre, String apellido, String dni, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    // Guardar en BD
    public void guardar() throws BaseDeDatosExcepcion {
        String sql = "INSERT INTO cliente (nombre, apellido, dni, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, dni);
            stmt.setString(4, telefono);
            stmt.setString(5, direccion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al guardar el cliente", e);
        }
    }

    public void actualizar() throws BaseDeDatosExcepcion {
        String sql = "UPDATE cliente SET nombre=?, apellido=?, telefono=?, direccion=? WHERE dni=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);
            stmt.setString(5, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al actualizar el cliente", e);
        }
    }

    public void eliminar() throws BaseDeDatosExcepcion {
        String sql = "DELETE FROM cliente WHERE dni = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDeDatosExcepcion("Error al eliminar el cliente", e);
        }
    }

    public String toString() {
        return "Cliente: " + nombre + " " + apellido +
                ", DNI: " + dni +
                ", Teléfono: " + telefono +
                ", Dirección: " + direccion;
    }
}