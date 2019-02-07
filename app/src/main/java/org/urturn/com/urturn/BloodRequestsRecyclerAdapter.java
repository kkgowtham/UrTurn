package org.urturn.com.urturn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BloodRequestsRecyclerAdapter extends RecyclerView.Adapter<BloodRequestsRecyclerAdapter.MyViewHolder> {
    ArrayList<BloodDonateModel> list;
    Context c;
    public BloodRequestsRecyclerAdapter(ArrayList<BloodDonateModel> list,Context c)
    {
        this.list=list;
        this.c=c;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(c).inflate(R.layout.custom_blood_requests_single_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        BloodDonateModel model=list.get(i);
        myViewHolder.postedTime.setText(model.getTimeStamp());
        myViewHolder.cityName.setText(model.getCity());
        myViewHolder.unitsRequired.setText(String.valueOf(model.getNoOfUnits()));
        myViewHolder.bloodGroup.setText(model.getBloodGroup());
        myViewHolder.timeLeft.setText(model.getWithinTime());
        myViewHolder.hospitalName.setText(model.getHospitalName());
        myViewHolder.patientName.setText(model.getPatientName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView hospitalName,timeLeft,patientName,bloodGroup,unitsRequired,cityName,postedTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalName=itemView.findViewById(R.id.single_hospital_name);
            timeLeft=itemView.findViewById(R.id.single_time_within);
            patientName=itemView.findViewById(R.id.single_patient_name);
            bloodGroup=itemView.findViewById(R.id.single_blood_group);
            unitsRequired=itemView.findViewById(R.id.single_no_of_units);
            cityName=itemView.findViewById(R.id.single_city_name);
            postedTime=itemView.findViewById(R.id.single_posted_time);
        }
    }
}
