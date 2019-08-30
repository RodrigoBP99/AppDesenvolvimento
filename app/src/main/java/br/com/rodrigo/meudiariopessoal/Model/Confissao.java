package br.com.rodrigo.meudiariopessoal.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "confissao")
public class Confissao {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "confissoa_texto")
    private String texto;


    @ColumnInfo(name = "confissao_data")
    private String data;


    @ColumnInfo(name = "confissoa_hora")
    private String hora;

    public Confissao(int id, String texto, String data, String hora) {
        this.id = id;
        this.texto = texto;
        this.data = data;
        this.hora = hora;
    }

    @Ignore
    public Confissao(String texto, String data, String hora){
        this.texto = texto;
        this.data = data;
        this.hora = hora;
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
}
