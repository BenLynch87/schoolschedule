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
import com.lynch.schoolschedule.Assessments.ObjectiveAssessment;
import com.lynch.schoolschedule.Assessments.PerformanceAssessment;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private final Context context;
    private List<Assessment> assessments = new ArrayList<>();

    public AssessmentAdapter(Context context, List<Assessment> assessments) {
        this.context = context;
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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AssessmentDetailActivity.class);
            intent.putExtra("assessmentId", assessment.getId());
            intent.putExtra("assessmentType", assessment instanceof ObjectiveAssessment ? "Objective" : "Performance");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return assessments != null ? assessments.size() : 0;
    }

    public static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        TextView title, type, end;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textAssessmentName);
            type = itemView.findViewById(R.id.textAssessmentType);
            end = itemView.findViewById(R.id.textAssessmentEnd);
        }
    }
}
