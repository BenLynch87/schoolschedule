package com.lynch.schoolschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private final Context context;
    private List<ClassEntity> classes = new ArrayList<>();
    private OnClassSelectListener selectListener;

    public interface OnClassSelectListener {
        void onSelect(ClassEntity selected);
    }

    public void setOnClassSelectListener(OnClassSelectListener listener) {
        this.selectListener = listener;
    }

    public ClassAdapter(Context context, List<ClassEntity> classEntities) {
        this.context = context;
        this.classes = classEntities;
    }

    public void setClasses(List<ClassEntity> classList) {
        this.classes = classList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassEntity course = classes.get(position);
        holder.name.setText(course.getClassName());
        holder.start.setText(course.getStartDate());
        holder.end.setText(course.getEndDate());
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.itemBackground));

        holder.itemView.setOnClickListener(v -> {
            if (selectListener != null) {
                selectListener.onSelect(course);
            } else {
                Intent intent = new Intent(context, ClassDetailActivity.class);
                intent.putExtra("classId", course.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classes != null ? classes.size() : 0;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView name, start, end;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.itemBackground));
            name = itemView.findViewById(R.id.textClassName);
            start = itemView.findViewById(R.id.textClassStart);
            end = itemView.findViewById(R.id.textClassEnd);
        }
    }
}
