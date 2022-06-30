package edu.val.formulario;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.actions.NoteIntents;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    //EN UN CLASE SIEMPRE TENGO ATRIBUTOS Y MÉTODOS
    //LOS ATRIBUTOS SON LAS CARACTETRISTICAS O PROPIEDADES QUE COMPONEN LA CLASE
    //LAS FUNCIONES O MÉTODOS HABLAN DE LO QUE SE PUEDE HACER/LO QUE HACE ESA CLASE

    //MENÚ: VAMOS A HACER UN MENÚ CON DOS OPCIONES: BORRAR EL FORMULARIO Y OTRA PARA SALIR

    private EditText editTextNombre;
    private EditText editTextEdad;
    private RadioButton radioButtonHombre;
    private RadioButton radioButtonMujer;
    private CheckBox checkBoxMayorEdad;
    private ImageView imagen;

    private boolean aceptarTocado;
    private boolean formularioValido;
    private int nveces_hacia_atras;

    private static final int COD_PETICION_COLOR = 222;

    private ActivityResultLauncher<Intent> activityResultLauncher;//uso este objeto para lanzar la subactivdad y recibir el resultado


    //INICIALIZAR LAS VARIABLES / OBJETOS
    //NUMEROS - INICIAN A CERO
    //BOOLEAN - INICIAN A FALSE
    // STRING - NULL A CADENA VACÍA
    // OBJETOS (EDITTEXT, RADIOBUTTON, FILE) - A NULL





    //para considerar el formulario válido, vamos a controlar:
        //que el nombre sea distinto de null y que al menos tiene algúna letra de la A a la Z o espacios
        //que la edad esté formada por digitos (0 al 9)

    //TODO hacer un método que se llame validarFormulario, que sea invocado cuando el usuario
    //toca el botón de aceptar y que me diga si el formulario es válido o no con verdadero o falso


    /**
     * EL PROBLEMA AÑADIDO DE LA ESTRATEGIA SENSIBLE
     * ESQUE LA INFORMACIÓN NO VISUAL ASOCIADA A LA ACTIVIDAD
     * SE PIERDE AL GIRAR EL DISPOSITIVO (AL RECREARSE LA ACTIVIDAD)
     *
     * TENEMOS QUE BUSCAR UNA MANERA DE GUARDAR LA INFORMACIÓN QUE ME
     * INTERESE ENTRE CAMBIOS DE ORIENTACIÓN
     *
     *
     */

    //TODO definir un proceso de validación para los campos de nombre y edad
    //quiero guardar si los datos del formulario son válidos o no

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //crearAlarma ();
        //selectContact ();
        //createNote("ASUNTO", "Duda de Android");
        setContentView(R.layout.activity_main);
        Log.d("ETIQUETA_LOG", "entrando en onCreate()");
        //this precede al nombre de una función o una propiedad de la clase
        //this es la nueva pantalla / un objeto de la propia clase / UNA VARIABLE de MainActivity
        this.iniciarActividad();
        //dibujamos la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState!=null)

        {
            //la actividad se ha recreado y se ha guardado información
            Log.d("ETIQUETA_LOG", "EN EL SACO HAY INFORMACIÓN (viene de recrearse)");
            this.formularioValido = savedInstanceState.getBoolean("VALIDO");

        } else

        {
            Log.d("ETIQUETA_LOG", "EN EL SACO NO HAY INFORMACIÓN (actividad nueva o no se ha guardado nada)");
            //la actividad se crea por primera vez o no se ha guardado información

        }
        Log.d("ETIQUETA_LOG", "ES VALIDO en Oncreate = "+ formularioValido);

        //dibujamos el menú contextual sobre el icono
        this.imagen = findViewById(R.id.imageView);
        registerForContextMenu(imagen);

    }


    //este método se invoca para dibujar el menú sobre el icono
    //cuando lo deje pulsado: menu contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //vamos a añadir la opción de borrar la imagen
        menu.add(Menu.NONE, 3, 3, "BORRAR");
    }

    //este método es invocado al selecionar una opción del menú contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Log.d("ETIQUETA_LOG", "Opción tocada = " + item.getItemId());
        this.imagen.setVisibility(View.INVISIBLE);
        return super.onContextItemSelected(item);
    }

    private void crearAlarma ()
    {

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "DESPIERTA")
                .putExtra(AlarmClock.EXTRA_HOUR, 21)
                .putExtra(AlarmClock.EXTRA_MINUTES, 43);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d("ETIQUETA_LOG", "Alarma generada");
            startActivity(intent);
        }
    }

    static final int REQUEST_SELECT_CONTACT = 1;

    public void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Log.d("ETIQUETA_LOG", "URI " + contactUri.toString());
            // Do something with the selected contact at contactUri
        //...
        }
    }*/


        public void createNote(String subject, String text) {
        Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
                .putExtra(NoteIntents.EXTRA_NAME, subject)
                .putExtra(NoteIntents.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d("ETIQUETA_LOG", "Estoy pidiendo crear una nota");
            startActivity(intent);
        } else {
            Log.d("ETIQUETA_LOG", "NO puedo crear una nota");
        }
    }


    //este método es obligatorio para dibujar el menú de la barrita
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();//este método me da el objeto para "inflar" dibujar el menú
        menuInflater.inflate(R.menu.menu_formulario, menu);//menu es como si fuera el padre menu_formulario nuestro hijo
        return true;//super.onCreateOptionsMenu(menu);
    }

    //este método es necesiario
    //para escuchar las acciones sobre el menú
    //al tocar una opción del menú, se realiza un callback a este método
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            Log.d("ETIQUETA_LOG", "OPCIÓN MENÚ TOCADA");
            int id_item = item.getItemId();
            switch (id_item)
            {
                case R.id.opcionlimpiar:
                    //el usuario le ha dado a limpiar
                    limpiarFormulario();
                    break;
                case R.id.opcionsalir:
                    //el usuario le ha dado a salir
                    salir();
                    break;
                case android.R.id.home:
                    Log.d("ETIQUETA_LOG", "Ha tocado la fecha de ir para atrás");
                    salir();
            }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarFormulario()
    {
        this.editTextEdad.setText("");
        this.editTextNombre.setText("");
        this.radioButtonMujer.setChecked(false);
        this.radioButtonHombre.setChecked(false);
        this.checkBoxMayorEdad.setChecked(false);

        Log.d("ETIQUETA_LOG", "Formulario limpio...");
        Toast toast = Toast.makeText(this, "Formulario LIMPIO", Toast.LENGTH_LONG);//preparo
        toast.show();//muestro

    }

    private void salir ()
    {
        Log.d("ETIQUETA_LOG", "Saliendo...");
        //this.finish();
        //vamos a preguntar eso...quieres salir de verdad???
        //vamos a dibujar una vetana emergente
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("AVISO");
        alertDialog.setMessage("¿Desea salir?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialogInterface, i) -> {
            dialogInterface.cancel();//no quiere salir, cierro el mensaje
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SÍ", (dialogInterface, i) -> {
            this.finish();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NO SÉ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //MainActivity.this.finish(); //esto sería salir de la forma antigua/ sin lambdas sin funciones anónimas
                dialogInterface.cancel();//no quiere salir, cierro el mensaje
            }
        });

        alertDialog.show();
    }



    private void iniciarActividad()
    {
        //cargar las vistas/ los controles
        //con this, me estoy refiriendo a la propia pantalla que estoy visualizando en este momento
        this.editTextNombre = findViewById(R.id.editTextNombre);
        this.editTextEdad = findViewById(R.id.editTextEdad);
        this.radioButtonHombre = findViewById(R.id.radioButtonHombre);
        this.radioButtonMujer = findViewById(R.id.radioButtonMujer);
        this.checkBoxMayorEdad = findViewById(R.id.checkBoxMayorEdad);

        this.nveces_hacia_atras=0;
        //cuando cambie el foco sobre este elemento, llamas al método onFocusChange de esta clase
        this.editTextNombre.setOnFocusChangeListener(this);

        //1º programar el objeto que gestiona la subactividad

        this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //aquí recibo la respuesta
                    if (result.getResultCode() == RESULT_OK)
                    {
                        //obtengo el intent
                        Intent intent_resultado = result.getData();
                        int color_seleccioando = intent_resultado.getIntExtra("COLOR_ELEGIDO", R.color.white);
                        findViewById(R.id.botonColorFavorito).setBackgroundColor(color_seleccioando);
                    }
                });

    }

    /**
     * Validamos la edad del formulario
     *
     * @return false si la edad es cadena vacía true en caso contrario
     */
    private boolean esEdadValida ()
    {
        boolean edad_valida = false;

            String edad = this.editTextEdad.getText().toString();
            //con esta expresión regular, estoy diciendo, que la edad es válida
            //si tiene caracteres del 0 al 9 (dígitos) y al menos uno (+)
            //edad_valida = (edad != null) && edad.matches("[0-9]+");//EXPRESIÓN REGULARES - VALIDAR
            if (edad.equals(""))
            {
                edad_valida = false;
            } else {
                edad_valida = true;
            }

            // edad_valida = !edad.equals("");

        return edad_valida;
    }

    /**
     * Validamos el nombre del formulario
     *
     * @return false si el nombre es cadena vacía true en caso contrario
     */
    private boolean esNombreValido ()
    {
        boolean nombre_valido = false;

            String nombre = this.editTextNombre.getText().toString();
            //con esta expresión regular, estoy diciendo, que el nombre es válidos
            //si tiene caracteres del alfabéticos de la a la z mayusculas o minusuclas y espacios y al menos uno (+)
            nombre_valido = (nombre!=null) && nombre.matches("[a-zA-Z\\s]+");
            /*if (nombre.equals(""))
            {
                nombre_valido = false;
            } else {
                nombre_valido = true;
            }*/

           // nombre_valido = !nombre.equals("");

        return nombre_valido;
    }

    private boolean esFormularioValido ()
    {
        boolean formulario_valido = false;//LOS BOOLEAN SE INICIALIZAN A FALSE POR DEFECTO
        boolean edad_valida = false;
        boolean nombre_valido = false;

            edad_valida = esEdadValida();
            nombre_valido = esNombreValido();

            if (!edad_valida) //edad_valida == false
            {
                this.editTextEdad.setError("Introduce un número correcto");
                //this.editTextEdad.setError("Introduce un número correcto", getDrawable(R.drawable.ic_baseline_dangerous_24));
            }

            if (!nombre_valido)
            {
                this.editTextNombre.setError("Introduce un nombre correcto");
            }

            formulario_valido = edad_valida && nombre_valido;

           /*if ((edad_valida==true) && (nombre_valido==true))
           {
                formulario_valido = true;
           } else
           {
               formulario_valido = false;
           }*/



        return formulario_valido;
    }

    /**
     * este método se invoca al darle a Aceptar al formulario
     * @param view
     */
    public void mostrarDatosFormulario(View view) {
        Log.d("ETIQUETA_LOG", "Nombre intro = " + this.editTextNombre.getText().toString());
        Log.d("ETIQUETA_LOG", "Edad intro = " + this.editTextEdad.getText().toString());
        Log.d("ETIQUETA_LOG", "RadioButton Hombre = " + this.radioButtonHombre.isChecked());
        Log.d("ETIQUETA_LOG", "RadioButton Mujer = " + this.radioButtonMujer.isChecked());
        Log.d("ETIQUETA_LOG", "CheckBox Mayor 18 marcado = " + this.checkBoxMayorEdad.isChecked());
        this.aceptarTocado = true;
        //Log.d("ETIQUETA_LOG", "ACEPTAR TOCADO en mostrarDatosFormulario = "+ aceptarTocado);
        formularioValido = this.esFormularioValido();
        Log.d("ETIQUETA_LOG", "ES VALIDO despues de Aceptar = "+ formularioValido);
        //si el formulario es válido, vamos a crear a una persona con los datos introducidos
        Persona persona = null;
        //¿cómo creo una nueva persona --> a través del constructor de la clase
        //cómo llamo al constructor --> con la palabra reservada new
        //TODO crear una Persona a partir de los datos del formulario
        //si se supera la validación
        if (formularioValido)//formularioValido==true
        {
            String nombre_persona = this.editTextNombre.getText().toString();
            String edad_persona = this.editTextEdad.getText().toString();
            int edad_numerica = Integer.parseInt(edad_persona);
            char sexo;
            if (this.radioButtonHombre.isChecked())
            {
                sexo = 'H';
            } else {
                sexo = 'M';
            }
            boolean mayor_edad;
            if (this.checkBoxMayorEdad.isChecked())//==true
            {
                mayor_edad = true;
            } else {
                mayor_edad = false;
            }
            persona = new Persona(nombre_persona, edad_numerica, sexo, mayor_edad);
            Log.d("ETIQUETA_LOG", "Nombre de la persona = "+ persona.getNombre());
            Persona persona2 = new Persona("Macarena", edad_numerica, sexo, mayor_edad);
            Log.d("ETIQUETA_LOG", "Nombre de la persona = "+ persona2.getNombre());
            Intent intent_bienvenida = new Intent(this, BienvenidaActivity.class);
            //Class claseBienvenida = BienvenidaActivity.class;
            //CADA CLASE, crea automáticamente un objeto Class que describe la propia clase
            //el objeto class se usa para refexión: saber qué atriubtos, métodos, contenido de una clase
            //se crea automáticamente para cada clase
            //claseBienvenida.getm
            //persona.getNombre();
            intent_bienvenida.putExtra("NOMBRE", persona.getNombre());
            intent_bienvenida.putExtra("PERSONA", persona);//hay que convertirlo a serializable o parcelable
            //podría meter una lista de personas al ser Parcelable
            List<Persona> lista_p = List.of(persona, persona);
            intent_bienvenida.putExtra("PERSONAS", lista_p.toArray());
            //
            startActivity(intent_bienvenida);
        }

        //persona = new Persona("VALE", 38, 'H', true);

    }


    //este método es invocado antes de destruirse la actividad
    //si lo sobreescribo, me doy la opción de guardar datos en el bundle
    //ese mismo bundle, lo recibe el metodo onCreate después
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //outState es como un saco donde yo puedo guadar datos
        //para guardar el boolean
        outState.putBoolean("VALIDO", this.formularioValido);
        super.onSaveInstanceState(outState);//aquí, dentro del método del padre Android guarda la información de las VISTAS
    }

    //si yo lo sobreescribo, es que quiero modificarlo
    @Override //significa que este método ya existe en el padre
    protected void onStart() {
        Log.d("ETIQUETA_LOG", "onStart ()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("ETIQUETA_LOG", "onResume");
        super.onResume();
        //escribe lo que había guardado
        //actulizar mi pos gps
        //la activdad es visible en primer plano / o vuelve a serlo
    }

    @Override
    protected void onPause() {
        Log.d("ETIQUETA_LOG", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("ETIQUETA_LOG", "onStop");
        super.onStop();
        //guardar lo que yo he escrito
        //librear los recursos hardware de la cámara
    }

    @Override
    protected void onDestroy() {
        Log.d("ETIQUETA_LOG", "onDestroy");
        super.onDestroy();
    }

     //TODO haced que la aplicacción se cierre cuando el usuario
    //le dé 3 veces al botón de ir hacia atrás
    //este método recibe un callback cuando el botón de ir hacia atrás es
    //pulsado por el usuario
    @Override
    public void onBackPressed() {
        Log.d("ETIQUETA_LOG", "onBackPressed");
        //this.nveces_hacia_atras = this.nveces_hacia_atras+1;
        this.nveces_hacia_atras++;
        if (this.nveces_hacia_atras==3)
        {
            //super.onBackPressed();
            this.finish();
        }
        //super.onBackPressed();
    }

    public void seleccionarColor(View view) {
        Log.d("ETIQUETA_LOG", "Toca color");
        Intent intent = new Intent(this, SeleccionColorActivity.class );
        //método deprecado
        //startActivityForResult(intent, COD_PETICION_COLOR);//DEPRECADO:Hay una forma nueva en una más moderno, de hacerlo "mejor"
        this.activityResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent intent_resultado) {
        super.onActivityResult(requestCode, resultCode, intent_resultado);
        if (requestCode == COD_PETICION_COLOR)
        {
            Log.d("ETIQUETA_LOG", "Es la respuesta a mi petición desde seleccionarColor ");
            if (resultCode == RESULT_OK)
            {
                Log.d("ETIQUETA_LOG", "La subactividad finalizó bien ");
                //obtener el color
                int color_seleccionado = intent_resultado.getIntExtra("COLOR_ELEGIDO", R.color.white);
                findViewById(R.id.botonColorFavorito).setBackgroundColor(color_seleccionado);//pillo el boton y le pongo el fondo
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean tieneFoco) {
    //yo quiero validar, cuando pierda el foco
        if (!tieneFoco)
        {
            EditText editTextNombre = (EditText) view;
            String nombreIntro = editTextNombre.getText().toString();

            //si no es válido, vamos a animar su envolotorio el TextInputLayout del nombre
            if (!esNombreValido())
            {
                TextInputLayout envoltorioNombre = findViewById(R.id.tilnombre);
                envoltorioNombre.setError("Nombre incorrecto");
            } else {
                TextInputLayout envoltorioNombre = findViewById(R.id.tilnombre);
                envoltorioNombre.setErrorEnabled(false);
            }
        }
    }
}