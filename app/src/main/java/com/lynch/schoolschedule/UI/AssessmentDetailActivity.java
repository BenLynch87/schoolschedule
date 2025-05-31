package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetailActivity extends Activity {

    private Repository repository;
    private EditText titleField, dueDateField, typeField;
    private Button saveButton, deleteButton;
    private Spinner classSpinner;
    private Assessment currentAssessment;
    private boolean isNew = false;
    private int classId = 0;
    private List<ClassEntity> classes;
    private DateTimeFormatter storageFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        titleField = findViewById(R.id.edit_title);
        dueDateField = findViewById(R.id.edit_due_date);
        typeField = findViewById(R.id.edit_type);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);
        classSpinner = findViewById(R.id.class_spinner);

        repository = Repository.getInstance(this);
        classes = repository.getAllClasses();

        if (classes.isEmpty()) {
            TermEntity defaultTerm = new TermEntity(0, "Default Term", "", "");
            repository.insertTerm(defaultTerm);
            ClassEntity defaultClass = new ClassEntity(0, "Default Class",
                    "", "", "", "In Progress", 0, "", "", "");
            repository.insertClass(defaultClass);
            classes.add(defaultClass);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (ClassEntity cls : classes) {
            adapter.add(cls.getClassName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classId = classes.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dueDateField.setFocusable(false);
        dueDateField.setOnClickListener(v -> showDateTimePicker());

        int assessmentId = getIntent().getIntExtra("assessmentId", -1);

        if (assessmentId != -1) {
            currentAssessment = repository.getAssessment(assessmentId);
            if (currentAssessment != null) {
                titleField.setText(currentAssessment.getTitle());
                try {
                    LocalDateTime dt = LocalDateTime.parse(currentAssessment.getDueDate(), storageFormatter);
                    dueDateField.setText(dt.format(displayFormatter));
                } catch (Exception e) {
                    dueDateField.setText(currentAssessment.getDueDate());
                }
                typeField.setText(currentAssessment.getType());
                classId = currentAssessment.getClassId();
                for (int i = 0; i < classes.size(); i++) {
                    if (classes.get(i).getId() == classId) {
                        classSpinner.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            isNew = true;
        }

        saveButton.setOnClickListener(v -> saveAssessment(v));
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timeDialog = new TimePickerDialog(this, (timeView, hour, minute) -> {
                calendar.set(year, month, dayOfMonth, hour, minute);
                LocalDateTime selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hour, minute);
                dueDateField.setText(selectedDateTime.format(displayFormatter));
                dueDateField.setTag(selectedDateTime.format(storageFormatter));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timeDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    public void saveAssessment(View view) {
        String title = titleField.getText().toString();
        String dueDateStr = (dueDateField.getTag() instanceof String) ? (String) dueDateField.getTag() : "";
        String type = typeField.getText().toString();

        if (title.isEmpty() || dueDateStr.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime dueDate;
        try {
            dueDate = LocalDateTime.parse(dueDateStr, storageFormatter);
        } catch (Exception e) {
            Toast.makeText(this, "Date parsing failed", Toast.LENGTH_LONG).show();
            return;
        }

        if (isNew) {
            currentAssessment = new Assessment(0, classId, title, dueDate.toString(), type);
            repository.insertAssessment(currentAssessment);
        } else {
            currentAssessment.setTitle(title);
            currentAssessment.setDueDate(dueDate.toString());
            currentAssessment.setType(type);
            repository.updateAssessment(currentAssessment);
        }

        finish();
    }}