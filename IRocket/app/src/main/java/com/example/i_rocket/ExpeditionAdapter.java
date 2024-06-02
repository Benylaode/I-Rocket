package com.example.i_rocket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ExpeditionAdapter extends RecyclerView.Adapter<ExpeditionAdapter.ViewHolder> {
    private final List<Expedition> expeditions;
    private String str;

    public ExpeditionAdapter(@NonNull List<Expedition> objects,  String str) {
        this.expeditions = objects;
        this.str = str;
    }
    @NonNull
    @Override
    public ExpeditionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mission_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expedition expedition = expeditions.get(position);
        holder.title.setText(expedition.getName());
        holder.start.setText(expedition.getStart().substring(0,10));
        if(expedition.getEnd() != null ){
            holder.end.setText(expedition.getEnd().substring(0,10));
        }else {
            holder.end.setText("on going");
        }
        holder.title.setOnClickListener(e->{
            Intent intent = new Intent(holder.context, ExpeditionActivity.class);
            intent.putExtra("EXP", expedition);
            intent.putExtra("fragment", str);
            holder.context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return expeditions.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView start;
        private final TextView end;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start_ex);
            title = itemView.findViewById(R.id.title_ex);
            end = itemView.findViewById(R.id.end_ex);
            context = itemView.getContext();

        }


    }
}
