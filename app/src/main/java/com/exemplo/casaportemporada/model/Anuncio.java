package com.exemplo.casaportemporada.model;

import com.exemplo.casaportemporada.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Anuncio implements Serializable {

    private String id;
    private String idUsuario;
    private String titulo;
    private String descricao;
    private String quarto;
    private String banheiro;
    private String garagem;
    private boolean status;
    private String urlImagem;

    public Anuncio() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvar() {
        // Grava o anúncio do usuário no nó "anuncios" do Firebase Realtime Database.  Aparecerá  na
        // tela "Meus anúncios"
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getUidFirebase())
                .child(this.getId());

        reference.setValue(this);

        // Grava o anúncio no nó "anuncios_publicos" do Firebase  Realtime  Database.  Aparecerá  na
        // tela principal
        DatabaseReference anuncioPublico = FirebaseHelper.getDatabaseReference()
                .child("anuncios_publicos")
                .child(this.getId());

        anuncioPublico.setValue(this);
    }

    public void excluir() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getUidFirebase())
                .child(this.getId());

        reference.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                StorageReference storageReference = FirebaseHelper.getStorageReference()
                        .child("imagens")
                        .child("anuncios")
                        .child(this.getId() + ".jpeg");

                storageReference.delete();
            }
        });

        DatabaseReference referencePublico = FirebaseHelper.getDatabaseReference()
                .child("anuncios_publicos")
                .child(this.getId());

        referencePublico.removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getBanheiro() {
        return banheiro;
    }

    public void setBanheiro(String banheiro) {
        this.banheiro = banheiro;
    }

    public String getGaragem() {
        return garagem;
    }

    public void setGaragem(String garagem) {
        this.garagem = garagem;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
