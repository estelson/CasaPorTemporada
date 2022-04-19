package com.exemplo.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private EditText edit_email;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        configCliques();

        iniciarComponentes();
    }

    public void validarDados(View view) {
        String email = edit_email.getText().toString();
        if(!email.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);

            recuperarSenha(email);
        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu e-Mail");
        }
    }

    private void recuperarSenha(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(this, "e-Mail enviado com sucesso.\nVerifique sua caixa de entrada para recuperar a senha.", Toast.LENGTH_LONG).show();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, "Erro ao enviar e-Mail de recuperação de senha. Motivo: " + error, Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
        });
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> {
            finish();
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo_anuncio);
        text_titulo.setText("Recuperar conta");

        edit_email = findViewById(R.id.edit_email);

        progressBar = findViewById(R.id.progressBar);
    }

}