package com.icst.professionalOrientation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;


public class ProfAdapter extends RecyclerView.Adapter<ProfAdapter.ViewHolder> {
    private List<Profession> mDataset;
    private LayoutInflater inf;

    ProfAdapter(Context context, List<Profession> prof)
    {
        this.mDataset = prof;
        this.inf = LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final TextView description;
        final TextView profFirst;
        final TextView profSecond;
        final TextView profThird;
        public ViewHolder(View v)
        {
            super(v);
            name = v.findViewById(R.id.oneProfName);
            description = v.findViewById(R.id.oneProfBriefDesc);
            profFirst = v.findViewById(R.id.content_competence);
            profSecond = v.findViewById(R.id.content_salary);
            profThird = v.findViewById(R.id.content_links);
        }
    }

    @Override
    public ProfAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = inf.inflate(R.layout.profession, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Profession prof = mDataset.get(position);
        holder.name.setText(prof.getName());
        holder.description.setText(prof.getDescription());
        holder.profFirst.setText(prof.getProfFirst());
        holder.profSecond.setText(prof.getProfSecond());
        holder.profThird.setText(prof.getProfThird());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
