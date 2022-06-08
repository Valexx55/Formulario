package edu.val.formulario;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
//YA SE MEDIANTE PARCEL O SEERIALIZABLE, LO QUE ESTOY PERMITIENDO
//ES QUE UN OBJETO PERSONA PUEDA GUARDARSE O LEERSE EN UN FICHERO
//SERALIZAR -> PASAR UN OBJETO A BYTES PARA SU ALMACENACENAMIENTO O TRANSMISIÓN

//public class Persona implements Serializable {
public class Persona implements Parcelable { //tipo recomendado por Android

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
    //NOTA: En el caso de que tenga atributos que sean clases "no estándar", necesito hacerlas también serializables
    //private Coche coche; //tendría que hacer el COCHE serlizable o parcelabe para poder seguir escribiendo personas en el intent

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

    @Override
    public String toString() //sobreescribimos el método heredado de Object - POLIMORFISMO
    {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", sexo=" + sexo +
                ", mayor_edad=" + mayor_edad +
                '}';
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    //MÉTODOS GENERADOS AUTOMÁTICAMENTE POR EL ASISTENTE//
    //PARA HACER QUE PERSONA SEA PARCELABLE//
    ////////////////////////////////
    ////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    ////ESTE MÉTODO SE INVOCA CUANDO QUIERO ESCRIBIR UNA PERSONA A UN PARCEL (FICHERO)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(edad);
        parcel.writeInt((int) sexo);
        parcel.writeByte((byte) (mayor_edad ? 1 : 0));
    }

    //ESTE MÉTODO SE INVOCA CUANDO QUIERO CREAR UNA PERSONA DESDE UN PARCEL (FICHERO)
    protected Persona(Parcel in) {
        nombre = in.readString();
        edad = in.readInt();
        sexo = (char) in.readInt();
        mayor_edad = in.readByte() != 0;
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };
}
