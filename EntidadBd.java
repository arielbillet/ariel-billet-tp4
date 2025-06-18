public interface EntidadBd {
    void guardar() throws BaseDeDatosExcepcion;
    void actualizar() throws BaseDeDatosExcepcion;
    void eliminar() throws BaseDeDatosExcepcion;
}