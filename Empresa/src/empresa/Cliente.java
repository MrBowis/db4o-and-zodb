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

// Clase Cliente hereda de Persona
public class Cliente extends Persona {
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