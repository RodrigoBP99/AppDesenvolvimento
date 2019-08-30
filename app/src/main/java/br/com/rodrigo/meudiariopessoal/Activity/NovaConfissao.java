package br.com.rodrigo.meudiariopessoal.Activity;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import br.com.rodrigo.meudiariopessoal.DAO.AppDatabase;
import br.com.rodrigo.meudiariopessoal.Model.Confissao;
import br.com.rodrigo.meudiariopessoal.R;

public class NovaConfissao extends AppCompatActivity {

    private Button buttonSalvarConfissoa;
    private EditText editTextConfissoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_confissao);
        getSupportActionBar().setElevation(0);

        editTextConfissoa = findViewById(R.id.editTextConfissoa);


        Calendar agora = Calendar.getInstance();

        int dia = agora.get(Calendar.DAY_OF_MONTH);
        int mes = 1 + agora.get(Calendar.MONTH);
        int ano = agora.get(Calendar.YEAR);

        int hora = agora.get(Calendar.HOUR_OF_DAY);
        int minuto = agora.get(Calendar.MINUTE);

        final String data = dia + "/" + mes + "/" + ano;
        final String horario = hora + ":" + minuto;
        buttonSalvarConfissoa = findViewById(R.id.buttonAdicionarConfissao);
        buttonSalvarConfissoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = editTextConfissoa.getText().toString();
                if (!texto.isEmpty()){
                    Confissao confissao = new Confissao(texto, data, horario);
                    salvarConfissao(confissao);
                    finish();
                } else {
                    editTextConfissoa.setError("Campo Vazio");
                }
            }
        });
    }

    private void salvarConfissao(Confissao confissao){
        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "confissaoDataBase")
                .allowMainThreadQueries()
                .build();
        appDatabase.confissaoDao().insertConfissao(confissao);
    }
}
