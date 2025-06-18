public class BaseDeDatosExcepcion extends Exception {

  // Constructor simple con mensaje
  public BaseDeDatosExcepcion(String mensaje) {
    super(mensaje);
  }

  // Constructor con mensaje y causa
  public BaseDeDatosExcepcion(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}