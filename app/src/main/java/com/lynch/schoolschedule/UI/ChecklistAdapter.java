package com.lynch.schoolschedule.UI;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lynch.schoolschedule.Entities.ChecklistItemEntity;
import com.lynch.schoolschedule.R;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {
    private List<ChecklistItemEntity> checklistItems;

    public ChecklistAdapter(List<ChecklistItemEntity> checklistItems) {
        this.checklistItems = checklistItems;
    }

    @NonNull
    @Override
    public ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
        return new ChecklistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, int position) {
        ChecklistItemEntity item = checklistItems.get(position);
        holder.checkBox.setChecked(item.isDone());
        holder.editText.setText(item.getContent());

        updateTextAppearance(holder.editText, item.isDone());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setDone(isChecked);
            updateTextAppearance(holder.editText, isChecked);
        });

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });
    }

    private void updateTextAppearance(EditText editText, boolean completed) {
        if (completed) {
            editText.setPaintFlags(editText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            editText.setAlpha(0.5f);
        } else {
            editText.setPaintFlags(editText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            editText.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return checklistItems != null ? checklistItems.size() : 0;
    }

    public void addItem(ChecklistItemEntity newItem) {
        checklistItems.add(newItem);
        notifyItemInserted(checklistItems.size() - 1);
    }

    public List<ChecklistItemEntity> getChecklistItems() {
        return checklistItems;
    }

    public static class ChecklistViewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        CheckBox checkBox;

        public ChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.checklist_item_text);
            checkBox = itemView.findViewById(R.id.checklist_item_checkbox);
        }
    }
}
