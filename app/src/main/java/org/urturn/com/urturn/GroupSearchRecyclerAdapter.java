package org.urturn.com.urturn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GroupSearchRecyclerAdapter extends RecyclerView.Adapter<GroupSearchRecyclerAdapter.MyViewHolder> {

    DatabaseReference databaseReference;
    private ArrayList<GroupModel> list;
    private Context context;
    private ArrayList<String> keyList;
    public GroupSearchRecyclerAdapter(ArrayList<GroupModel> list,Context context)
    {
        this.context=context;
        this.list=list;
        keyList=new ArrayList<>();
        try
        {
            String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String user = userMail.substring(0, userMail.indexOf('@'));
            databaseReference=FirebaseDatabase.getInstance().getReference("JoinedGroups").child(user);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.group_single_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final GroupModel groupModel=list.get(i);
        myViewHolder.desc.setText(groupModel.getGroupDesc());
        myViewHolder.title.setText(groupModel.getGroupName());
        String groupId=groupModel.getGroupId();
        keyList.add(groupModel.getGroupId());
        myViewHolder.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(groupModel.getGroupId()).setValue(groupModel);
                Toast.makeText(context,"Group Joined",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,MyGroups.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        Button joinBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_single);
            desc=itemView.findViewById(R.id.desc_single);
            joinBtn=itemView.findViewById(R.id.join_btn);
        }
    }
}
