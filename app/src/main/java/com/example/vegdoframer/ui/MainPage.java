package com.example.vegdoframer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.vegdoframer.R;
import com.example.vegdoframer.adapter.HawkerListAdapter;
import com.example.vegdoframer.adapter.VegetableListAdapter;
import com.example.vegdoframer.auth.SignIn_page;
import com.example.vegdoframer.model.HawkerListModel;
import com.example.vegdoframer.model.VegetableListModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    String name,uid,last;
    ImageView userLogout;
    DatabaseReference databaseeReference;
    FirebaseUser user;

    RecyclerView vegetableItemRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        findViewById(R.id.userLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), SignIn_page.class));
            finish();
        });

        // initialize recyclerView
        vegetableItemRecyclerView = findViewById(R.id.vegetableItemRecyclerView);


        vegetableItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vegetableItemRecyclerView.setHasFixedSize(true);

        List<HawkerListModel> dataList = new ArrayList<>();
        HawkerListAdapter adapter = new HawkerListAdapter(dataList);
        vegetableItemRecyclerView.setAdapter(adapter);

        databaseeReference = FirebaseDatabase.getInstance().getReference().child("vegetable_list");
        databaseeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<HawkerListModel> vegetables = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String price = snapshot.child("price").getValue(String.class);
                        String location = snapshot.child("hawkerLocation").getValue(String.class);
                        // Use name, price, and location as needed
                        HawkerListModel model = new HawkerListModel(name, price, location);
                        vegetables.add(model);
                    }
                    dataList.clear();
                    dataList.addAll(vegetables);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

    }

}