package com.example.navigationdrawer.ui.home.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.databinding.FragmentGalleryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private EditText taskInput;
    private EditText taskDescription;
    private ListView todayTaskListView, otherTaskListView;
    private TextView selectedDateText;
    private ArrayAdapter<String> todayAdapter, otherAdapter;
    private ArrayList<String> todayTasks = new ArrayList<>();
    private ArrayList<String> otherTasks = new ArrayList<>();

    private CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        taskInput = root.findViewById(R.id.taskInput);
        taskDescription = root.findViewById(R.id.taskDescription);
        selectedDateText = root.findViewById(R.id.selectedDateText);
        todayTaskListView = root.findViewById(R.id.todayTaskListView);
        otherTaskListView = root.findViewById(R.id.otherTaskListView);
        calendarView = root.findViewById(R.id.calendarView);
        Button addTaskButton = root.findViewById(R.id.addTaskButton);

        todayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todayTasks);
        otherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, otherTasks);

        todayTaskListView.setAdapter(todayAdapter);
        otherTaskListView.setAdapter(otherAdapter);

        // Handle date selection from the CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%d/%d/%d", dayOfMonth, month + 1, year);
            selectedDateText.setText("Selected Date: " + selectedDate);
        });

        // Handle adding task
        addTaskButton.setOnClickListener(v -> {
            String task = taskInput.getText().toString();
            String description = taskDescription.getText().toString();
            String selectedDate = selectedDateText.getText().toString().replace("Selected Date: ", "");

            if (task.isEmpty() || description.isEmpty() || selectedDate.isEmpty()) {
                return;
            }

            // Get today's date
            String todayDate = new SimpleDateFormat("d/M/yyyy").format(new Date());

            // Add task to the appropriate list
            if (selectedDate.equals(todayDate)) {
                todayTasks.add(task + ": " + description);
                todayAdapter.notifyDataSetChanged();
            } else {
                otherTasks.add(selectedDate + " - " + task + ": " + description);
                otherAdapter.notifyDataSetChanged();
            }

            // Clear the input fields
            taskInput.setText("");
            taskDescription.setText("");
        });

        return root;
    }
}
