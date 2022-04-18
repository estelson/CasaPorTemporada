package com.exemplo.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.activity.MainActivity;
import com.exemplo.casaportemporada.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_email;
    private EditText edit_senha;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarComponentes();

        configCliques();
    }

    private void configCliques() {
        findViewById(R.id.text_criar_conta).setOnClickListener(view -> {
            startActivity(new Intent(this, CriarContaActivity.class));
        });

        findViewById(R.id.text_recuperar_conta).setOnClickListener(view -> {
            startActivity(new Intent(this, RecuperarContaActivity.class));
        });
    }

    public void validarDados(View view) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if(!email.isEmpty()) {
            if(!senha.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);
            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe sua senha");
            }
        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu e-Mail");
        }
    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email,
                senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, "Erro ao efetuar login. Motivo: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciarComponentes() {
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);

        progressBar = findViewById(R.id.progressBar);
    }

}