package br.com.rodrigo.meudiariopessoal.Model;

import android.arch.persistence.room.Entity;

@Entity(tableName = "diarios")
public class Confissao {

    private int id;
    private String texto;
    private String data;
    private String hora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
