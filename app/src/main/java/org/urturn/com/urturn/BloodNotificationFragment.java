package org.urturn.com.urturn;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodNotificationFragment extends Fragment {


    ArrayList<BloodDonateModel> list;
    RecyclerView recyclerView;
    String h;
    String cityName;
    Context context;
    FloatingActionButton floatingActionButton;

    public BloodNotificationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_blood_notification, container, false);
        recyclerView=view.findViewById(R.id.recycler_bloood_requests);
        floatingActionButton=view.findViewById(R.id.fab_requestblood);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,RequestBloodActivity.class));
            }
        });
        list=new ArrayList<BloodDonateModel>();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        try {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            assert email != null;
            int i = email.indexOf('@');
            h = email.substring(0, i);
            databaseReference = databaseReference.child(h);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    try {
                        assert userModel != null;
                        cityName=userModel.getCity();
                        retrieveData(cityName);
                    }catch (Exception e)
                    {
                        Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
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
        return view;
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
                BloodRequestsRecyclerAdapter adapter=new BloodRequestsRecyclerAdapter(list,context);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
