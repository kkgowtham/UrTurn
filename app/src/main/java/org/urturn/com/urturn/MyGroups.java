package org.urturn.com.urturn;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyGroups extends Fragment {

    List<GroupModel> list;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Context context;
    Toolbar toolbar;
    ImageView search_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my_groups,container,false);
        recyclerView=view.findViewById(R.id.recyclerview_my_groups);
        toolbar=view.findViewById(R.id.toolbar_mygroup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        }
        search_btn=view.findViewById(R.id.search_button_groups);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,SearchGroup.class);
                context.startActivity(intent);
            }
        });
        list=new ArrayList<>();
        try
        {
            String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String user = userMail.substring(0, userMail.indexOf('@'));
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Users/"+user);
            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel userModel=dataSnapshot.getValue(UserModel.class);
                    UserDetailModel.email=userModel.getEmail();
                    UserDetailModel.userName=userModel.getName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
                recyclerView.setAdapter(new MyGroupsRecyclerAdpater(list,context));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
