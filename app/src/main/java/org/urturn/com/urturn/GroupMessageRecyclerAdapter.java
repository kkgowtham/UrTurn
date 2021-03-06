package org.urturn.com.urturn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupMessageRecyclerAdapter extends RecyclerView.Adapter<GroupMessageRecyclerAdapter.GroupsViewHolder> {

     ArrayList<GroupAdminPostModel> list;
    Context c;

    public GroupMessageRecyclerAdapter (ArrayList<GroupAdminPostModel> list,Context c)
    {
        this.list=list;
        this.c=c;
    }
    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(c).inflate(R.layout.single_item_message_model_group,viewGroup,false);
        return new GroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder groupsViewHolder, int i) {
        GroupAdminPostModel model=list.get(i);
        groupsViewHolder.message_tv.setText(model.getDesc());
        groupsViewHolder.time_tv.setText(model.getTimestamp());
        Picasso.get().load(model.getUrl()).placeholder(R.drawable.ic_home_black_24dp).into(groupsViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GroupsViewHolder extends RecyclerView.ViewHolder {
        TextView message_tv,time_tv;
        ImageView imageView;
        public GroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            message_tv=itemView.findViewById(R.id.message_textview);
            time_tv=itemView.findViewById(R.id.time_message_textview);
            imageView=itemView.findViewById(R.id.image_view_post_message);
        }
    }
}
