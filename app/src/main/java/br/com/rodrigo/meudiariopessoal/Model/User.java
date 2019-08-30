package br.com.rodrigo.meudiariopessoal.Model;

import com.google.firebase.database.DatabaseReference;

import br.com.rodrigo.meudiariopessoal.Config.ConfigFireBase;

public class User {

    private String id;
    private String name;
    private String email;
    private String senha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvarUsuario() {
        DatabaseReference firebase = ConfigFireBase.getDatabase();

        firebase.child("user")
                .child(this.id)
                .setValue(this);
    }
}
