package edu.val.formulario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BienvenidaActivity extends AppCompatActivity {

    private TextView texto_bienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        String nombre_persona = getIntent().getStringExtra("NOMBRE");
        //Persona persona_intent_serializable =  (Persona) getIntent().getSerializableExtra("PERSONA");
        Persona persona_intent_parcelable =  getIntent().getParcelableExtra("PERSONA");
        Log.d("ETIQUETA_LOG", "PERSONA = "+persona_intent_parcelable.toString());
        this.texto_bienvenida = findViewById(R.id.textView);
        this.texto_bienvenida.setText("BIENVENIDO\n"+nombre_persona);
    }
}