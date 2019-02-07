package org.urturn.com.urturn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGroupsRecyclerAdpater extends RecyclerView.Adapter<MyGroupsRecyclerAdpater.MyGroupsViewHolder> {

    List<GroupModel> list;
    Context context;
    public MyGroupsRecyclerAdpater(List<GroupModel> list, Context c)
    {
        this.list=list;
        context=c;
    }
    @NonNull
    @Override
    public MyGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.single_item_mygroups,viewGroup,false);
        return new MyGroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGroupsViewHolder myGroupsViewHolder, int i) {
        final GroupModel model=list.get(i);
            myGroupsViewHolder.groupName.setText(model.getGroupName());
        Glide.with(context).load(model.getGroupImageUrl()).into(myGroupsViewHolder.groupDp);
        myGroupsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,GroupMessageActivity.class);
                intent.putExtra("admin",model.getCretedBy());
                intent.putExtra("groupId",model.getGroupId());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
     class MyGroupsViewHolder extends RecyclerView.ViewHolder{

        CircularImageView groupDp;
        TextView groupName;
         public MyGroupsViewHolder(@NonNull View itemView) {
             super(itemView);
             groupName=itemView.findViewById(R.id.group_name_my_groups);
             groupDp=(CircularImageView)itemView.findViewById(R.id.group_dp_mygroups);
         }
     }

}
