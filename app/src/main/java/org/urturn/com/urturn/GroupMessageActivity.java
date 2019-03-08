package org.urturn.com.urturn;

import android.content.Intent;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class GroupMessageActivity extends AppCompatActivity {

    ArrayList<GroupAdminPostModel> list;
    RecyclerView messageRecyclerView;
    FloatingActionButton addMessageBtn;
    String post,groupId;
    Toolbar toolbar;
    GroupMessageRecyclerAdapter groupMessageRecyclerAdapter;
    
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();

        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
       pay.put("send_sms", true);
      pay.put("send_email", true);
 } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }
    
    InstapayListener listener;

    
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        // Call the function callInstamojo to start payment here
        toolbar=findViewById(R.id.toolbar_group);
        setSupportActionBar(toolbar);
        list=new ArrayList<>();
        FirebaseUser user;
        user=FirebaseAuth.getInstance().getCurrentUser();
        messageRecyclerView=findViewById(R.id.recycler_view_message);
        addMessageBtn=findViewById(R.id.fab);
        addMessageBtn.hide();
        DatabaseReference messageDbReference;
        groupMessageRecyclerAdapter=new GroupMessageRecyclerAdapter(list,getApplicationContext());
        groupId=getIntent().getStringExtra("groupId");
        try {
            String email=user.getEmail();
            String adminEmail=getIntent().getStringExtra("admin");
            assert email != null;
            if(email.equals(adminEmail))
            {
                addMessageBtn.show();
            }
        }catch (NullPointerException e)
        {
            Log.d("Group",e.getMessage());
        }

        messageDbReference=FirebaseDatabase.getInstance().getReference("GroupsPosts/"+groupId);
        messageDbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    list.add(dataSnapshot1.getValue(GroupAdminPostModel.class));
                    i++;
                }
                messageRecyclerView.setAdapter(groupMessageRecyclerAdapter);
                messageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                messageRecyclerView.setHasFixedSize(true);
                groupMessageRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Group",databaseError.getMessage());
            }
        });



    }

    public void newMessage(View view) {
    /*    final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final ViewGroup nullParent = null;
        final View view1=LayoutInflater.from(this).inflate(R.layout.add_post_dialog_item,nullParent,false);
        final EditText postEditText=view1.findViewById(R.id.edittext_post);
        Button cancelBtn=view1.findViewById(R.id.cancel_post_btn);
        Button addPost=view1.findViewById(R.id.add_post_btn);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog=builder.create();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    post=postEditText.getText().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("GroupMessage/"+groupId);
                    MessageModel messageModel=new MessageModel(post,getTime());
                    databaseReference.child(UUID.randomUUID().toString()).setValue(messageModel);
                    list.add(messageModel);
                    groupMessageRecyclerAdapter.notifyDataSetChanged();
                    dialog.dismiss();
            }
        });
        dialog.show();*/
        Intent intent=new Intent(this,PostMessageActivity.class);
        intent.putExtra("groupid",groupId);
        startActivity(intent);

    }

    public String getTime() {
        String date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        return date;
    }
    public void openChatActivity(View v){
        Intent intent=new Intent(this,ChatActivityGroup.class);
        intent.putExtra("groupid",groupId);
        startActivity(intent);
    }
}
