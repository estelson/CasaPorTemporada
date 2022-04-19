package com.exemplo.casaportemporada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.exemplo.casaportemporada.R;

public class FiltrarAnunciosActivity extends AppCompatActivity {

    private TextView text_quarto;
    private TextView text_banheiro;
    private TextView text_garagem;

    private SeekBar sb_quarto;
    private SeekBar sb_banheiro;
    private SeekBar sb_garagem;

    private int qtd_quarto;
    private int qtd_banheiro;
    private int qtd_garagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_anuncios);

        iniciarComponentes();

        configCliques();
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> {
            finish();
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Filtrar");

        text_quarto = findViewById(R.id.text_quarto);
        text_banheiro = findViewById(R.id.text_banheiro);
        text_garagem = findViewById(R.id.text_garagem);

        sb_quarto = findViewById(R.id.sb_quarto);
        sb_banheiro = findViewById(R.id.sb_banheiro);
        sb_garagem = findViewById(R.id.sb_garagem);
    }

    public void limparFiltro(View view) {
        qtd_quarto = 0;
        qtd_banheiro = 0;
        qtd_garagem = 0;

        sb_quarto.setProgress(0);
        sb_banheiro.setProgress(0);
        sb_garagem.setProgress(0);

        finish();
    }

}