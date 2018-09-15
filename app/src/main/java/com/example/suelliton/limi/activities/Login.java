package com.example.suelliton.limi.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;


import static com.example.suelliton.limi.activities.Splash.LOGADO;
import static com.example.suelliton.limi.activities.Splash.usuarioReference;

public class Login extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;
    private Usuario USUARIO_OBJETO_LOGADO;
    private EditText ed_password;
    private View progressView;
    private TextView linkCadastro;
    private EditText ed_login;
    Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        findViews();
        setViewListeners();

    }

public void findViews(){
    ed_login = (EditText) findViewById(R.id.input_user);
    ed_password = (EditText) findViewById(R.id.input_password);
    progressView = (ProgressBar) findViewById(R.id.login_progress);
    linkCadastro = (TextView) findViewById(R.id.link_login);
    btnLogar = (Button) findViewById(R.id.sign_in_button);
}
public void setViewListeners(){
    ed_login.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        }
    });
    ed_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        }
    });
    linkCadastro.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Login.this,Addsuario.class));
            finish();
        }
    });
    btnLogar.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            attemptLogin();
        }
    });

}




    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        ed_password.setError(null);
        ed_login.setError(null);

        String password = ed_password.getText().toString();
        String user = ed_login.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            ed_password.setError(getString(R.string.error_invalid_password));
            focusView = ed_password;
            cancel = true;
        }

        if (!TextUtils.isEmpty(user) && !isUserValid(user)) {
            ed_login.setError(getString(R.string.error_invalid_user));
            focusView = ed_login;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(user, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isUserValid(String user) {
        //TODO: Replace this with your own logic
        return user.length() > 4;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }



    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private boolean passLogin = false;
        private boolean passPassword = false;
        private  String login;
        private  String password;

        UserLoginTask(String user, String password) {
            login = user;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Query query = usuarioReference.orderByChild("username").equalTo(login).limitToFirst(1);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.exists()) {
                        passLogin = true;
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        if (usuario.getPassword().equals(password)) {
                            LOGADO = dataSnapshot.getKey();
                            passPassword = true;
                            //Toast.makeText(Login.this, "usuario encontrado"+ usuario.getUsername(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }


            if(passLogin && passPassword){
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("usuarioLogado", LOGADO);
                editor.apply();
                startActivity(new Intent(Login.this,Experimento.class));
                finish();
            } else {
                ed_password.setError(getString(R.string.error_incorrect_password));
                ed_password.requestFocus();
                Toast.makeText(Login.this, "Usuário inválido", Toast.LENGTH_SHORT).show();

            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}

