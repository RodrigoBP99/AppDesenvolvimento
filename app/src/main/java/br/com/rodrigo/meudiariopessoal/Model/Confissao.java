package br.com.rodrigo.meudiariopessoal.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "confissao")
public class Confissao {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "confissao_texto")
    private String texto;


    @ColumnInfo(name = "confissao_data")
    private String data;


    @ColumnInfo(name = "confissao_hora")
    private String hora;

    @ColumnInfo(name = "confissao_userID")
    private String userID;

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
