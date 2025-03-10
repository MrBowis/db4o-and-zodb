package empresa;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.query.*;
import java.util.*;

/**
 *
 * @author bowis
 */

// Clase base Persona
class Persona {
    protected String nombre;
    protected int edad;
    protected String correo;

    public Persona(String nombre, int edad, String correo) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + nombre + ", " + edad + ", " + correo + ")";
    }
}

// Clase Empleado hereda de Persona
class Empleado extends Persona {
    protected double salario;
    protected String departamento;

    public Empleado(String nombre, int edad, String correo, double salario, String departamento) {
        super(nombre, edad, correo);
        this.salario = salario;
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return super.toString() + ", Salario: " + salario + ", Departamento: " + departamento;
    }
}

// Clase Gerente hereda de Empleado
class Gerente extends Empleado {
    private List<Empleado> subordinados;

    public Gerente(String nombre, int edad, String correo, double salario, String departamento) {
        super(nombre, edad, correo, salario, departamento);
        this.subordinados = new ArrayList<>();
    }

    public void agregarSubordinado(Empleado empleado) {
        if (!subordinados.contains(empleado)) {
            subordinados.add(empleado);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Subordinados: " + subordinados.stream().map(e -> e.nombre).toList();
    }
}

// Clase Desarrollador hereda de Empleado
class Desarrollador extends Empleado {
    private String lenguajeProgramacion;

    public Desarrollador(String nombre, int edad, String correo, double salario, String lenguajeProgramacion) {
        super(nombre, edad, correo, salario, "Desarrollo de Software");
        this.lenguajeProgramacion = lenguajeProgramacion;
    }

    @Override
    public String toString() {
        return super.toString() + ", Lenguaje: " + lenguajeProgramacion;
    }
}

// Clase Cliente hereda de Persona
class Cliente extends Persona {
    private String empresa;
    private List<String> proyectosContratados;

    public Cliente(String nombre, int edad, String correo, String empresa) {
        super(nombre, edad, correo);
        this.empresa = empresa;
        this.proyectosContratados = new ArrayList<>();
    }

    public void contratarProyecto(String proyecto) {
        proyectosContratados.add(proyecto);
    }

    @Override
    public String toString() {
        return super.toString() + ", Empresa: " + empresa + ", Proyectos: " + proyectosContratados;
    }
}

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