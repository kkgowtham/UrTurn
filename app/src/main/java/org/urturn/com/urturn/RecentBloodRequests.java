package org.urturn.com.urturn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;


public class RecentBloodRequests extends AppCompatActivity {

    ArrayList<BloodDonateModel> list;
    RecyclerView recyclerView;
    String h;
    String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_blood_requests);
        recyclerView=findViewById(R.id.recycler_bloood_requests);
        list=new ArrayList<BloodDonateModel>();

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        try {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            int i = email.indexOf('@');
            h = email.substring(0, i);
            databaseReference = databaseReference.child(h);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    try {
                        cityName=userModel.getCity();
                        retrieveData(cityName);
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void retrieveData(String cityName)
    {
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("RequestBasedOnCities").child(cityName);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    list.add(dataSnapshot1.getValue(BloodDonateModel.class));
                }
                Collections.reverse(list);
                BloodRequestsRecyclerAdapter adapter=new BloodRequestsRecyclerAdapter(list,getApplicationContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
