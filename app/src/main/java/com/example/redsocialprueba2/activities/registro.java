package com.example.redsocialprueba2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.models.User;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class registro extends AppCompatActivity {

    CircleImageView mg_back;
    TextInputEditText txt_user;
    TextInputEditText txt_EmailUser;
    TextInputEditText txt_PhoneUser;
    TextInputEditText txt_passwordUser;
    TextInputEditText txt_confirmPassword;
    Button registrar_user;
    private AuthProvider mAuthProvider;
    private UserProvider mUserProvider;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.mProgressDialog = new ProgressDialog(this);

        mAuthProvider = new AuthProvider();
        mUserProvider = new UserProvider();

        txt_user = findViewById(R.id.txt_ed_user);
        txt_EmailUser = findViewById(R.id.txt_ed_email);
        txt_passwordUser = findViewById(R.id.txt_ed_passwordUser);
        txt_PhoneUser = findViewById(R.id.txt_ed_phone);
        txt_confirmPassword = findViewById(R.id.txt_ed_confirmPassword);

        registrar_user = findViewById(R.id.b_registrar);
        registrar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrer();
            }
        });

        mg_back = findViewById(R.id.Circle_img_back);
        mg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void registrer(){

        String user = txt_user.getText().toString();
        String EmailUser = txt_EmailUser.getText().toString();
        String phone = txt_PhoneUser.getText().toString();
        String passwordUser = txt_passwordUser.getText().toString();
        String confirmPassword = txt_confirmPassword.getText().toString();

        if(!user.isEmpty()&&!EmailUser.isEmpty()&&!passwordUser.isEmpty()&&!confirmPassword.isEmpty()&&!phone.isEmpty()){
            if(isEmailValid(EmailUser)) {
                if (passwordUser.equals(confirmPassword)) {
                    if(passwordUser.length()>=6){
                        createUser(user,EmailUser,passwordUser,phone);
                    }
                    else{
                        Toast.makeText(this, "Debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "user: " + user + " Email: " + EmailUser + " password: " + passwordUser, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "password no son iguales", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Email incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"campos vacios",Toast.LENGTH_SHORT).show();
        }

    }

    private void createUser(String userName,final String email, String password, final String phone) {
        ShowPDialog();
        mAuthProvider.register(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuthProvider.getUid();

                    Toast.makeText(registro.this, "Authentication exit.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuthProvider.getUser();
                    User userr = new User();
                    userr.setId(id);
                    userr.setEmail(email);
                    userr.setUserName(userName);
                    userr.setUserPhone(phone);
                    userr.setTimestamp(new Date().getTime());

                    mUserProvider.create(userr).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressDialog.dismiss();
                            if(task.isSuccessful()){
                                Intent intent = new Intent(registro.this,HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(registro.this, "usuario no almacenado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(registro.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(registro.this, "Authentication failed: " + e,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void ShowPDialog(){
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Entrando!");
        mProgressDialog.setMessage("Porfavor Espere!");
        mProgressDialog.show();
    }
}