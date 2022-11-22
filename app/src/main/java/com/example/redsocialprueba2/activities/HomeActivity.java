package com.example.redsocialprueba2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.fragments.ChatFragment;
import com.example.redsocialprueba2.fragments.FiltersFragment;
import com.example.redsocialprueba2.fragments.HomeFragment;
import com.example.redsocialprueba2.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());


    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.ItemHome){
                        openFragment(new HomeFragment());
                    }
                    else if (item.getItemId() == R.id.ItemChat){
                        openFragment(new ChatFragment());
                    }
                    else if (item.getItemId() == R.id.ItemFilters){
                        openFragment(new FiltersFragment());
                    }
                    else if (item.getItemId() == R.id.ItemPerfil){
                        openFragment(new PerfilFragment());
                    }
                    return true;
                }
            };

    public void singOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}