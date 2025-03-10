/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empresa;

/**
 *
 * @author bowis
 */
// Clase Desarrollador hereda de Empleado
public class Desarrollador extends Empleado {
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