package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private List<Object> results = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setResults(List<Object> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public void clearResults() {
        this.results.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Object item = results.get(position);
        String displayText = "";
        View.OnClickListener clickListener = null;

        if (item instanceof TermEntity) {
            TermEntity term = (TermEntity) item;
            displayText = "Term: " + term.getTermName();
            clickListener = v -> {
                Intent intent = new Intent(context, TermDetailActivity.class);
                intent.putExtra("termId", term.getId());
                context.startActivity(intent);
            };
        } else if (item instanceof ClassEntity) {
            ClassEntity classEntity = (ClassEntity) item;
            displayText = "Class: " + classEntity.getClassName();
            clickListener = v -> {
                Intent intent = new Intent(context, ClassDetailActivity.class);
                intent.putExtra("classId", classEntity.getId());
                context.startActivity(intent);
            };
        } else if (item instanceof Assessment) {
            Assessment assessment = (Assessment) item;
            displayText = "Assessment: " + assessment.getTitle();
            clickListener = v -> {
                Intent intent = new Intent(context, AssessmentDetailActivity.class);
                intent.putExtra("assessmentId", assessment.getId());
                context.startActivity(intent);
            };
        }

        holder.textView.setText(displayText);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
