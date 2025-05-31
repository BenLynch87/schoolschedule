package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ChecklistItemEntity;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.Helpers.ChecklistHelper;
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
    private ChecklistHelper checklistHelper;
    private EditText titleField, dueDateField;
    private Spinner typeSpinner, classSpinner;
    private Button saveButton, deleteButton, addChecklistButton;
    private View checklistSection;
    private RecyclerView checklistRecyclerView;
    private ChecklistAdapter checklistAdapter;
    private Assessment currentAssessment;
    private boolean isNew = false;
    private int classId = 0;
    private List<ClassEntity> classes;
    private List<ChecklistItemEntity> checklistItems;
    private DateTimeFormatter storageFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        titleField = findViewById(R.id.edit_title);
        dueDateField = findViewById(R.id.edit_due_date);
        typeSpinner = findViewById(R.id.spinner_type);
        classSpinner = findViewById(R.id.class_spinner);
        checklistSection = findViewById(R.id.checklist_section);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);
        addChecklistButton = findViewById(R.id.button_add_checklist_item);
        checklistRecyclerView = findViewById(R.id.recycler_view_checklist);

        repository = Repository.getInstance(this);
        checklistHelper = new ChecklistHelper(this);

        classes = repository.getAllClasses();

        if (classes.isEmpty()) {
            TermEntity defaultTerm = new TermEntity(0, "Default Term", "", "");
            repository.insertTerm(defaultTerm);
            ClassEntity defaultClass = new ClassEntity(0, "Default Class", "", "", "", "In Progress", 0, "", "", "");
            repository.insertClass(defaultClass);
            classes.add(defaultClass);
        }

        // Setup Type Spinner
        ArrayAdapter<String> adapterT = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Objective", "Performance"});
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterT);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = (String) parent.getItemAtPosition(position);
                if ("Performance".equalsIgnoreCase(selectedType)) {
                    checklistSection.setVisibility(View.VISIBLE);
                } else {
                    checklistSection.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Setup Class Spinner
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

        // Initialize checklist
        checklistItems = new ArrayList<>();
        checklistAdapter = new ChecklistAdapter(checklistItems);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checklistRecyclerView.setAdapter(checklistAdapter);

        int assessmentId = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1) {
            currentAssessment = repository.getAssessment(assessmentId);
            if (currentAssessment != null) {
                titleField.setText(currentAssessment.getTitle());
                try {
                    LocalDateTime dt = LocalDateTime.parse(currentAssessment.getDueDate(), storageFormatter);
                    dueDateField.setText(dt.format(displayFormatter));
                    dueDateField.setTag(dt.format(storageFormatter));
                } catch (Exception e) {
                    dueDateField.setText(currentAssessment.getDueDate());
                }

                if ("Performance".equalsIgnoreCase(currentAssessment.getType())) {
                    typeSpinner.setSelection(1);
                    checklistSection.setVisibility(View.VISIBLE);

                    // Load checklist items from database
                    checklistItems.addAll(checklistHelper.getChecklistItemsForAssessment(assessmentId));
                    checklistAdapter.notifyDataSetChanged();
                } else {
                    typeSpinner.setSelection(0);
                    checklistSection.setVisibility(View.GONE);
                }

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

        addChecklistButton.setOnClickListener(v -> addChecklistItem());
        saveButton.setOnClickListener(this::saveAssessment);
        deleteButton.setOnClickListener(v -> deleteAssessment());
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

    private void addChecklistItem() {
        ChecklistItemEntity newItem = new ChecklistItemEntity(0, currentAssessment != null ? currentAssessment.getId() : 0, "", false);
        checklistAdapter.addItem(newItem);
    }

    public void saveAssessment(View view) {
        String title = titleField.getText().toString();
        String dueDateStr = (dueDateField.getTag() instanceof String) ? (String) dueDateField.getTag() : "";
        String type = typeSpinner.getSelectedItem().toString();

        if (title.isEmpty() || dueDateStr.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime dueDate;
        try {
            dueDate = LocalDateTime.parse(dueDateStr);
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

        // Save checklist items
        if ("Performance".equalsIgnoreCase(type)) {
            if (!isNew) {
                // Remove old checklist items
                checklistHelper.deleteChecklistItemsForAssessment(currentAssessment.getId());
            }
            for (ChecklistItemEntity item : checklistAdapter.getChecklistItems()) {
                item.setAssessmentId(currentAssessment.getId());
                checklistHelper.insertChecklistItem(item);
            }
        }

        finish();
    }

    private void deleteAssessment() {
        if (currentAssessment != null) {
            repository.deleteAssessment(currentAssessment);
            checklistHelper.deleteChecklistItemsForAssessment(currentAssessment.getId());
            Toast.makeText(this, "Assessment deleted", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
