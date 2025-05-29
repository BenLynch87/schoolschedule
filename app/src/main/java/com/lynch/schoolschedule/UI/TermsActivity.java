package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.List;

public class TermsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Repository repository;
    private TermAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        recyclerView = findViewById(R.id.termRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = Repository.getInstance(getApplication());
        adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);

        List<TermEntity> terms = repository.getAllTerms();
        adapter.setTerms(terms);


    }

    public void addTerm(View view) {
        Intent intent = new Intent(this, TermDetailActivity.class);
        startActivity(intent);
    }
}
