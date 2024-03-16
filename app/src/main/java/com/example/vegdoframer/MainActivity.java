package com.example.vegdoframer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.vegdoframer.auth.SignIn_page;
import com.example.vegdoframer.ui.CheckUserView;
import com.example.vegdoframer.ui.HawkerDashboard;
import com.example.vegdoframer.ui.MainPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth Fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          Fauth = FirebaseAuth.getInstance();
                                          if (Fauth.getCurrentUser() != null) {

                                              Intent intent = new Intent(MainActivity.this, CheckUserView.class);
                                              startActivity(intent);
                                              finish();

                                          } else {

                                              Intent intent = new Intent(MainActivity.this, SignIn_page.class);
                                              startActivity(intent);
                                              finish();
                                          }


                                      }
                                  }
                , 5000
        );
    }
}