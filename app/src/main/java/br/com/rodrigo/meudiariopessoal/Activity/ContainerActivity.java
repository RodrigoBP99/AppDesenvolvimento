package br.com.rodrigo.meudiariopessoal.Activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import br.com.rodrigo.meudiariopessoal.Adapter.ConfissaoAdapter;
import br.com.rodrigo.meudiariopessoal.DAO.AppDatabase;
import br.com.rodrigo.meudiariopessoal.Model.Confissao;
import br.com.rodrigo.meudiariopessoal.R;

public class ContainerActivity extends AppCompatActivity {

    private ArrayList<Confissao> confissaos = new ArrayList<>();
    private ConfissaoAdapter confissaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        novaConfissao();
    }

    private void novaConfissao(){
        FloatingActionButton floatingActionButtonNovaConfissao;
        floatingActionButtonNovaConfissao = findViewById(R.id.floatButtonNovaConfissao);
        floatingActionButtonNovaConfissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContainerActivity.this, NovaConfissao.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_container, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_logOut:
                logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ContainerActivity.this, MainActivity.class));
    }

    @Override
    public void onResume() {
        RecyclerView recyclerView = findViewById(R.id.recycleViewConfissao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContainerActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        AppDatabase appDatabase = AppDatabase.getInstance(ContainerActivity.this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getDisplayName();

        confissaos = (ArrayList<Confissao>) appDatabase.confissaoDao().getConfissao(userEmail);
        confissaoAdapter = new ConfissaoAdapter(confissaos, this);
        recyclerView.setAdapter(confissaoAdapter);
        super.onResume();
    }
}
