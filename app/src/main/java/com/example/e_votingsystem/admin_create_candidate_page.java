package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_create_candidate_page extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private adminadapter.RecyclerViewClickListener  listener;
    private adminadapter adminadapter;
    private ArrayList<Election> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_candidate_page);

        recyclerView=findViewById(R.id.electionList);

        databaseReference= FirebaseDatabase.getInstance().getReference("Election");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listener =new adminadapter.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(),Results.class);
                intent.putExtra("electionName",list.get(position).getName());
                startActivity(intent);
            }
        };
        list=new ArrayList<>();
        adminadapter = new adminadapter(this, list, listener);
        recyclerView.setAdapter(adminadapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Election election=dataSnapshot.getValue(Election.class);
                    list.add(election);
                }
                adminadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_create_candidate_page.this, Results.class));
            }
        });

    }
}