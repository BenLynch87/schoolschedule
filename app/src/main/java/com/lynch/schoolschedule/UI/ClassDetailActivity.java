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
    private EditText nameField, instructorField, notesField;
    private Spinner termSpinner, statusSpinner;
    private Button startButton, endButton, saveButton, deleteButton;
    private int classId = -1;
    private HashMap<String, Integer> termMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        repository = DatabaseManager.getInstance(this);
        nameField = findViewById(R.id.textClassNameField);
        instructorField = findViewById(R.id.instructorName);
        startButton = findViewById(R.id.DateStartPickerbutton);
        endButton = findViewById(R.id.DateEndPickerbutton);
        notesField = findViewById(R.id.classNote);
        termSpinner = findViewById(R.id.TermList);
        statusSpinner = findViewById(R.id.Status);
        saveButton = findViewById(R.id.SaveAssessmentButton);
        deleteButton = findViewById(R.id.DeleteAssessmentButton);

        saveButton.setOnClickListener(v -> saveClass());
        deleteButton.setOnClickListener(v -> deleteClass());

        List<TermEntity> terms = repository.getTermHelper().getAllTerms();
        ArrayAdapter<String> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (TermEntity t : terms) {
            termAdapter.add(t.getTermName());
            termMap.put(t.getTermName(), t.getId());
        }
        termSpinner.setAdapter(termAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.Class_Status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        ClassEntity cls = new ClassEntity(1, "Math", "Dr. Smith", "2025-01-01","2025-05-01" ,"In Progress" ,1 , "Syllabus Notes");
        classId = (int) repository.getClassHelper().insertClass(cls);

        ClassEntity fetched = repository.getClassHelper().getClass(classId);
        if (fetched != null) {
            nameField.setText(fetched.getClassName());
            instructorField.setText(fetched.getInstructor());
            startButton.setText(fetched.getStartDate());
            endButton.setText(fetched.getEndDate());
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
        String start = startButton.getText().toString();
        String end = endButton.getText().toString();
        String notes = notesField.getText().toString();
        String status = (String) statusSpinner.getSelectedItem();
        String selectedTerm = (String) termSpinner.getSelectedItem();

        if (name.isEmpty() || instructor.isEmpty() || start.isEmpty() || end.isEmpty() || notes.isEmpty() || selectedTerm == null || status == null) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return;
        }

        int termId = termMap.getOrDefault(selectedTerm, 1);
        ClassEntity cls = new ClassEntity(classId, name, instructor, start, end, status, termId, notes);
        repository.getClassHelper().updateClass(cls);
    }

    private void deleteClass() {
        if (classId != -1) {
            repository.getClassHelper().deleteClass(classId);
            finish();
        }
    }
}
