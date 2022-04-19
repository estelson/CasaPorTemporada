package com.exemplo.casaportemporada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.model.Anuncio;
import com.squareup.picasso.Picasso;

public class DetalheAnuncioActivity extends AppCompatActivity {

    private ImageView img_anuncio;

    private TextView text_titulo_anuncio;
    private TextView text_descricao;

    private EditText edit_quarto;
    private EditText edit_banheiro;
    private EditText edit_garagem;

    private Anuncio anuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_anuncio);

        iniciarComponentes();

        anuncio = (Anuncio) getIntent().getSerializableExtra("anuncio");

        configDados();

        configCliques();
    }

    private void configDados() {
        if(anuncio != null) {
            Picasso.get().load(anuncio.getUrlImagem()).into(img_anuncio);

            text_titulo_anuncio.setText(anuncio.getTitulo());
            text_descricao.setText(anuncio.getDescricao());

            edit_quarto.setText(anuncio.getQuarto());
            edit_quarto.setEnabled(false);
            edit_quarto.setTextColor(Color.DKGRAY);

            edit_banheiro.setText(anuncio.getBanheiro());
            edit_banheiro.setEnabled(false);
            edit_banheiro.setTextColor(Color.DKGRAY);

            edit_garagem.setText(anuncio.getGaragem());
            edit_garagem.setEnabled(false);
            edit_garagem.setTextColor(Color.DKGRAY);
        } else {
            Toast.makeText(this, "Não foi possível recuperar as informações deste anúncio", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarComponentes() {
        TextView text_titulo_toolbar = findViewById(R.id.text_titulo);
        text_titulo_toolbar.setText("Detalhe anúncio");

        img_anuncio = findViewById(R.id.img_anuncio);

        text_titulo_anuncio = findViewById(R.id.text_titulo_anuncio);
        text_descricao = findViewById(R.id.text_descricao);

        edit_quarto = findViewById(R.id.edit_quarto);
        edit_banheiro = findViewById(R.id.edit_banheiro);
        edit_garagem = findViewById(R.id.edit_garagem);
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> {
            finish();
        });
    }

}