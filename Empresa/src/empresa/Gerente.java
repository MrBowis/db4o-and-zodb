/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empresa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bowis
 */
// Clase Gerente hereda de Empleado
public class Gerente extends Empleado {
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