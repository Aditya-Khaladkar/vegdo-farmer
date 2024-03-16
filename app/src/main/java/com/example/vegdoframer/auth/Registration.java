package com.example.vegdoframer.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.vegdoframer.MainActivity;
import com.example.vegdoframer.ui.CheckUserView;
import com.example.vegdoframer.ui.MainPage;
import com.example.vegdoframer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    TextInputLayout Fname, Email, Pass;

    Button signup;

    FirebaseAuth FAuth;
    DatabaseReference databaseReference;

    String fname, emailid, password;

    RadioButton rbHawker, rbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signup = (Button) findViewById(R.id.Signup);
        Fname = findViewById(R.id.Fname);
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.Pass);

        rbHawker = findViewById(R.id.rbHawker);
        rbUser = findViewById(R.id.rbUser);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();

                if (fname.isEmpty() || emailid.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog mDialog = new ProgressDialog(Registration.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Registration in progress please wait......");
                mDialog.show();

                FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(useridd);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("First Name", fname);
                            hashMap.put("EmailId", emailid);
                            hashMap.put("Password", password);
                            // Add user type
                            hashMap.put("UserType", rbHawker.isChecked() ? "Hawker" : "User");

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                        Intent intent = new Intent(Registration.this, CheckUserView.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(Registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(Registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
