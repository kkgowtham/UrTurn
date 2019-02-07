package org.urturn.com.urturn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyGroups extends AppCompatActivity {

    List<GroupModel> list;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        list=new ArrayList<>();
        try
        {
            String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String user = userMail.substring(0, userMail.indexOf('@'));
            databaseReference=FirebaseDatabase.getInstance().getReference("JoinedGroups").child(user);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    list.add(dataSnapshot1.getValue(GroupModel.class));
                }
                recyclerView=findViewById(R.id.recyclerview_my_groups);
                recyclerView.setAdapter(new MyGroupsRecyclerAdpater(list,getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
