package com.tarxsoft.ilaclistesi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Drugs> drugs;

    public Adapter(Context context, List<Drugs> drugs){
        this.layoutInflater = LayoutInflater.from(context);
        this.drugs = drugs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.drug_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.drugName.setText(drugs.get(position).getDrugName());
        holder.drugDesc.setText(drugs.get(position).getDrugDesc());
        holder.drugBarcode.setText(drugs.get(position).getDrugBarcode());
        Picasso.get().load(drugs.get(position).getDrugIcon()).into(holder.drugIcon);
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView drugName, drugDesc, drugBarcode;
        ImageView drugIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugName = itemView.findViewById(R.id.drugName);
            drugDesc = itemView.findViewById(R.id.drugDesc);
            drugBarcode = itemView.findViewById(R.id.drugBarcode);
            drugIcon = itemView.findViewById(R.id.drugIcon);
        }
    }
}
