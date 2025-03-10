from ZODB import FileStorage, DB
import transaction
import persistent

# Clase base Persona
class Persona(persistent.Persistent):
    def __init__(self, nombre, edad, correo):
        self.nombre = nombre
        self.edad = edad
        self.correo = correo
    
    def __str__(self):
        return f"{self.__class__.__name__}({self.nombre}, {self.edad}, {self.correo})"

# Clase Empleado hereda de Persona
class Empleado(Persona):
    def __init__(self, nombre, edad, correo, salario, departamento):
        super().__init__(nombre, edad, correo)
        self.salario = salario
        self.departamento = departamento
    
    def __str__(self):
        return f"{super().__str__()}, Salario: {self.salario}, Departamento: {self.departamento}"

# Clase Gerente hereda de Empleado
class Gerente(Empleado):
    def __init__(self, nombre, edad, correo, salario, departamento):
        super().__init__(nombre, edad, correo, salario, departamento)
        self.subordinados = []
    
    def agregar_subordinado(self, empleado):
        if isinstance(empleado, Empleado) and empleado not in self.subordinados:
            self.subordinados.append(empleado)
            transaction.commit()
    
    def __str__(self):
        return f"{super().__str__()}, Subordinados: {[e.nombre for e in self.subordinados]}"

# Clase Desarrollador hereda de Empleado
class Desarrollador(Empleado):
    def __init__(self, nombre, edad, correo, salario, lenguaje_programacion):
        super().__init__(nombre, edad, correo, salario, "Desarrollo de Software")
        self.lenguaje_programacion = lenguaje_programacion

    def desarrollar(self, proyecto):
        print(f"{self.nombre} está desarrollando el proyecto {proyecto}")
    
    def __str__(self):
        return f"{super().__str__()}, Lenguaje: {self.lenguaje_programacion}"

# Clase Cliente hereda de Persona
class Cliente(Persona):
    def __init__(self, nombre, edad, correo):
        super().__init__(nombre, edad, correo)
        self.proyectos_contratados = []
    
    def contratar_proyecto(self, proyecto):
        self.proyectos_contratados.append(proyecto)
        transaction.commit()
    
    def __str__(self):
        return f"{super().__str__()}, Proyectos: {self.proyectos_contratados}"

# Clase Empresa maneja empleados y clientes con ZODB
class Empresa:
    def __init__(self, db_path='empresa_software.fs'):
        self.storage = FileStorage.FileStorage(db_path)
        self.db = DB(self.storage)
        self.conexion = self.db.open()
        self.raiz = self.conexion.root()
        
        if not hasattr(self.raiz, 'empleados'):
            self.raiz.empleados = {}
        if not hasattr(self.raiz, 'clientes'):
            self.raiz.clientes = {}
        transaction.commit()
    
    def agregar_empleado(self, empleado):
        self.raiz.empleados[empleado.nombre] = empleado
        transaction.commit()
    
    def agregar_cliente(self, cliente):
        self.raiz.clientes[cliente.nombre] = cliente
        transaction.commit()
    
    def listar_empleados(self):
        for empleado in self.raiz.empleados.values():
            print(empleado)
    
    def listar_clientes(self):
        for cliente in self.raiz.clientes.values():
            print(cliente)
    
    def cerrar(self):
        self.conexion.close()
        self.db.close()

# Ejemplo de uso
empresa = Empresa()

# Crear empleados
gerente = Gerente("Carlos", 40, "carlos@empresa.com", 7000, "Dirección de Tecnología")
dev1 = Desarrollador("Ana", 28, "ana@empresa.com", 4000, "Python")
dev2 = Desarrollador("Pedro", 30, "pedro@empresa.com", 4500, "JavaScript")
empresa.agregar_empleado(gerente)
empresa.agregar_empleado(dev1)
empresa.agregar_empleado(dev2)

gerente.agregar_subordinado(dev1)
gerente.agregar_subordinado(dev2)

# Crear clientes
cliente1 = Cliente("Empresa XYZ", 45, "contacto@xyz.com")
cliente1.contratar_proyecto("Desarrollo de plataforma web")
empresa.agregar_cliente(cliente1)

# Mostrar datos
empresa.listar_empleados()
empresa.listar_clientes()

empresa.cerrar()