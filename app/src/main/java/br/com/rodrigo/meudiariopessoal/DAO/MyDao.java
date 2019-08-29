package br.com.rodrigo.meudiariopessoal.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import br.com.rodrigo.meudiariopessoal.Model.User;

@Dao
public interface MyDao {

    @Insert
    public void addUser(User user);
}
