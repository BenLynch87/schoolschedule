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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_report);

        repository = new Repository(getApplication());
        reportView = findViewById(R.id.report_text);

        repository.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                StringBuilder reportBuilder = new StringBuilder();
                for (Assessment a : assessments) {
                    reportBuilder.append(a.getReportLine()).append("\n");
                }
                reportView.setText(reportBuilder.toString());
            }
        });
    }
}