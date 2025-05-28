package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lynch.schoolschedule.R;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button searchButton;
    private Button viewTermsButton;
    private Button viewCoursesButton;
    private Button viewAssessmentsButton;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logoutButton);
        searchButton = findViewById(R.id.searchButton);
        viewTermsButton = findViewById(R.id.viewTermsButton);
        viewCoursesButton = findViewById(R.id.viewCoursesButton);
        viewAssessmentsButton = findViewById(R.id.viewAssessmentsButton);
        searchField = findViewById(R.id.searchInput);

        searchButton.setOnClickListener(v -> {
            String query = searchField.getText().toString().trim();
            // TODO: Implement search logic or pass query to another activity
        });

        viewTermsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TermsActivity.class);
            startActivity(intent);
        });

        viewCoursesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassActivity.class);
            startActivity(intent);
        });

        viewAssessmentsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
