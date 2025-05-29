package com.lynch.schoolschedule.UI;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.R;
import java.util.List;

public class AssessmentReportActivity extends AppCompatActivity {
    private Repository repository;
    private TextView reportView;
    private AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_report);

        repository = Repository.getInstance(getApplication());
        reportView = findViewById(R.id.report_text);

        List<Assessment> assessments= repository.getAllAssessments();
        adapter.setAssessments(assessments);

    }
}