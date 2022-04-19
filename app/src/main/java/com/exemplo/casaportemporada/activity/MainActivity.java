package com.exemplo.casaportemporada.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.activity.autenticacao.LoginActivity;
import com.exemplo.casaportemporada.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    ImageButton ib_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();

        configCliques();
    }

    private void configCliques() {
        ib_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_filtrar) {
                    startActivity(new Intent(this, FiltrarAnunciosActivity.class));
                } else if(menuItem.getItemId() == R.id.menu_meus_anuncios) {
                    if(FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MeusAnunciosActivity.class));
                    } else {
                        showDialogLogin();
                    }
                } else {
                    if(FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    } else {
                        showDialogLogin();
                    }
                }

                return true;
            });

            popupMenu.show();
        });
    }

    private void showDialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação");
        builder.setMessage("Você não está autenticado no app. Deseja fazer isso agora?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setPositiveButton("Sim", (dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciarComponentes() {
        ib_menu = findViewById(R.id.ib_menu);
    }

}