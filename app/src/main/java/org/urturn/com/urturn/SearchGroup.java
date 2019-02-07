package org.urturn.com.urturn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchGroup extends AppCompatActivity {

    ArrayList<GroupModel> list;
    ArrayList<String> groupIdList;
   private EditText editText;
   RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        list=new ArrayList<>();
        groupIdList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview_group_search);
        editText=findViewById(R.id.editText_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    list.clear();
                    groupIdList.clear();
                    try {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups");
                        Query query = databaseReference.orderByChild("groupName");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    GroupModel model = new GroupModel();
                                    model = dataSnapshot1.getValue(GroupModel.class);
                                    assert model != null;
                                    if (model.groupName.contains(editText.getText().toString())) {
                                        if(!groupIdList.contains(model.getGroupId())) {
                                            list.add(dataSnapshot1.getValue(GroupModel.class));
                                            groupIdList.add(model.getGroupId());
                                        }
                                    } else if (model.groupName.toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                                        if(!groupIdList.contains(model.getGroupId())) {
                                            list.add(dataSnapshot1.getValue(GroupModel.class));
                                            groupIdList.add(model.getGroupId());
                                        }                                    }
                                }
                                GroupSearchRecyclerAdapter adapter = new GroupSearchRecyclerAdapter(list, getApplicationContext());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void searchGroups(View view) {
        list.clear();
        if(!editText.toString().equals("")) {
            try {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups");
                Query query = databaseReference.orderByChild("groupName");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            GroupModel model = new GroupModel();
                            model = dataSnapshot1.getValue(GroupModel.class);
                            assert model != null;
                            if (model.groupName.contains(editText.getText().toString())) {
                                if(!groupIdList.contains(model.getGroupId())) {
                                    list.add(dataSnapshot1.getValue(GroupModel.class));
                                    groupIdList.add(model.getGroupId());
                                }
                            } else if (model.groupName.toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                                if(!groupIdList.contains(model.getGroupId())) {
                                    list.add(dataSnapshot1.getValue(GroupModel.class));
                                    groupIdList.add(model.getGroupId());
                                }                            }
                        }
                        GroupSearchRecyclerAdapter adapter = new GroupSearchRecyclerAdapter(list, getApplicationContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
