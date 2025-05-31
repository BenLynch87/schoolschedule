package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle; import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClassDetailActivity extends Activity {


    private Repository repository;
    private EditText titleField, noteField, instructorNameField, instructorPhoneField, instructorEmailField;
    private Button saveButton, cancelButton, deleteButton, startDateButton, endDateButton;
    private Spinner termSpinner, statusSpinner;
    private RecyclerView assessmentList;
    private int termId = 0, classId = -1;
    private List<TermEntity> terms;
    private List<Assessment> assessments;
    private DateTimeFormatter storageFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        titleField = findViewById(R.id.textClassNameField);
        noteField = findViewById(R.id.classNote);
        instructorNameField = findViewById(R.id.instructorName);
        instructorPhoneField = findViewById(R.id.instructorPhone);
        instructorEmailField = findViewById(R.id.instructorEMail);

        startDateButton = findViewById(R.id.DateStartPickerbutton);
        endDateButton = findViewById(R.id.DateEndPickerbutton);

        saveButton = findViewById(R.id.SaveAssessmentButton);
        cancelButton = findViewById(R.id.CancelAssessmentButton);
        deleteButton = findViewById(R.id.DeleteAssessmentButton);

        termSpinner = findViewById(R.id.TermList);
        statusSpinner = findViewById(R.id.Status);
        assessmentList = findViewById(R.id.classAssessmentList);

        repository = Repository.getInstance(this);
        terms = repository.getAllTerms();

        if (terms.isEmpty()) {
            TermEntity defaultTerm = new TermEntity(0, "Default Term", "", "");
            repository.insertTerm(defaultTerm);
            terms.add(defaultTerm);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (TermEntity term : terms) {
            adapter.add(term.getTermName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapter);

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                termId = terms.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        classId = getIntent().getIntExtra("classId", -1);
        if (classId != -1) {
            ClassEntity existingClass = repository.getClass(classId);
            if (existingClass != null) {
                titleField.setText(existingClass.getClassName());
                startDateButton.setText(LocalDateTime.parse(existingClass.getStartDate()).format(displayFormatter));
                startDateButton.setTag(existingClass.getStartDate());
                endDateButton.setText(LocalDateTime.parse(existingClass.getEndDate()).format(displayFormatter));
                endDateButton.setTag(existingClass.getEndDate());
                noteField.setText(existingClass.getNotes());
                instructorNameField.setText(existingClass.getInstructor());
                instructorPhoneField.setText(existingClass.getPhone());
                instructorEmailField.setText(existingClass.getEmail());
                // Select term and status in spinners
                for (int i = 0; i < terms.size(); i++) {
                    if (terms.get(i).getId() == existingClass.getTermId()) {
                        termSpinner.setSelection(i);
                        break;
                    }
                }

                ArrayAdapter<String> statusAdapter = (ArrayAdapter<String>) statusSpinner.getAdapter();
                if (statusAdapter != null) {
                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        if (statusAdapter.getItem(i).equals(existingClass.getStatus())) {
                            statusSpinner.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }

        startDateButton.setOnClickListener(v -> showDateTimePicker(startDateButton));
        endDateButton.setOnClickListener(v -> showDateTimePicker(endDateButton));

        saveButton.setOnClickListener(v -> saveClass());
        cancelButton.setOnClickListener(v -> finish());
        deleteButton.setOnClickListener(v -> deleteClass());

        if (classId != -1) {
            assessments = repository.getAssessmentsByClassId(classId);
            assessmentList.setLayoutManager(new LinearLayoutManager(this));
            AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, assessments);
            assessmentList.setAdapter(assessmentAdapter);
        }
    }

    private void showDateTimePicker(Button button) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            TimePickerDialog timeDialog = new TimePickerDialog(this, (timeView, hour, minute) -> {
                calendar.set(year, month, dayOfMonth, hour, minute);
                LocalDateTime selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hour, minute);
                button.setText(selectedDateTime.format(displayFormatter));
                button.setTag(selectedDateTime.format(storageFormatter));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timeDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    private void saveClass() {
        String title = titleField.getText().toString();
        String startDateStr = (startDateButton.getTag() instanceof String) ? (String) startDateButton.getTag() : "";
        String endDateStr = (endDateButton.getTag() instanceof String) ? (String) endDateButton.getTag() : "";
        String status = statusSpinner.getSelectedItem().toString();
        String notes = noteField.getText().toString();
        String instructor = instructorNameField.getText().toString();
        String phone = instructorPhoneField.getText().toString();
        String email = instructorEmailField.getText().toString();

        if (title.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(startDateStr, storageFormatter);
            endDate = LocalDateTime.parse(endDateStr, storageFormatter);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_LONG).show();
            return;
        }

        ClassEntity newClass = new ClassEntity(classId == -1 ? 0 : classId, title, instructor, startDate.toString(), endDate.toString(), status, termId, notes, phone, email);

        if (classId == -1) repository.insertClass(newClass);
        else repository.updateClass(newClass);

        finish();
    }

    private void deleteClass() {
        if (classId != -1) {
            ClassEntity toDelete = repository.getClass(classId);
            repository.deleteClass(toDelete);
        }
        finish();
    }


}
