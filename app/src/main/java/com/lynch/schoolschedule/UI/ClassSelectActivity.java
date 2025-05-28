package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectActivity extends AppCompatActivity {
    private Repository repository;
    private int termId;
    private RecyclerView recyclerView;
    private ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_select);

        repository = new Repository(getApplication());
        recyclerView = findViewById(R.id.selectableClassList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termId = getIntent().getIntExtra("termId", -1);

        adapter = new ClassAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.setOnClassSelectListener(selected -> {
            selected.setTermId(termId);
            repository.update(selected);
            setResult(RESULT_OK);
            finish();
        });

        repository.getAllClasses().observe(this, new Observer<List<ClassEntity>>() {
            @Override
            public void onChanged(List<ClassEntity> allClasses) {
                List<ClassEntity> eligible = new ArrayList<>();
                for (ClassEntity c : allClasses) {
                    if (c.getTermId() != termId) {
                        eligible.add(c);
                    }
                }
                if (eligible.isEmpty()) {
                    Toast.makeText(ClassSelectActivity.this, "No eligible classes available", Toast.LENGTH_SHORT).show();
                }
                adapter.setClasses(eligible);
            }
        });
    }
}
