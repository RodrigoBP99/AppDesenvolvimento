package br.com.rodrigo.meudiariopessoal.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.rodrigo.meudiariopessoal.Model.Confissao;

@Dao
public interface ConfissaoDao {
    @Query("SELECT * FROM confissao")
    List<Confissao> getAll();

    @Query("SELECT * FROM confissao WHERE userID=:userID")
    List<Confissao> getConfissao(String userID);

    @Insert
    void insertConfissao(Confissao confissao);

    @Delete
    void delete(Confissao confissao);
}
