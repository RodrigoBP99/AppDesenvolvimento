package br.com.rodrigo.meudiariopessoal.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.rodrigo.meudiariopessoal.Model.User;
import br.com.rodrigo.meudiariopessoal.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextSenha;

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        findViewByIds();

        trocarTela();
    }

    private void findViewByIds() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarLogin();
    }

    private void verificarLogin(){
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, ContainerActivity.class));
            finish();
        }
    }

    private void trocarTela(){

        TextView textViewRegistrar = findViewById(R.id.textViewCadastrar);

        textViewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                email = editTextEmail.getText().toString().trim();
                senha = editTextSenha.getText().toString().trim();
                if (email.isEmpty() || senha.isEmpty()){
                    Snackbar.make(v, "Os campos devem ser Preeenchidos", Snackbar.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    user.setEmail(email);
                    user.setSenha(senha);
                    confirmarLogin(v);
                }


            }
        });
    }

    private void confirmarLogin(final View v){
        firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextSenha.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            verificarLogin();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Email ou senha inválidos";
                            } catch (FirebaseAuthInvalidUserException e) {
                                excecao = "Usuario nao está cadastrado";
                            } catch (Exception e) {
                                excecao = "Erro";
                                e.printStackTrace();
                            }
                            Snackbar.make(v, excecao, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
