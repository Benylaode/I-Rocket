package com.example.i_rocket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExpeditionAdapter extends RecyclerView.Adapter<ExpeditionAdapter.ViewHolder> {
    private final List<Expedition> expeditions;

    public ExpeditionAdapter(@NonNull List<Expedition> objects) {
        this.expeditions = objects;
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
        holder.end.setText(expedition.getEnd().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return expeditions.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private  TextView title;
        private  TextView start;
        private  TextView end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start_ex);
            title = itemView.findViewById(R.id.title_ex);
            end = itemView.findViewById(R.id.end_ex);
        }


    }
}
