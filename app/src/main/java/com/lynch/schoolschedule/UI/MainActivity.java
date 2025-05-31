package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView searchResults;
    private SearchAdapter searchAdapter;
    private TextView noResultsText;

    private Button viewTermsButton, viewClassesButton, viewAssessmentsButton, upcomingAssessmentsButton, logoutButton;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = Repository.getInstance(getApplication());

        // Search bar
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        searchResults = findViewById(R.id.searchResults);
        searchResults.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this);
        searchResults.setAdapter(searchAdapter);

        noResultsText = findViewById(R.id.noResultsText);
        noResultsText.setVisibility(View.GONE);

        searchButton.setOnClickListener(v -> performSearch());

        // Navigation Buttons
        viewTermsButton = findViewById(R.id.viewTermsButton);
        viewClassesButton = findViewById(R.id.viewCoursesButton);
        viewAssessmentsButton = findViewById(R.id.viewAssessmentsButton);
        upcomingAssessmentsButton = findViewById(R.id.upcomingAssessmentsButton);
        logoutButton = findViewById(R.id.logoutButton);

        viewTermsButton.setOnClickListener(v -> {
            clearSearchResults();
            startActivity(new Intent(MainActivity.this, TermsActivity.class));
        });

        viewClassesButton.setOnClickListener(v -> {
            clearSearchResults();
            startActivity(new Intent(MainActivity.this, ClassActivity.class));
        });

        viewAssessmentsButton.setOnClickListener(v -> {
            clearSearchResults();
            startActivity(new Intent(MainActivity.this, AssessmentActivity.class));
        });

        upcomingAssessmentsButton.setOnClickListener(v -> {
            clearSearchResults();
            startActivity(new Intent(MainActivity.this, AssessmentReportActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            clearSearchResults();
            startActivity(new Intent(MainActivity.this, LogoutActivity.class));
            finish();
        });
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            searchAdapter.clearResults();
            searchResults.setVisibility(View.GONE);
            noResultsText.setVisibility(View.GONE);
            return;
        }

        List<Object> results = new ArrayList<>();
        results.addAll(repository.searchTerms(query));
        results.addAll(repository.searchClasses(query));
        results.addAll(repository.searchAssessments(query));

        if (results.isEmpty()) {
            searchAdapter.clearResults();
            searchResults.setVisibility(View.GONE);
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            searchAdapter.setResults(results);
            searchResults.setVisibility(View.VISIBLE);
            noResultsText.setVisibility(View.GONE);
        }
    }

    private void clearSearchResults() {
        searchInput.setText("");
        searchAdapter.clearResults();
        searchResults.setVisibility(View.GONE);
        noResultsText.setVisibility(View.GONE);
    }
}
