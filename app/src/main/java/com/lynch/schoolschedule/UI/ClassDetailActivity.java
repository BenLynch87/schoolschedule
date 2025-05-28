package com.lynch.schoolschedule.UI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.lynch.schoolschedule.Database.DatabaseManager;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.HashMap;
import java.util.List;

public class ClassDetailActivity extends AppCompatActivity {

    private DatabaseManager repository;
    private EditText nameField, instructorField, startField, endField, notesField;
    private Spinner termSpinner, statusSpinner;
    private Button saveButton, deleteButton;
    private int classId = -1;
    private HashMap<String, Integer> termMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        repository = DatabaseManager.getInstance(this);
        nameField = findViewById(R.id.class_name);
        instructorField = findViewById(R.id.class_instructor);
        startField = findViewById(R.id.class_start);
        endField = findViewById(R.id.class_end);
        notesField = findViewById(R.id.class_notes);
        termSpinner = findViewById(R.id.term_spinner);
        statusSpinner = findViewById(R.id.class_status);
        saveButton = findViewById(R.id.class_save);
        deleteButton = findViewById(R.id.class_delete);

        saveButton.setOnClickListener(v -> saveClass());
        deleteButton.setOnClickListener(v -> deleteClass());

        List<TermEntity> terms = repository.getTermHelper().getAllTerms();
        ArrayAdapter<String> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (TermEntity t : terms) {
            termAdapter.add(t.getName());
            termMap.put(t.getName(), t.getId());
        }
        termSpinner.setAdapter(termAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.class_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        ClassEntity cls = new ClassEntity(0, 1, "Math", "Dr. Smith", "2025-01-01", "2025-05-01", "In Progress", "Syllabus Notes");
        classId = (int) repository.getClassHelper().insertClass(cls);

        ClassEntity fetched = repository.getClassHelper().getClass(classId);
        if (fetched != null) {
            nameField.setText(fetched.getName());
            instructorField.setText(fetched.getInstructor());
            startField.setText(fetched.getStartDate());
            endField.setText(fetched.getEndDate());
            notesField.setText(fetched.getNotes());
            for (String key : termMap.keySet()) {
                if (termMap.get(key) == fetched.getTermId()) {
                    termSpinner.setSelection(termAdapter.getPosition(key));
                    break;
                }
            }
            int statusPos = statusAdapter.getPosition(fetched.getStatus());
            statusSpinner.setSelection(statusPos);
        }
    }

    private void saveClass() {
        String name = nameField.getText().toString();
        String instructor = instructorField.getText().toString();
        String start = startField.getText().toString();
        String end = endField.getText().toString();
        String notes = notesField.getText().toString();
        String status = (String) statusSpinner.getSelectedItem();
        String selectedTerm = (String) termSpinner.getSelectedItem();

        if (name.isEmpty() || instructor.isEmpty() || start.isEmpty() || end.isEmpty() || notes.isEmpty() || selectedTerm == null || status == null) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return;
        }

        int termId = termMap.getOrDefault(selectedTerm, 1);
        ClassEntity cls = new ClassEntity(classId, termId, name, instructor, start, end, status, notes);
        repository.getClassHelper().updateClass(cls);
    }

    private void deleteClass() {
        if (classId != -1) {
            repository.getClassHelper().deleteClass(classId);
            finish();
        }
    }
}
