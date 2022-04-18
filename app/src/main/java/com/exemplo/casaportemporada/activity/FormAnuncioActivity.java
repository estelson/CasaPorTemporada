package com.exemplo.casaportemporada.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.casaportemporada.R;
import com.exemplo.casaportemporada.model.Produto;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class FormAnuncioActivity extends AppCompatActivity {

    public static final int REQUEST_GALERIA = 100;

    private ImageView img_anuncio;
    private String caminhoImagem;
    private Bitmap imagem;

    private EditText edit_titulo;
    private EditText edit_descricao;
    private EditText edit_quarto;
    private EditText edit_banheiro;
    private EditText edit_garagem;

    private CheckBox cb_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_anuncio);

        iniciarComponentes();

        configCliques();
    }

    private void configCliques() {
        findViewById(R.id.ib_salvar).setOnClickListener(view -> {
            validarDados();
        });
    }

    private void iniciarComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Form anúncio");

        edit_titulo = findViewById(R.id.edit_titulo);
        edit_descricao = findViewById(R.id.edit_descricao);
        edit_quarto = findViewById(R.id.edit_quarto);
        edit_banheiro = findViewById(R.id.edit_banheiro);
        edit_garagem = findViewById(R.id.edit_garagem);

        cb_status = findViewById(R.id.cb_status);
    }

    private void validarDados() {
        String titulo = edit_titulo.getText().toString();
        String descricao = edit_descricao.getText().toString();
        String quarto = edit_quarto.getText().toString();
        String banheiro = edit_banheiro.getText().toString();
        String garagem = edit_garagem.getText().toString();

        if (!titulo.isEmpty()) {
            if (!descricao.isEmpty()) {
                if (!quarto.isEmpty()) {
                    if (!banheiro.isEmpty()) {
                        if (!garagem.isEmpty()) {
                            Produto produto = new Produto();
                            produto.setTitulo(titulo);
                            produto.setDescricao(descricao);
                            produto.setQuarto(quarto);
                            produto.setBanheiro(banheiro);
                            produto.setGaragem(garagem);
                            produto.setStatus(cb_status.isChecked());
                        } else {
                            edit_garagem.requestFocus();
                            edit_garagem.setError("Informe a quantidade de vagas de garagem");
                        }
                    } else {
                        edit_banheiro.requestFocus();
                        edit_banheiro.setError("Informe a quantidade de banheiros");
                    }
                } else {
                    edit_quarto.requestFocus();
                    edit_quarto.setError("Informe a quantidade de quartos");
                }
            } else {
                edit_descricao.requestFocus();
                edit_descricao.setError("Informe uma descrição");
            }
        } else {
            edit_titulo.requestFocus();
            edit_titulo.setError("Informe um título");
        }
    }

    public void verificarPermissaoGaleria(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormAnuncioActivity.this, "Permissao negada", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissaoGaleria(permissionListener, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }

    private void showDialogPermissaoGaleria(PermissionListener listener, String[] permissoes) {
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissões negadas")
                .setDeniedMessage("Você negou a permissão para acessar a galeria do dispositivo. Deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .check();
    }

}