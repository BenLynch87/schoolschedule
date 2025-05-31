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
import com.lynch.schoolschedule.R;

import java.util.List;
import java.util.Map;

public class AssessmentReportAdapter extends RecyclerView.Adapter<AssessmentReportAdapter.AssessmentReportViewHolder> {

    private final Context context;
    private List<Assessment> assessments;
    private Map<Integer, String> classNamesMap;
    private Map<Integer, Integer> termIdsMap;
    private Map<Integer, String> termNamesMap;

    public AssessmentReportAdapter(Context context,
                                   List<Assessment> assessments,
                                   Map<Integer, String> classNamesMap,
                                   Map<Integer, Integer> termIdsMap,
                                   Map<Integer, String> termNamesMap) {
        this.context = context;
        this.assessments = assessments;
        this.classNamesMap = classNamesMap;
        this.termIdsMap = termIdsMap;
        this.termNamesMap = termNamesMap;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssessmentReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_report_item, parent, false);
        return new AssessmentReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentReportViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);

        // Existing bindings
        holder.textAssessmentName.setText(assessment.getTitle());
        String className = classNamesMap.getOrDefault(assessment.getClassId(), "Unknown Class");
        holder.textClassName.setText(className);
        int termId = termIdsMap.getOrDefault(assessment.getClassId(), -1);
        String termName = termNamesMap.getOrDefault(termId, "Unknown Term");
        holder.textTermName.setText(termName);
        holder.textDueDateTime.setText(assessment.getDueDate());

        // NEW: Click listener
        holder.itemView.setOnClickListener(v -> {
            // Use the context to start the detail activity
            Intent intent = new Intent(context, AssessmentDetailActivity.class);
            intent.putExtra("assessmentId", assessment.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return assessments != null ? assessments.size() : 0;
    }

    public static class AssessmentReportViewHolder extends RecyclerView.ViewHolder {
        TextView textAssessmentName;
        TextView textClassName;
        TextView textTermName;
        TextView textDueDateTime;

        public AssessmentReportViewHolder(@NonNull View itemView) {
            super(itemView);
            textAssessmentName = itemView.findViewById(R.id.textAssessmentName);
            textClassName = itemView.findViewById(R.id.textClassName);
            textTermName = itemView.findViewById(R.id.textTermName);
            textDueDateTime = itemView.findViewById(R.id.textDueDateTime);
        }
    }
}
