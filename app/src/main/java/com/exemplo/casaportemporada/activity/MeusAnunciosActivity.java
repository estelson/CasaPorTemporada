package com.exemplo.casaportemporada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.adapter.AdapterAnuncios;
import com.exemplo.casaportemporada.helper.FirebaseHelper;
import com.exemplo.casaportemporada.model.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity implements AdapterAnuncios.OnClick {

    private List<Anuncio> anuncioList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView text_info;
    private SwipeableRecyclerView rv_anuncios;

    private AdapterAnuncios adapterAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        iniciarComponentes();

        configRv();

        configCliques();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperarAnuncios();
    }

    private void configCliques() {
        findViewById(R.id.ib_add).setOnClickListener(view -> {
            startActivity(new Intent(this, FormAnuncioActivity.class));
        });

        findViewById(R.id.ib_voltar).setOnClickListener(view -> {
            finish();
        });
    }

    private void configRv() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(this));
        rv_anuncios.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(anuncioList, this);
        rv_anuncios.setAdapter(adapterAnuncios);

        rv_anuncios.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                showDialogDelete(position);
            }
        });
    }

    private void showDialogDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir anúncio");
        builder.setMessage("Clique em SIM para confirmar ou em NÃO para cancelar");

        builder.setNegativeButton("Não", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
            adapterAnuncios.notifyDataSetChanged();
        }));

        builder.setPositiveButton("Sim", ((dialogInterface, i) -> {
//            Anuncio anuncio = anuncioList.get(position);
//            anuncio.excluir();

            anuncioList.get(position).excluir();
            Toast.makeText(this, "Anúncio excluído com sucesso", Toast.LENGTH_SHORT).show();

            adapterAnuncios.notifyItemRemoved(position);
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
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

                    text_info.setVisibility(View.GONE);
                } else {
                    text_info.setText("Nenhum anúncio cadastrado");
                }

                progressBar.setVisibility(View.GONE);

                Collections.reverse(anuncioList);
                adapterAnuncios.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo_anuncio);
        text_titulo.setText("Meus anúncios");

        progressBar = findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
        rv_anuncios = findViewById(R.id.rv_anuncios);
    }

    @Override
    public void OnClickListener(Anuncio anuncio) {
        Intent intent = new Intent(this, FormAnuncioActivity.class);
        intent.putExtra("anuncio", anuncio);

        startActivity(intent);
    }
}