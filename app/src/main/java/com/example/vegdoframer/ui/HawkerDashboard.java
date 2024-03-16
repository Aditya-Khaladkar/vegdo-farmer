package com.example.vegdoframer.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vegdoframer.R;
import com.example.vegdoframer.auth.SignIn_page;
import com.example.vegdoframer.util.LocationList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HawkerDashboard extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    TextInputLayout hawkerVegNameEDT, hawkerVegPriceEDT;
    ImageView hawkerVegImage;
    Button btnAddVegetable;
    AutoCompleteTextView autoCompleteLocation;
    Uri imageUri;
    String base64Image, hawkerLocation;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hawker_dashboard);

        FAuth = FirebaseAuth.getInstance();

        findViewById(R.id.hawkerLogout).setOnClickListener(v->{
            FAuth.signOut();
            startActivity(new Intent(getApplicationContext(), SignIn_page.class));
            finish();
        });

        hawkerVegNameEDT = findViewById(R.id.hawkerVegNameEDT);
        hawkerVegPriceEDT = findViewById(R.id.hawkerVegPriceEDT);
        hawkerVegImage = findViewById(R.id.hawkerVegImage);
        btnAddVegetable = findViewById(R.id.btnAddVegetable);
        autoCompleteLocation = findViewById(R.id.autoCompleteLocation);

        String[] nagpurLocations = new LocationList().getLocationArray();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                nagpurLocations
        );

        autoCompleteLocation.setAdapter(adapter);
        autoCompleteLocation.setOnItemClickListener((adapterView, view, i, l) -> {
            hawkerLocation = (String) adapterView.getItemAtPosition(i);
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("vegetable_list").push();

        // Open the gallery when the ImageView is clicked
        hawkerVegImage.setOnClickListener(v -> openGallery());

        btnAddVegetable.setOnClickListener(v -> {
            String vegetableName = Objects.requireNonNull(hawkerVegNameEDT.getEditText()).getText().toString();
            String vegetablePrice = Objects.requireNonNull(hawkerVegPriceEDT.getEditText()).getText().toString();

            // Create a HashMap to store the vegetable data
            Map<String, Object> vegetableData = new HashMap<>();
            vegetableData.put("name", vegetableName);
            vegetableData.put("price", vegetablePrice);
            //vegetableData.put("image", base64Image);
            vegetableData.put("hawkerLocation", hawkerLocation);
            vegetableData.put("uid", FAuth.getCurrentUser().getUid());

            databaseReference.setValue(vegetableData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(HawkerDashboard.this, "data uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HawkerDashboard.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            hawkerVegImage.setImageURI(imageUri);

            // Convert the image to Base64
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                // Now you can use the base64Image string to upload the image to Firebase
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
