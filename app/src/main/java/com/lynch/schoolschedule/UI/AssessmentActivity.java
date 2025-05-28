package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.R;

import java.util.List;

public class AssessmentActivity extends AppCompatActivity {
    private Repository repository;
    private AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        RecyclerView recyclerView = findViewById(R.id.allAssessmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);

        repository = new Repository(getApplication());
        repository.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });
    }

    public void addAssessment(View view) {
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        startActivity(intent);
    }
}
