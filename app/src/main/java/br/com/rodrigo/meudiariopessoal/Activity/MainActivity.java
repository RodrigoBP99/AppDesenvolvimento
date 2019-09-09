package br.com.rodrigo.meudiariopessoal.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import br.com.rodrigo.meudiariopessoal.Config.ConfigFireBase;
import br.com.rodrigo.meudiariopessoal.Helper.Base64Helper;
import br.com.rodrigo.meudiariopessoal.Model.User;
import br.com.rodrigo.meudiariopessoal.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private User user = new User();

    private LoginButton buttonLoginFacebook;
    private CallbackManager callbackManager;
    private ValueEventListener listener;

    private String email;
    private String senha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(MainActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewByIds();

        acaoLogar();

        callbackManager = CallbackManager.Factory.create();
        buttonLoginFacebook.setReadPermissions("email");
        buttonLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookLogin: ", "Facebook login erro msg: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            verificarPerfil();
                        } else {
                            Log.w("FacebookLogin: ", "Erro no login com facebook", task.getException());
                        }
                    }
                });
    }

    private void verificarPerfil() {
        listener = ConfigFireBase.getDatabase().child("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String idUser = "";
                        if (firebaseAuth.getCurrentUser().getEmail() != null){
                            idUser = Base64Helper.codificarBase64(firebaseAuth.getCurrentUser().getEmail());
                        }

                        if (!dataSnapshot.hasChild(idUser) || idUser.equals("")){
                            finish();
                            startActivity(new Intent(MainActivity.this, CadastroActivity.class));
                        } else {
                            finish();
                            startActivity(new Intent(MainActivity.this, ContainerActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void findViewByIds() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLoginFacebook = findViewById(R.id.login_button_facebook);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarLogin();
    }

    private void verificarLogin() {
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, ContainerActivity.class));
            finish();
        }
    }

    private void acaoLogar() {

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
                if (email.isEmpty() || senha.isEmpty()) {
                    Snackbar.make(v, "Os campos devem ser Preeenchidos", Snackbar.LENGTH_LONG).show();
                } else {
                    user.setEmail(email);
                    user.setSenha(senha);
                    validarLogin(v);
                }
            }
        });
    }

    private void validarLogin(final View v) {
        firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextSenha.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
