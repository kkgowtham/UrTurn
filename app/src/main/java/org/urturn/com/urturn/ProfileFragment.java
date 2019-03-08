package org.urturn.com.urturn;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    Context context;
    public ProfileFragment() {
        // Required empty public constructor
    }
    FirebaseAuth mAuth;
    CircularImageView profilePic;
    TextView nameTv,phoneTv,addressTv,emailTv,cityTv,stateTv,bloodGroupTv;
    Button editProfileBtn,signOutBtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic=view.findViewById(R.id.profile_pic);
        mAuth=FirebaseAuth.getInstance();
        signOutBtn=view.findViewById(R.id.signout_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context,GoogleSignInActivity.class));
            }
        });
        nameTv=view.findViewById(R.id.profile_name);
        phoneTv=view.findViewById(R.id.profile_phoneno);
        addressTv=view.findViewById(R.id.profile_address);
        emailTv=view.findViewById(R.id.profile_email);
        cityTv=view.findViewById(R.id.profile_city);
        stateTv=view.findViewById(R.id.profile_state);
        bloodGroupTv=view.findViewById(R.id.profile_blood_group);
        editProfileBtn=view.findViewById(R.id.update_profile_btn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final FirebaseUser user = mAuth.getCurrentUser();
        try {
            String email=mAuth.getCurrentUser().getEmail();
            int i=email.indexOf('@');
             String h=email.substring(0,i);
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(h);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel userModel=dataSnapshot.getValue(UserModel.class);

                    Picasso.get().load(userModel.getPicUrl()).placeholder(R.drawable.ic_account_circle_black_24dp).into(profilePic);
                    nameTv.setText(userModel.getName());
                    emailTv.setText(userModel.getEmail());
                    phoneTv.setText(userModel.getPhoneNo());
                    addressTv.setText(userModel.getAddress());
                    cityTv.setText(userModel.getCity());
                    stateTv.setText(userModel.getState());
                    bloodGroupTv.setText(userModel.getBloodGroup());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return view;
    }


}
