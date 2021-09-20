package com.ashishgaike.covidcasestracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishgaike.covidcasestracker.api.CountryData;
import com.ashishgaike.covidcasestracker.api.StateData;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StateAdapter extends RecyclerView.Adapter <StateAdapter.stateViewHolder>{

    private Context context;
    private List<CountryData> list;

    public StateAdapter(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.state_item_layout,parent, false);

        return new stateViewHolder(view);
    }

    public void filterList(List<CountryData> filterList ){
        list = filterList;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull StateAdapter.stateViewHolder holder, int position) {

        CountryData data = list.get(position);

        holder.statecases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.statename.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String, String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);



        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class stateViewHolder extends RecyclerView.ViewHolder {

        private TextView sno, statename,statecases;
        private ImageView imageView;



        public stateViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            statename = itemView.findViewById(R.id.statename);
            statecases = itemView.findViewById(R.id.statecases);
            imageView = itemView.findViewById(R.id.flag);

        }
    }
}
