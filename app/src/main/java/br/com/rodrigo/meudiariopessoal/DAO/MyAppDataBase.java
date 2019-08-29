package br.com.rodrigo.meudiariopessoal.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.rodrigo.meudiariopessoal.Model.User;

@Database(entities = {User.class},version = 1)
public abstract class MyAppDataBase extends RoomDatabase {

    public abstract MyDao myDao();
}
