package br.com.rodrigo.meudiariopessoal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

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
        getSupportActionBar().setTitle("Nova Confissão");

        editTextConfissoa = findViewById(R.id.editTextConfissoa);

        buttonSalvarConfissoa = findViewById(R.id.buttonAdicionarConfissao);
        SalvarConfissao();
    }

    private void SalvarConfissao() {
        buttonSalvarConfissoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = editTextConfissoa.getText().toString();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userId = firebaseAuth.getCurrentUser().getEmail();

                String data = pegarData();
                String hora = pegarHora();

                if (!texto.isEmpty()){
                    Confissao confissao = new Confissao();
                    confissao.setTexto(texto);
                    confissao.setData(data);
                    confissao.setHora(hora);
                    confissao.setUserID(userId);
                    salvarConfissao(confissao);
                    finish();
                } else {
                    editTextConfissoa.setError("Campo Vazio");
                }
            }
        });
    }

    private void salvarConfissao(Confissao confissao){
        AppDatabase appDatabase = AppDatabase.getInstance(NovaConfissao.this);
        appDatabase.confissaoDao().insertConfissao(confissao);
    }

    private String pegarData(){
        Calendar agora = Calendar.getInstance();

        int dia = agora.get(Calendar.DAY_OF_MONTH);
        int mes = 1 + agora.get(Calendar.MONTH);
        int ano = agora.get(Calendar.YEAR);
        String data = String.format("%02d", dia) + "/" + String.format("%02d", mes) + "/" + ano;
        return data;
    }

    private String pegarHora(){
        Calendar agora = Calendar.getInstance();

        int hora = agora.get(Calendar.HOUR_OF_DAY);
        int minuto = agora.get(Calendar.MINUTE);
        String horario = String.format("%02d", hora) + ":" + String.format("%02d", minuto);
        return horario;
    }
}
