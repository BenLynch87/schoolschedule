package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.R;

import java.util.List;

public class AssessmentReportAdapter extends RecyclerView.Adapter<AssessmentReportAdapter.ReportViewHolder> {
    private final Context context;
    private final List<Assessment> assessmentList;
    private final int filterType;

    public AssessmentReportAdapter(Context context, List<Assessment> data, int filterType) {
        this.context = context;
        this.assessmentList = data;
        this.filterType = filterType;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Assessment assessment = assessmentList.get(position);
        holder.title.setText(assessment.getTitle());
        holder.type.setText(assessment.getClass().getSimpleName());
    }

    @Override
    public int getItemCount() {
        return assessmentList != null ? assessmentList.size() : 0;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView title, type;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textAssessmentName);
            type = itemView.findViewById(R.id.textAssessmentType);
        }
    }
}
