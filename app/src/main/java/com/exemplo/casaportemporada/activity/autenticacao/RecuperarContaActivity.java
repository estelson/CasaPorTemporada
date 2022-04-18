package com.exemplo.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.exemplo.casaportemporada.R;

public class RecuperarContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        configCliques();

        iniciarComponentes();
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> {
            finish();
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Recuperar conta");
    }

}