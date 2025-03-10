/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empresa;

/**
 *
 * @author bowis
 */
// Clase Empleado hereda de Persona
public class Empleado extends Persona {
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