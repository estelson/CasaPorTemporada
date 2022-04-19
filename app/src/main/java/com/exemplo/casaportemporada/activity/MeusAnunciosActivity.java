package com.exemplo.casaportemporada.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.adapter.AdapterAnuncios;
import com.exemplo.casaportemporada.helper.FirebaseHelper;
import com.exemplo.casaportemporada.model.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity {

    private List<Anuncio> anuncioList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_anuncios;

    private AdapterAnuncios adapterAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        iniciarComponentes();

        configRv();

        recuperarAnuncios();
    }

    private void configRv() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(this));
        rv_anuncios.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(anuncioList);
        rv_anuncios.setAdapter(adapterAnuncios);
    }

    private void recuperarAnuncios() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getUidFirebase());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    anuncioList.clear();

                    for(DataSnapshot snap : snapshot.getChildren()) {
                        Anuncio anuncio = snap.getValue(Anuncio.class);
                        anuncioList.add(anuncio);
                    }
                } else {
                    text_info.setText("Nenhum anúncio cadastrado");
                }

                progressBar.setVisibility(View.GONE);
                text_info.setVisibility(View.GONE);

                Collections.reverse(anuncioList);
                adapterAnuncios.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Meus anúncios");

        progressBar = findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
        rv_anuncios = findViewById(R.id.rv_anuncios);
    }

}