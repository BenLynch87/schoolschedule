package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.R;

import java.util.List;

public class AssessmentActivity extends Activity implements AssessmentAdapter.OnAssessmentClickListener {

    private Repository repository;
    private RecyclerView recyclerView;
    private AssessmentAdapter assessmentAdapter;
    private List<Assessment> assessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        repository = Repository.getInstance(this);
        recyclerView = findViewById(R.id.allAssessmentList);

        assessments = repository.getAllAssessments();

        assessmentAdapter = new AssessmentAdapter(assessments, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assessmentAdapter);
    }
    public void addAssessment(View view) {
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        startActivity(intent);
    }
    @Override
    public void onAssessmentClick(int position) {
        Assessment selected = assessments.get(position);
        Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailActivity.class);
        intent.putExtra("assessmentId", selected.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> assessments = repository.getAllAssessments();
        assessmentAdapter.setAssessments(assessments);
    }
}
