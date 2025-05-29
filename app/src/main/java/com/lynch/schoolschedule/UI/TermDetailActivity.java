package com.lynch.schoolschedule.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.lynch.schoolschedule.Database.DatabaseManager;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

public class TermDetailActivity extends AppCompatActivity {

    private DatabaseManager repository;
    private EditText titleField;
    private Button saveButton, startButton, endButton, deleteButton;
    private int termId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        repository = DatabaseManager.getInstance(this);
        titleField = findViewById(R.id.TermNameField);
        startButton = findViewById(R.id.DateStartPickerbuttonc);
        endButton = findViewById(R.id.DateEndPickerbuttonc);
        saveButton = findViewById(R.id.SaveAssessmentButton);
        deleteButton = findViewById(R.id.DeleteAssessmentButton);

        saveButton.setOnClickListener(v -> saveTerm());
        deleteButton.setOnClickListener(v -> deleteTerm());

        TermEntity term = new TermEntity(0, "Fall 2025", "2025-09-01", "2025-12-15");
        termId = (int) repository.getTermHelper().insertTerm(term);

        TermEntity fetched = repository.getTermHelper().getTerm(termId);
        if (fetched != null) {
            titleField.setText(fetched.getTermName());
            startButton.setText(fetched.getStartDate());
            endButton.setText(fetched.getEndDate());
        }
    }

    private void saveTerm() {
        String title = titleField.getText().toString();
        String start = startButton.getText().toString();
        String end = endButton.getText().toString();
        TermEntity term = new TermEntity(termId, title, start, end);
        repository.getTermHelper().updateTerm(term);
    }

    private void deleteTerm() {
        if (termId != -1) {
            repository.getTermHelper().deleteTerm(termId);
            finish();
        }
    }
}
