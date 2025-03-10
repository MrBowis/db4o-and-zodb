package empresa;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.query.*;
import java.util.*;

/**
 *
 * @author bowis
 */

public class Empresa {
    
    private ObjectContainer db;

    public Empresa(String dbFile) {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Empresa.class).cascadeOnUpdate(true);
        db = Db4oEmbedded.openFile(config, dbFile);
    }

    public void agregarEmpleado(Empleado empleado) {
        db.store(empleado);
        db.commit();
    }

    public void agregarCliente(Cliente cliente) {
        db.store(cliente);
        db.commit();
    }

    public void listarEmpleados() {
        Query query = db.query();
        query.constrain(Empleado.class);
        List<Empleado> empleados = query.execute();
        empleados.forEach(System.out::println);
    }

    public void listarClientes() {
        Query query = db.query();
        query.constrain(Cliente.class);
        List<Cliente> clientes = query.execute();
        clientes.forEach(System.out::println);
    }

    public void cerrar() {
        db.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Empresa empresa = new Empresa("empresa_software.db4o");

        // Crear empleados
        Gerente gerente = new Gerente("Carlos", 40, "carlos@empresa.com", 7000, "Dirección de Tecnología");
        Desarrollador dev1 = new Desarrollador("Ana", 28, "ana@empresa.com", 4000, "Python");
        Desarrollador dev2 = new Desarrollador("Pedro", 30, "pedro@empresa.com", 4500, "JavaScript");

        empresa.agregarEmpleado(gerente);
        empresa.agregarEmpleado(dev1);
        empresa.agregarEmpleado(dev2);

        gerente.agregarSubordinado(dev1);
        gerente.agregarSubordinado(dev2);

        // Crear clientes
        Cliente cliente1 = new Cliente("Empresa XYZ", 45, "contacto@xyz.com", "XYZ Solutions");
        cliente1.contratarProyecto("Desarrollo de plataforma web");
        empresa.agregarCliente(cliente1);

        // Mostrar datos
        System.out.println("Empleados:");
        empresa.listarEmpleados();

        System.out.println("Clientes:");
        empresa.listarClientes();

        empresa.cerrar();
    }
}