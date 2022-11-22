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
import com.example.redsocialprueba2.activities.HomeActivity;
import com.example.redsocialprueba2.models.User;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CompliteProfileActivity extends AppCompatActivity {

    TextInputEditText txt_userConfirm;
    Button confirm_user;
    private AuthProvider mAuthProvider;
    private UserProvider mUserProviter;
    private TextInputEditText txt_phoneConfirm;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complite_profile);

        this.mProgressDialog = new ProgressDialog(this);

        mAuthProvider = new AuthProvider();
        mUserProviter = new UserProvider();


        txt_phoneConfirm = findViewById(R.id.txt_ed_phoneConfirm);
        txt_userConfirm = findViewById(R.id.txt_ed_userCofirm);

        confirm_user = findViewById(R.id.b_confirm);
        confirm_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrer();
            }
        });



    }
    private void registrer(){

        String user = txt_userConfirm.getText().toString();
        String phone = txt_phoneConfirm.getText().toString();

        if(!user.isEmpty()&&!phone.isEmpty()){
            updateUser(user,phone);
        }
        else{
            Toast.makeText(this,"Campos no debe estar vacio",Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUser(String userName,final String phone) {
        ShowPDialog();
        String id = mAuthProvider.getUid();
        Toast.makeText(CompliteProfileActivity.this, "Authentication exit.",
                Toast.LENGTH_SHORT).show();
        User user = new User();
        user.setUserName(userName);
        user.setUserPhone(phone);
        user.setTimestamp(new Date().getTime());
        user.setId(id);
        mUserProviter.UpDate(user).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(CompliteProfileActivity.this, "usuario alamecnado",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CompliteProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CompliteProfileActivity.this, "usuario no almacenado",
                            Toast.LENGTH_SHORT).show();
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