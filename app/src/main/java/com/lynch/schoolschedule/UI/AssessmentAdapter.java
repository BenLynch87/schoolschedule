package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Assessments.ObjectiveAssessment;
import com.lynch.schoolschedule.Assessments.PerformanceAssessment;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private final Context context;
    private final OnAssessmentClickListener listener;
    private List<Assessment> assessments = new ArrayList<>();

    public AssessmentAdapter(List<Assessment> assessments, OnAssessmentClickListener listener, Context context) {
        this.context = context;
        this.listener = listener;
        this.assessments = assessments;
    }

    public void setAssessments(List<Assessment> list) {
        this.assessments = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_item, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.title.setText(assessment.getTitle());
        holder.type.setText(assessment instanceof ObjectiveAssessment ? "Objective" : "Performance");
        holder.end.setText(assessment.getDueDate().toString());
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.itemBackground));


        holder.itemView.setOnClickListener(v -> listener.onAssessmentClick(position));
    }

    @Override
    public int getItemCount() {
        return assessments != null ? assessments.size() : 0;
    }

    public static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        TextView title, type, end;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.itemBackground));
            title = itemView.findViewById(R.id.textAssessmentName);
            type = itemView.findViewById(R.id.textAssessmentType);
            end = itemView.findViewById(R.id.textAssessmentEnd);
        }
    }

    public interface OnAssessmentClickListener {
        void onAssessmentClick(int position);
    }
}
