package br.com.rodrigo.meudiariopessoal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.rodrigo.meudiariopessoal.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setTitle("Cadastro");
        getSupportActionBar().setElevation(0);
    }
}
