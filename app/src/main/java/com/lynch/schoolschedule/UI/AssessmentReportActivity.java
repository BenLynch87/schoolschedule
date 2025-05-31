package com.lynch.schoolschedule.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentReportActivity extends AppCompatActivity {

    private Spinner reportFilterSpinner;
    private RecyclerView reportRecyclerView;
    private AssessmentReportAdapter reportAdapter;
    private Repository repository;

    private static final String FILTER_NEXT_WEEK = "Next Week";
    private static final String FILTER_NEXT_MONTH = "Next Month";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_report);

        reportFilterSpinner = findViewById(R.id.reportFilterSpinner);
        reportRecyclerView = findViewById(R.id.reportRecyclerView);

        repository = Repository.getInstance(getApplication());
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupFilterSpinner();
    }

    private void setupFilterSpinner() {
        List<String> filterOptions = new ArrayList<>();
        filterOptions.add(FILTER_NEXT_WEEK);
        filterOptions.add(FILTER_NEXT_MONTH);

        // Load terms dynamically
        List<TermEntity> terms = repository.getAllTerms();
        for (TermEntity term : terms) {
            filterOptions.add(term.getTermName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, filterOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportFilterSpinner.setAdapter(adapter);

        reportFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                loadReportData(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadReportData(String filter) {
        List<Assessment> filteredAssessments = new ArrayList<>();

        if (FILTER_NEXT_WEEK.equals(filter)) {
            filteredAssessments = repository.getAssessmentsDueInNextWeek();
        } else if (FILTER_NEXT_MONTH.equals(filter)) {
            filteredAssessments = repository.getAssessmentsDueInNextMonth();
        } else {
            // Filter by selected term
            TermEntity term = repository.getTermByName(filter);
            if (term != null) {
                filteredAssessments = repository.getAssessmentsByTermId(term.getId());
            }
        }

        // Build maps
        List<ClassEntity> allClasses = repository.getAllClasses();
        List<TermEntity> allTerms = repository.getAllTerms();

        Map<Integer, String> classNamesMap = buildClassNamesMap(allClasses);
        Map<Integer, Integer> termIdsMap = buildTermIdsMap(allClasses);
        Map<Integer, String> termNamesMap = buildTermNamesMap(allTerms);

        reportAdapter = new AssessmentReportAdapter(
                this,
                filteredAssessments,
                classNamesMap,
                termIdsMap,
                termNamesMap
        );

        reportRecyclerView.setAdapter(reportAdapter);
    }

    private Map<Integer, String> buildClassNamesMap(List<ClassEntity> classList) {
        Map<Integer, String> map = new HashMap<>();
        for (ClassEntity c : classList) {
            map.put(c.getId(), c.getClassName());
        }
        return map;
    }

    private Map<Integer, Integer> buildTermIdsMap(List<ClassEntity> classList) {
        Map<Integer, Integer> map = new HashMap<>();
        for (ClassEntity c : classList) {
            map.put(c.getId(), c.getTermId());
        }
        return map;
    }

    private Map<Integer, String> buildTermNamesMap(List<TermEntity> termList) {
        Map<Integer, String> map = new HashMap<>();
        for (TermEntity t : termList) {
            map.put(t.getId(), t.getTermName());
        }
        return map;
    }
}
