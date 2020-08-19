package com.tarxsoft.ilaclistesi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    LayoutInflater layoutInflater;
    List<Drugs> drugs;
    List<Drugs> drugsFilter;

    public Adapter(Context context, List<Drugs> drugs){
        this.layoutInflater = LayoutInflater.from(context);
        this.drugs = drugs;
        this.drugsFilter = drugs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.drug_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.drugName.setText(drugsFilter.get(position).getDrugName());
        holder.drugDesc.setText(drugsFilter.get(position).getDrugDesc());
        holder.drugBarcode.setText(drugsFilter.get(position).getDrugBarcode());
//        Picasso.get().load(drugsFilter.get(position).getDrugIcon()).into(holder.drugIcon);
    }

    @Override
    public int getItemCount() {
        return drugsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint.toString().isEmpty()){
                    drugsFilter = drugs;
                }else{
                    List<Drugs> drugFilterList = new ArrayList<>();
                    for (Drugs drugs: drugs){
                        if (drugs.getDrugName().toLowerCase().contains(constraint.toString().toLowerCase())){
                            drugFilterList.add(drugs);
                        }
                    }

                    drugsFilter = drugFilterList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = drugsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                drugsFilter = (ArrayList<Drugs>) results.values;
                notifyDataSetChanged();
            }
        };
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
