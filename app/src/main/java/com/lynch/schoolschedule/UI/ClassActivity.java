package com.lynch.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Database.Repository;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.R;

import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private Repository repository;
    private ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        RecyclerView recyclerView = findViewById(R.id.allClassList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassAdapter(this);
        recyclerView.setAdapter(adapter);

        repository = Repository.getInstance(getApplication());

        List<ClassEntity> classes = repository.getAllClasses();
        adapter.setClasses(classes);

    }

    public void addClass(View view) {
        Intent intent = new Intent(this, ClassDetailActivity.class);
        startActivity(intent);
    }
}
