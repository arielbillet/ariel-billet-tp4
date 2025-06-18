import java.util.*;
import java.sql.*;
import java.util.Date;

public class MenuPrincipal {
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Administrador> administradores;
    private List<Movimiento> movimientos;
    private List<Reporte> reportes;
    private List<Alerta> alertas;
    private Scanner scanner;

    public MenuPrincipal() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        administradores = new ArrayList<>();
        movimientos = new ArrayList<>();
        reportes = new ArrayList<>();
        alertas = new ArrayList<>();
        scanner = new Scanner(System.in);

        cargarProductosDesdeBD();
        cargarClientesDesdeBD();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor ingrese un número válido.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 0:
                    agregarProducto();
                    break;
                case 1:
                    eliminarProducto();
                    break;
                case 2:
                    registrarMovimiento();
                    break;
                case 3:
                    generarReporte();
                    break;
                case 4:
                    verAlertas();
                    break;
                case 5:
                    guardarCliente();
                    break;
                case 6:
                    eliminarCliente();
                    break;
                case 7:
                    salir();
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }

    private void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("0. Agregar producto");
        System.out.println("1. Eliminar producto");
        System.out.println("2. Registrar movimiento de stock");
        System.out.println("3. Generar reporte");
        System.out.println("4. Ver alertas");
        System.out.println("5. Registrar nuevo cliente");
        System.out.println("6. Eliminar cliente");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void agregarProducto() {
        System.out.print("Ingrese ID del producto: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese stock: ");
        int stock = scanner.nextInt();

        Producto producto = new Producto(id, nombre, descripcion, precio, stock);
        try {
            producto.guardar();
            productos.add(producto);
            System.out.println("Producto agregado exitosamente (guardado en BD).");
        } catch (BaseDeDatosExcepcion e) {
            System.out.println("Error al guardar el producto en la base de datos: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        System.out.println("\n--- Productos cargados ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para eliminar.");
            return;
        }

        for (Producto p : productos) {
            System.out.println("ID: " + p.getIdProducto() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
        }

        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();

        Producto productoAEliminar = null;
        for (Producto p : productos) {
            if (p.getIdProducto() == id) {
                productoAEliminar = p;
                break;
            }
        }

        if (productoAEliminar != null) {
            try {
                productoAEliminar.eliminar();
                productos.remove(productoAEliminar);
                System.out.println("Producto eliminado exitosamente (de BD y memoria).");
            } catch (BaseDeDatosExcepcion e) {
                System.out.println("Error al eliminar el producto de la base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void registrarMovimiento() {
        System.out.print("Ingrese ID del producto: ");
        int idProducto = scanner.nextInt();
        Producto producto = buscarProductoPorId(idProducto);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.print("Ingrese tipo de movimiento (ingreso/egreso): ");
        String tipo = scanner.next();
        System.out.print("Ingrese cantidad: ");
        int cantidad = scanner.nextInt();

        if (!tipo.equalsIgnoreCase("ingreso") && !tipo.equalsIgnoreCase("egreso")) {
            System.out.println("Tipo de movimiento no válido.");
            return;
        }

        Movimiento movimiento = new Movimiento(movimientos.size() + 1, tipo, producto, cantidad, new Date());

        try {
            movimiento.guardar();
            movimientos.add(movimiento);
            System.out.println("Movimiento registrado y guardado en BD.");
            System.out.println("Stock actualizado del producto ID " + producto.getIdProducto() + ": " + producto.getStock());
        } catch (BaseDeDatosExcepcion e) {
            System.out.println("Error al guardar el movimiento en la base de datos: " + e.getMessage());
        }

        if (producto.getStock() < 5) {
            crearAlerta(producto);
        }
    }

    private void generarReporte() {
        System.out.print("Ingrese tipo de reporte: ");
        String tipo = scanner.nextLine();
        Reporte reporte = new Reporte(reportes.size() + 1, tipo, new Date(), null);
        reportes.add(reporte);
        System.out.println("Reporte generado exitosamente.");
    }

    private void verAlertas() {
        System.out.println("\n--- Alertas Actuales ---");
        for (Alerta alerta : alertas) {
            System.out.println(alerta);
        }
    }

    private Producto buscarProductoPorId(int id) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == id) {
                return producto;
            }
        }
        return null;
    }

    private void crearAlerta(Producto producto) {
        Alerta alerta = new Alerta(alertas.size() + 1, producto, new Date(), "activa");
        alertas.add(alerta);
        System.out.println("Alerta generada por bajo stock.");
    }

    private void salir() {
        System.out.println("Gracias por utilizar el sistema de la Ferretería Los Leos. ¡Hasta pronto!");
    }

    private void guardarCliente() {
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del cliente: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, apellido, dni, telefono, direccion);

        try {
            cliente.guardar();
            clientes.add(cliente);
            System.out.println("Cliente guardado exitosamente en la base de datos.");
        } catch (BaseDeDatosExcepcion e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        System.out.print("Ingrese DNI del cliente a eliminar: ");
        String dni = scanner.nextLine().trim();  // ← evita espacios

        if (dni.isEmpty()) {
            System.out.println("DNI inválido. No se puede eliminar.");
            return;
        }

        try {
            Cliente cliente = new Cliente(null, null, dni, null, null);
            cliente.eliminar();
            System.out.println("Cliente eliminado exitosamente de la base de datos.");
        } catch (BaseDeDatosExcepcion e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
    private void cargarClientesDesdeBD() {
        clientes.clear();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cliente");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                clientes.add(new Cliente(nombre, apellido, dni, telefono, direccion));
            }

        } catch (Exception e) {
            System.out.println("Error al cargar clientes: " + e.getMessage());
        }
    }
    private void cargarProductosDesdeBD() {
        productos.clear();
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM producto");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                int stockMinimo = rs.getInt("stock_minimo");
                productos.add(new Producto(id, nombre, descripcion, precio, stock));
            }

        } catch (Exception e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }
}