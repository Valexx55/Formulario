package edu.val.formulario;

public class Persona {

    /**
     * nombre,
     * edad,
     * sexo,
     * mayor_de_edad
     *
     *  atributos/propiedaes, características*/

    public String nombre;
    public int edad;
    public char sexo;//letra /'H' para hombres 'M' para mujeres
    public boolean mayor_edad;

    //vamos a generar el constructor de Persona
    //el método/ la función que se llama igual que la clase
    //y que sirve para crear personas nuevas (instancias/objetos)


    public Persona(String nombre, int edad, char sexo, boolean mayor_edad) {
        //this representa a la nueva persona que se está creando en este momento
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.mayor_edad = mayor_edad;
    }
}
