package edu.val.formulario;

public class Persona {

    /**
     * nombre,
     * edad,
     * sexo,
     * mayor_de_edad
     *
     *  atributos/propiedaes, características*/

    private String nombre;
    private int edad;
    private char sexo;//letra /'H' para hombres 'M' para mujeres
    private boolean mayor_edad;

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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isMayor_edad() {
        return mayor_edad;
    }

    public void setMayor_edad(boolean mayor_edad) {
        this.mayor_edad = mayor_edad;
    }
}
