package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.R;

import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private Repository repository;
    private ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        repository = Repository.getInstance(getApplication());
        List<ClassEntity> classEntities = repository.getAllClasses();
        RecyclerView recyclerView = findViewById(R.id.allClassList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassAdapter(this, classEntities);
        recyclerView.setAdapter(adapter);
    }

    public void addClass(View view) {
        Intent intent = new Intent(this, ClassDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<ClassEntity> classes = repository.getAllClasses();
        adapter.setClasses(classes);
    }
}
