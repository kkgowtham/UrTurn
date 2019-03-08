package org.urturn.com.urturn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBloodRecyclerAdapter extends RecyclerView.Adapter<SearchBloodRecyclerAdapter.MyViewHolder> {

    ArrayList<UserModel> list;
    Context context;

    public SearchBloodRecyclerAdapter(Context context,ArrayList<UserModel> list)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.search_blood_single_item_users,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            UserModel model=list.get(i);
            myViewHolder.stateTV.setText(model.getName());
            myViewHolder.cityTV.setText(model.getCity());
            myViewHolder.phoneNoTV.setText(model.getPhoneNo());
            myViewHolder.nameTV.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV,stateTV,cityTV,phoneNoTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.name_single_item);
            stateTV=itemView.findViewById(R.id.state_single_item);
            cityTV=itemView.findViewById(R.id.city_single_item);
            phoneNoTV=itemView.findViewById(R.id.phone_no_single_item);
        }
    }
}
