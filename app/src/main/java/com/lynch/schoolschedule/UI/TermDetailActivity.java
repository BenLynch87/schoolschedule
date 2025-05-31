package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.UI.ClassAdapter;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetailActivity extends Activity  {

    private Repository repository;
    private EditText titleField;
    private Button startDateButton, endDateButton, saveButton, deleteButton, cancelButton;
    private RecyclerView classList;
    private int termId = -1;
    private List<ClassEntity> classEntities;
    private DateTimeFormatter storageFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter displayFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        titleField = findViewById(R.id.TermNameField);
        startDateButton = findViewById(R.id.DateStartPickerbuttonc);
        endDateButton = findViewById(R.id.DateEndPickerbuttonc);
        saveButton = findViewById(R.id.SaveAssessmentButton);
        deleteButton = findViewById(R.id.DeleteAssessmentButton);
        cancelButton = findViewById(R.id.CancelButton);
        classList = findViewById(R.id.termClassList);

        repository = Repository.getInstance(this);

        termId = getIntent().getIntExtra("termId", -1);
        if (termId != -1) {
            TermEntity term = repository.getTermById(termId);
            if (term != null) {
                titleField.setText(term.getTermName());
                startDateButton.setText(LocalDateTime.parse(term.getStartDate()).format(displayFormatter));
                startDateButton.setTag(term.getStartDate());
                endDateButton.setText(LocalDateTime.parse(term.getEndDate()).format(displayFormatter));
                endDateButton.setTag(term.getEndDate());
            }

            classEntities = repository.getClassesByTermId(termId);
            classList.setLayoutManager(new LinearLayoutManager(this));
            ClassAdapter classAdapter = new ClassAdapter(this, classEntities);
            classAdapter.setOnClassSelectListener(selected -> {
                Intent intent = new Intent(this, ClassDetailActivity.class);
                intent.putExtra("classId", selected.getId());
                startActivity(intent);
            });
            classList.setAdapter(classAdapter);
        }

        startDateButton.setOnClickListener(v -> showDateTimePicker(startDateButton));
        endDateButton.setOnClickListener(v -> showDateTimePicker(endDateButton));

        saveButton.setOnClickListener(v -> saveTerm());
        deleteButton.setOnClickListener(v -> deleteTerm());
        cancelButton.setOnClickListener(v -> finish());
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

    private void saveTerm() {
        String title = titleField.getText().toString();
        String startDateStr = (startDateButton.getTag() instanceof String) ? (String) startDateButton.getTag() : "";
        String endDateStr = (endDateButton.getTag() instanceof String) ? (String) endDateButton.getTag() : "";

        if (title.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(startDateStr.replace(" ", "T"));
            endDate = LocalDateTime.parse(endDateStr.replace(" ", "T"));
        } catch (Exception e) {
            Toast.makeText(this, "Date parsing failed", Toast.LENGTH_LONG).show();
            return;
        }

        TermEntity term = new TermEntity(termId == -1 ? 0 : termId, title, startDate.toString(), endDate.toString());

        if (termId == -1) repository.insertTerm(term);
        else repository.updateTerm(term);

        finish();
    }

    private void deleteTerm() {
        if (termId != -1) {
            TermEntity term = repository.getTermById(termId);
            repository.deleteTerm(term.getId());
        }
        finish();
    }


}

