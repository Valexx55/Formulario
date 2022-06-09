package edu.val.formulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class SeleccionColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_color);
    }

    public void colorSeleccionado(View view) {
        Log.d("ETIQUETA_LOG", "Color seleccionado ");
        LinearLayout linearLayout = (LinearLayout)view;
        ColorDrawable colorDrawable = (ColorDrawable) linearLayout.getBackground();
        int color = colorDrawable.getColor();

        Intent intent_resultado = new Intent();
        intent_resultado.putExtra("COLOR_ELEGIDO", color);

        this.setResult(RESULT_OK, intent_resultado);
        this.finish();
    }
}