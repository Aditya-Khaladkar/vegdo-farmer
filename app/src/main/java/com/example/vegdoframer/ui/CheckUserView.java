package com.example.vegdoframer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.vegdoframer.MainActivity;
import com.example.vegdoframer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckUserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userUid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String userType = dataSnapshot.child("UserType").getValue(String.class);
                            // You can use userType here

                            if (userType.equals("Hawker")) {
                                Intent i = new Intent(getApplicationContext(), HawkerDashboard.class);
                                startActivity(i);
                                finish();
                            }
                            if (userType.equals("User")) {
                                Intent i = new Intent(getApplicationContext(), MainPage.class);
                                startActivity(i);
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
            }
        },2000);
    }
}