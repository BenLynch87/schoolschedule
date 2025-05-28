package com.lynch.schoolschedule.UI;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.lynch.schoolschedule.Assessments.ObjectiveAssessment;
import com.lynch.schoolschedule.Assessments.PerformanceAssessment;
import com.lynch.schoolschedule.Database.DatabaseManager;
import com.lynch.schoolschedule.Entities.ChecklistItemEntity;
import com.lynch.schoolschedule.R;

public class AssessmentDetailActivity extends AppCompatActivity {

    private DatabaseManager repository;
    private EditText startField;
    private EditText endField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        repository = DatabaseManager.getInstance(this);
        startField = findViewById(R.id.edit_start_date);
        endField = findViewById(R.id.edit_end_date);

        // Assume ObjectiveAssessment for example purpose
        ObjectiveAssessment obj = new ObjectiveAssessment(0, 1, "Sample Objective", "");
        obj.setDueDate(endField.getText().toString());
        obj.setTitle("Updated Title");

        repository.getAssessmentHelper().insertAssessment(obj);

        // For checklist example (static values)
        ChecklistItemEntity item = new ChecklistItemEntity(0, 1, "Example item", false);
        repository.getChecklistHelper().insertChecklistItem(item);
    }
}
