package br.com.rodrigo.meudiariopessoal.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.rodrigo.meudiariopessoal.Helper.Base64Helper;
import br.com.rodrigo.meudiariopessoal.Model.User;
import br.com.rodrigo.meudiariopessoal.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextCadastoEmail;
    private EditText editTextCadastroSenha;
    private EditText editTextCadastroConfirmarSenha;
    private EditText editTextCadastroNome;

    private FirebaseAuth firebaseAuth;
    private User user;

    private String email;
    private String nome;
    private String senha;
    private String confirmarSenha;

    private Button buttonConfirmarCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setTitle("Cadastro");
        firebaseAuth = FirebaseAuth.getInstance();

        findViewByIds();
        userCadastro();

    }

    private void findViewByIds() {
        editTextCadastoEmail = findViewById(R.id.editTextEmailCadastro);
        editTextCadastroNome = findViewById(R.id.editTextNomeCadastro);
        editTextCadastroSenha = findViewById(R.id.editTextSenhaCadastro);
        editTextCadastroConfirmarSenha = findViewById(R.id.editTextConfirmaSenhaCadastro);

        buttonConfirmarCadastro = findViewById(R.id.buttonConfirmaCadastro);

    }

    private void userCadastro(){
        buttonConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextCadastoEmail.getText().toString().trim();
                nome = editTextCadastroNome.getText().toString().trim();
                senha = editTextCadastroSenha.getText().toString().trim();
                confirmarSenha = editTextCadastroConfirmarSenha.getText().toString().trim();

                if (!email.isEmpty()){
                    if (!nome.isEmpty()){
                        if (!senha.isEmpty() || firebaseAuth.getCurrentUser() != null){
                            if (!confirmarSenha.isEmpty() || firebaseAuth.getCurrentUser() != null){
                                if (confirmarSenha.equals(senha) || firebaseAuth.getCurrentUser() != null){

                                    user = new User();
                                    user.setEmail(email);
                                    user.setName(nome);
                                    user.setSenha(senha);

                                    if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().getEmail() == null){
                                        firebaseAuth.getCurrentUser().updateEmail(email);
                                    }
                                    cadastrarUser();
                                } else {
                                    Toast.makeText(CadastroActivity.this, "Senhas devem ser iguais", Toast.LENGTH_SHORT).show(); }
                            } else {
                                campoVazio(editTextCadastroConfirmarSenha); }
                        } else {
                            campoVazio(editTextCadastroSenha); }
                    } else {
                        campoVazio(editTextCadastroNome); }
                } else {
                    campoVazio(editTextCadastoEmail); }
            }
        });
    }

    private void campoVazio(EditText campo) {
        campo.setError("Preencha este campo.");
        campo.requestFocus();
    }

    private void cadastrarUser() {
        if (firebaseAuth.getCurrentUser() == null){
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String idUser = Base64Helper.codificarBase64(user.getEmail());
                                user.setId(idUser);
                                user.salvarUsuario();
                                startActivity(new Intent(CadastroActivity.this, ContainerActivity.class));
                                finish();
                            } else {
                                String excecao = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e){
                                    editTextCadastroSenha.setError("Senha fraca");
                                } catch (FirebaseAuthInvalidCredentialsException e){
                                    editTextCadastoEmail.setError("E-mail Inválido");
                                    excecao = "Utilize um E-mail Válido";
                                } catch (FirebaseAuthUserCollisionException e){
                                    editTextCadastoEmail.setError("Email já cadastrado");
                                    excecao = "Essa conta já existe";
                                } catch (Exception e){
                                    excecao = "Erro ao cadastrar usuário" + e.getMessage();
                                    e.printStackTrace();
                                }
                                Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            salvarUser(user);
        }
    }

    private void salvarUser(User user) {
        String idUsuario = Base64Helper.codificarBase64(user.getEmail());
        user.setId(idUsuario);
        user.salvarUsuario();
        startActivity(new Intent(CadastroActivity.this, ContainerActivity.class));
        finish();
    }
}
