package org.urturn.com.urturn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivityGroup extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText messageEditText;
    Button sendBtn;
    ChatRecyclerAdapter adapter;
    ArrayList<ChatModel> list;
    ArrayList<String> keyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        recyclerView=findViewById(R.id.chat_recycler_view);
        messageEditText=findViewById(R.id.message_editext);
        sendBtn=findViewById(R.id.button_send);
        list=new ArrayList<>();
        String groupId=getIntent().getStringExtra("groupid");
        keyList=new ArrayList<>();
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Chat/"+groupId);
            databaseReference.limitToLast(20).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        list.add(dataSnapshot1.getValue(ChatModel.class));
                    }
                    adapter = new ChatRecyclerAdapter(list, getApplicationContext());
                    LinearLayoutManager layout=new LinearLayoutManager(getApplicationContext());
                    layout.setReverseLayout(true);
                    recyclerView.setLayoutManager(layout);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    sendBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message=messageEditText.getText().toString();
            if(message.trim().equals(""))
            {
               return;
            }
            final ChatModel chatModel=new ChatModel();
            chatModel.setSentBy(UserDetailModel.userName);
            chatModel.setMessage(message);
            chatModel.setTimeStamp(getDateTime());
            chatModel.setSentByEmail(UserDetailModel.email);
           // int j=getDateTime().indexOf(" ",getDateTime().indexOf(" ")+1);
            databaseReference.push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    list.add(chatModel);
                    messageEditText.setText("");
                    adapter.notifyDataSetChanged();
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                }
            });
        }
    });
    }
    public String getDateTime()
    {
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
}
