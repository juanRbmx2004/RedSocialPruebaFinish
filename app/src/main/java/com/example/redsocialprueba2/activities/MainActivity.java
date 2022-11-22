package com.example.redsocialprueba2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.models.User;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.UserProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {



    TextView textview_registrer;
    TextInputEditText txt_correo;
    TextInputEditText txt_password;
    Button button_login;
    AuthProvider mAuthProvider;
    SignInButton button_google;
    ProgressDialog mProgressDialog;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private final int REQUEST_CODE_GOOGLE = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private UserProvider mUserProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mProgressDialog = new ProgressDialog(this);

        mAuthProvider = new AuthProvider();
        mUserProvider = new UserProvider();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        textview_registrer = findViewById(R.id.txt_v_registrer);
        txt_correo = findViewById(R.id.txt_ed_correo);
        txt_password = findViewById(R.id.txt_ed_passwordInicio);
        button_login = findViewById(R.id.b_login);
        button_google = findViewById(R.id.b_login_google);

        button_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        textview_registrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,registro.class);
                startActivity(intent);
            }
        });



    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuthProvider.getUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                Toast.makeText(MainActivity.this,"si entra",Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this,"error: "+e,Toast.LENGTH_LONG).show();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        ShowPDialog();
        mAuthProvider.googleLogin(idToken).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuthProvider.getUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mProgressDialog.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void login(){
        String Email = txt_correo.getText().toString();
        String Password = txt_password.getText().toString();
        if(!Email.isEmpty()&&!Password.isEmpty()){
            Toast.makeText(this,"Email: "+Email+" Password: "+Password,Toast.LENGTH_SHORT).show();
            ShowPDialog();
            mAuthProvider.login(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mProgressDialog.dismiss();
                    if(task.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Usuario o contraceña incorrecto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this,"Debes de llenar todos los compos",Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(FirebaseUser user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            String id = mAuthProvider.getUid();
            checkUserExist(id);

        }
    }

    private void checkUserExist(final String id) {
            mUserProvider.getUser(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()){
                        mProgressDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        String emailUser = mAuthProvider.getEmail();
                        Map<String, Object> map = new HashMap<>();
                        map.put("email",emailUser);
                        User user = new User();
                        user.setEmail(emailUser);
                        user.setId(id);
                        mUserProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mProgressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this,CompliteProfileActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"no se pudo iniciar",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
    }

    private void ShowPDialog(){
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Entrando!");
        mProgressDialog.setMessage("Porfavor Espere!");
        mProgressDialog.show();
    }


}