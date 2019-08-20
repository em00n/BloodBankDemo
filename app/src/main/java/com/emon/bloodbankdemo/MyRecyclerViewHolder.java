package com.emon.bloodbankdemo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nameTV,distTV,bloodTV,lastDonateTV;
    ImageView imageView;
    OnLongClickListener onLongClickListener;
   View.OnClickListener onClickListener;


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        nameTV=(TextView) itemView.findViewById(R.id.nameTV);
        distTV=(TextView)itemView.findViewById(R.id.distTV);
        bloodTV=(TextView)itemView.findViewById(R.id.bloodTV);
        lastDonateTV=(TextView)itemView.findViewById(R.id.lastdonetTV);
        imageView=itemView.findViewById(R.id.callIV);
    }


    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }
}
