package com.lynch.schoolschedule.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.R;

import java.util.List;
import java.util.stream.Collectors;

public class AssessmentActivity extends Activity {

    private Repository repository;
    private ListView listView;
    private List<Assessment> assessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        repository = Repository.getInstance(this);
        listView = findViewById(R.id.assessment_list);

        assessments = repository.getAllAssessments();

        List<String> titles = assessments.stream()
                .map(Assessment::getTitle)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assessment selected = assessments.get(position);
                Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailActivity.class);
                intent.putExtra("assessmentId", selected.getId());
                startActivity(intent);
            }
        });
    }
}
