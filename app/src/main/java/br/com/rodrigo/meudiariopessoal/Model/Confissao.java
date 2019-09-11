package br.com.rodrigo.meudiariopessoal.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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

    @ColumnInfo(name = "confissao_userName")
    private String userName;

    @ColumnInfo(name = "confissao_userEmail")
    private String userEmail;


    public Confissao(int id, String texto, String data, String hora, String userName, String userEmail) {
        this.id = id;
        this.texto = texto;
        this.data = data;
        this.hora = hora;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    @Ignore
    public Confissao(String texto, String data, String hora, String userName, String userEmail) {
        this.texto = texto;
        this.data = data;
        this.hora = hora;
        this.userName = userName;
        this.userEmail = userEmail;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
