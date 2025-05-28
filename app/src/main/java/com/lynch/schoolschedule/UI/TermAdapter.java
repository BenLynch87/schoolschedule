package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private final Context context;
    private List<TermEntity> terms = new ArrayList<>();

    public TermAdapter(Context context) {
        this.context = context;
    }

    public void setTerms(List<TermEntity> termList) {
        this.terms = termList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.term_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        TermEntity term = terms.get(position);
        holder.name.setText(term.getTermName());
        holder.start.setText(term.getStartDate());
        holder.end.setText(term.getEndDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TermDetailActivity.class);
            intent.putExtra("termId", term.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return terms != null ? terms.size() : 0;
    }

    public static class TermViewHolder extends RecyclerView.ViewHolder {
        TextView name, start, end;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textTermName);
            start = itemView.findViewById(R.id.textTermStart);
            end = itemView.findViewById(R.id.textTermEnd);
        }
    }
}
