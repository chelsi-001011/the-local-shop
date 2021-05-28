package com.example.thelocalshopfinal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<com.example.thelocalshopfinal.TimelineAdapter.ViewHolder> {

    private List<TimelineRestStruct> TimelineRests;
    private Context context;
    public TimelineAdapter(List<TimelineRestStruct> listRests, Context context) {
        this.TimelineRests = listRests;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_rest_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TimelineRestStruct Restinfo=TimelineRests.get(position);

                    holder.RestName.setText(Restinfo.getStore());
                    holder.grand_total.setText(Restinfo.getCost());
                    holder.status.setText(Restinfo.getStatus());





    }

    @Override
    public int getItemCount() {
        return TimelineRests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView RestName;
        public ImageView imageView;
        public CardView card;
        public TextView status;
        public TextView grand_total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RestName =itemView.findViewById(R.id.timeline_rest_name);
            imageView=itemView.findViewById(R.id.timeline_rest_image);
            card=itemView.findViewById(R.id.timeline_rest_card);
            status = itemView.findViewById(R.id.timeline_rest_status);
            grand_total = itemView.findViewById(R.id.timeline_rest_grand_total);

        }
    }


}
