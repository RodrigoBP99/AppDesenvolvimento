package br.com.rodrigo.meudiariopessoal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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


        RecyclerView recyclerView = findViewById(R.id.recycleViewConfissao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Calendar agora = Calendar.getInstance();

        agora.get(Calendar.DATE);

        String data = agora.toString();


        Confissao confissao = new Confissao();
        confissao.setTexto("qualquer coisa ali e aqui");
        confissao.setData(data);

        confissaos.add(confissao);

        confissaoAdapter = new ConfissaoAdapter(confissaos, this);
    }
}
