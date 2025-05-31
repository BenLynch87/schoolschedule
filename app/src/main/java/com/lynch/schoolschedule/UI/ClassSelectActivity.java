package com.lynch.schoolschedule.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.R;

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

        repository = Repository.getInstance(getApplication());
        recyclerView = findViewById(R.id.selectableClassList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termId = getIntent().getIntExtra("termId", -1);
        List<ClassEntity> classEntities = repository.getAllClasses();
        adapter = new ClassAdapter(this, classEntities);
        recyclerView.setAdapter(adapter);

        adapter.setOnClassSelectListener(selected -> {
            selected.setTermId(termId);
            repository.updateClass(selected);
            setResult(RESULT_OK);
            finish();
        });

        List<ClassEntity> classes = repository.getAllClasses();
        adapter.setClasses(classes);
    }
}
