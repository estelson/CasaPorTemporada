package com.exemplo.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.activity.MainActivity;
import com.exemplo.casaportemporada.helper.FirebaseHelper;
import com.exemplo.casaportemporada.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_telefone;
    private EditText edit_senha;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

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
        text_titulo.setText("Crie sua conta");

        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_senha = findViewById(R.id.edit_senha);

        progressBar = findViewById(R.id.progressBar);
    }

    public void validarDados(View view) {
        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String senha = edit_senha.getText().toString();

        if(!nome.isEmpty()) {
            if(!email.isEmpty()) {
                if(!telefone.isEmpty()) {
                    if(!senha.isEmpty()) {
                        progressBar.setVisibility(View.VISIBLE);

                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        cadastrarUsuario(usuario);
                    } else {
                        edit_senha.requestFocus();
                        edit_senha.setError("Informe sua senha");
                    }
                } else {
                    edit_telefone.requestFocus();
                    edit_telefone.setError("Informe seu telefone");
                }
            } else {
                edit_email.requestFocus();
                edit_email.setError("Informe seu e-Mail");
            }
        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome");
        }
    }

    private void cadastrarUsuario(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                String idUser = task.getResult().getUser().getUid();
                usuario.setId(idUser);

                usuario.salvar();

                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, "Erro ao cadastrar usuário. Motivo: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}