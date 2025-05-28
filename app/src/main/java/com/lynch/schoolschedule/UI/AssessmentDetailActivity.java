package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.R;

public class AssessmentDetailActivity extends Activity {

    private Repository repository;
    private EditText titleField, dueDateField, typeField;
    private Button saveButton, deleteButton;
    private Assessment currentAssessment;
    private boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        titleField = findViewById(R.id.edit_title);
        dueDateField = findViewById(R.id.edit_due_date);
        typeField = findViewById(R.id.edit_type);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        repository = Repository.getInstance(this);

        int assessmentId = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1) {
            currentAssessment = repository.getAssessment(assessmentId);
            if (currentAssessment != null) {
                titleField.setText(currentAssessment.getTitle());
                dueDateField.setText(currentAssessment.getDueDate());
                typeField.setText(currentAssessment.getType());
            }
        } else {
            currentAssessment = new Assessment();
            isNew = true;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAssessment.setTitle(titleField.getText().toString());
                currentAssessment.setDueDate(dueDateField.getText().toString());
                currentAssessment.setType(typeField.getText().toString());
                if (isNew) {
                    repository.insertAssessment(currentAssessment);
                } else {
                    repository.updateAssessment(currentAssessment);
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNew) {
                    repository.deleteAssessment(currentAssessment.getId());
                }
                finish();
            }
        });
    }
}
