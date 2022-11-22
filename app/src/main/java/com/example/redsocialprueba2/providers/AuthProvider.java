package com.example.redsocialprueba2.providers;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProvider {

    private FirebaseAuth mAuth;

    public AuthProvider(){
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> login(String Email, String Password){
        return mAuth.signInWithEmailAndPassword(Email,Password);
    }

    public Task<AuthResult> googleLogin(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return mAuth.signInWithCredential(credential);
    }

    public String getUid(){
        if(mAuth.getCurrentUser()!=null) {
            return mAuth.getCurrentUser().getUid();
        }
        else return null;
    }

    public String getEmail(){
        if(mAuth.getCurrentUser()!=null) {
            return mAuth.getCurrentUser().getEmail();
        }
        else return null;

    }
    public FirebaseUser getUser(){
        if(mAuth.getCurrentUser()!=null) {
            return  mAuth.getCurrentUser();
        }
        else return null;
    }

    public Task<AuthResult> register(String Email, String Password){
        return mAuth.createUserWithEmailAndPassword(Email, Password);
    }

    public void logOut(){
        if(mAuth!=null) {
            mAuth.signOut();
        }
    }

}
