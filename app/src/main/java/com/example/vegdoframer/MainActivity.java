package com.example.vegdoframer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            Fauth = FirebaseAuth.getInstance();
            if(Fauth.getCurrentUser()!=null){

                Fauth=FirebaseAuth.getInstance();

                Intent i = new Intent(MainActivity.this,MainPage.class);

                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String role = snapshot.getValue(String.class);
                        Log.d( "onDataChange: ","Admin here");
                        if(role.equals("Framer")){
                            i.putExtra("All_in_role","Framer");
                            startActivity(i);

                            finish();

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }else {

                Intent intent = new Intent(MainActivity.this, SignIn_page.class);
                startActivity(intent);
                finish();
            }



        }}
    ,5000
            );
}
}