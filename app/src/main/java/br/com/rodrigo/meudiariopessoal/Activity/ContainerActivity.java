package br.com.rodrigo.meudiariopessoal.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.rodrigo.meudiariopessoal.Adapter.ConfissaoAdapter;
import br.com.rodrigo.meudiariopessoal.Model.Confissao;
import br.com.rodrigo.meudiariopessoal.R;

public class ContainerActivity extends AppCompatActivity {

    private ArrayList<Confissao> confissaos = new ArrayList<>();
    private ConfissaoAdapter confissaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        getSupportActionBar().setElevation(0);


        RecyclerView recyclerView = findViewById(R.id.recycleViewConfissao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Calendar agora = Calendar.getInstance();

        int dia = agora.get(Calendar.DAY_OF_MONTH);
        int mes = 1 + agora.get(Calendar.MONTH);
        int ano = agora.get(Calendar.YEAR);

        int hora = agora.get(Calendar.HOUR_OF_DAY);
        int minuto = agora.get(Calendar.MINUTE);

        String data = dia + "/" + mes + "/" + ano;
        String horario = hora + ":" + minuto;

        Confissao confissao = new Confissao();
        confissao.setTexto("qualquer coisa ali e aqui");
        confissao.setData(data);
        confissao.setHora(horario);

        confissaos.add(confissao);

        confissaoAdapter = new ConfissaoAdapter(confissaos, this);
        recyclerView.setAdapter(confissaoAdapter);

        novoContainer();
    }

    private void novoContainer(){
        FloatingActionButton floatingActionButtonNovaConfissao;
        floatingActionButtonNovaConfissao = findViewById(R.id.floatButtonNovaConfissao);
        floatingActionButtonNovaConfissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContainerActivity.this, NovaConfissao.class));
            }
        });
    }
}
