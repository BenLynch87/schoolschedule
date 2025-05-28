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
    private EditText titleField, startField, endField;
    private Button saveButton, deleteButton;
    private int termId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        repository = DatabaseManager.getInstance(this);
        titleField = findViewById(R.id.term_title);
        startField = findViewById(R.id.term_start);
        endField = findViewById(R.id.term_end);
        saveButton = findViewById(R.id.term_save);
        deleteButton = findViewById(R.id.term_delete);

        saveButton.setOnClickListener(v -> saveTerm());
        deleteButton.setOnClickListener(v -> deleteTerm());

        TermEntity term = new TermEntity(0, "Fall 2025", "2025-09-01", "2025-12-15");
        termId = (int) repository.getTermHelper().insertTerm(term);

        TermEntity fetched = repository.getTermHelper().getTerm(termId);
        if (fetched != null) {
            titleField.setText(fetched.getName());
            startField.setText(fetched.getStartDate());
            endField.setText(fetched.getEndDate());
        }
    }

    private void saveTerm() {
        String title = titleField.getText().toString();
        String start = startField.getText().toString();
        String end = endField.getText().toString();
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
