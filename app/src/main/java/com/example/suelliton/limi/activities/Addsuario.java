package com.example.suelliton.limi.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.activities.Splash.LOGADO;
import static com.example.suelliton.limi.activities.Splash.usuarioReference;

public class Addsuario extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private UserLoginTask mAuthTask = null;
    private EditText ed_login;
    private AutoCompleteTextView ac_email;
    private EditText ed_password;
    Button btnSalvarUsuario;
    private View progressView;
    private View cadastroFormView;
    private ValueEventListener listener;
    boolean passUser = false;
    boolean passEmail = false;
    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_usuario_activity);
        findViews();
        setViewListeners();
    }
    public void findViews(){
        ed_login = (EditText) findViewById(R.id.user_cadastro);
        ac_email = (AutoCompleteTextView) findViewById(R.id.email_cadastro);
        ed_password = (EditText) findViewById(R.id.password_cadastro);
        btnSalvarUsuario = (Button) findViewById(R.id.cadastro_button);
        cadastroFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
        tv_login = findViewById(R.id.link_login);
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
        btnSalvarUsuario.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        tv_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addsuario.this,Login.class));
            }
        });
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        ac_email.setError(null);
        ed_password.setError(null);
        ed_login.setError(null);
        // Store values at the time of the login attempt.
        String email = ac_email.getText().toString();
        String password = ed_password.getText().toString();
        String user = ed_login.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            ed_password.setError(getString(R.string.error_invalid_password));
            focusView = ed_password;
            cancel = true;
        }
        // Check for a valid user, if the user entered one.
        if (!TextUtils.isEmpty(user) && !isUserValid(user)) {
            ed_login.setError(getString(R.string.error_invalid_user));
            focusView = ed_login;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            ac_email.setError(getString(R.string.error_field_required));
            focusView = ac_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            ac_email.setError(getString(R.string.error_invalid_email));
            focusView = ac_email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password,user);
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            cadastroFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            cadastroFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    cadastroFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            cadastroFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Addsuario.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        ac_email.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mLogin;

        UserLoginTask(String email, String password,String user) {
            mEmail = email;
            mPassword = password;
            mLogin = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            usuarioReference.orderByChild("username").equalTo(mLogin).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        passUser = false;
                    }else{
                        passUser = true;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            usuarioReference.orderByChild("email").equalTo(mEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        passEmail = false;
                    }else{
                        passEmail = true;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            if(passEmail && passUser) {
                Usuario novoUsuario = new Usuario(mEmail, mPassword, mLogin);
                usuarioReference.child(novoUsuario.getUsername()).setValue(novoUsuario);
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("usuarioLogado", novoUsuario.getUsername());
                editor.apply();
                LOGADO = novoUsuario.getUsername();
                return true;
            }else{
                return  false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Toast.makeText(Addsuario.this, "Usuário  cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Addsuario.this,Experimento.class));
                finish();
            } else {
                    if(!passUser){
                        ed_login.requestFocus();
                        Toast.makeText(Addsuario.this, "Username já existe tente outro", Toast.LENGTH_SHORT).show();
                    }
                    if(!passEmail){
                        ac_email.requestFocus();
                        Toast.makeText(Addsuario.this, "Já existe um usuário para este email ", Toast.LENGTH_SHORT).show();
                    }
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
        if(listener != null){
            usuarioReference.removeEventListener(listener);
        }
    }
}

