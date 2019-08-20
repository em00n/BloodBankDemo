package com.emon.bloodbankdemo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PostRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView showBloodTV,showDateTV,showLocationTV;
    OnLongClickListener onLongClickListener;
    View.OnClickListener onClickListener;


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PostRecyclerViewHolder(View itemView) {
        super(itemView);
        showBloodTV=(TextView) itemView.findViewById(R.id.showBloodTV);
        showDateTV=(TextView)itemView.findViewById(R.id.showDateTV);
        showLocationTV=(TextView)itemView.findViewById(R.id.showLocationTV);
    }


    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }
}
