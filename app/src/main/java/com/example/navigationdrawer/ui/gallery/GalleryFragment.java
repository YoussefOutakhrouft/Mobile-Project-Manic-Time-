package com.example.navigationdrawer.ui.gallery;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.Login;
import com.example.navigationdrawer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private EditText taskInput;
    private EditText taskDescription;
    private ListView taskListView;
    private CalendarView calendarView;
    private String selectedDate = "";
    private ArrayList<String> todayTaskList = new ArrayList<>();
    private ArrayList<String> otherTaskList = new ArrayList<>();
    private ArrayAdapter<String> todayAdapter;
    private Map<String, ArrayList<String>> taskDescriptions = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflater la vue pour ce fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Initialisation des vues de gestion des tâches
        taskInput = root.findViewById(R.id.taskInput);
        taskDescription = root.findViewById(R.id.taskDescription);
        calendarView = root.findViewById(R.id.calendarView);
        taskListView = root.findViewById(R.id.todayTaskListView);
        Button addTaskButton = root.findViewById(R.id.addTaskButton);

        // Initialiser l'adaptateur pour la ListView
        todayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todayTaskList);
        taskListView.setAdapter(todayAdapter);

        // Gestion de la sélection d'une date dans le calendrier
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            updateTaskListView();
        });

        // Gestion de l'ajout d'une tâche
        addTaskButton.setOnClickListener(view -> {
            String task = taskInput.getText().toString();
            String description = taskDescription.getText().toString();
            if (!task.isEmpty() && !selectedDate.isEmpty()) {
                if (selectedDate.equals(getTodayDate())) {
                    todayTaskList.add(task);
                    taskDescriptions.put(task, new ArrayList<>());
                    todayAdapter.notifyDataSetChanged();
                } else {
                    // Ajouter la tâche avec la date dans la liste otherTaskList
                    String taskWithDate = task + " - " + selectedDate;  // Ajout de la date
                    otherTaskList.add(taskWithDate);
                    taskDescriptions.put(taskWithDate, new ArrayList<>());
                }

                // Ajouter la description de la tâche
                if (!description.isEmpty()) {
                    taskDescriptions.get(selectedDate.equals(getTodayDate()) ? task : task + " - " + selectedDate).add(description);
                }

                taskInput.setText("");
                taskDescription.setText("");
                updateTaskListView();
            } else {
                Toast.makeText(getActivity(), "Please enter a task and select a date", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur une tâche pour afficher sa description
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = (String) parent.getItemAtPosition(position);
            ArrayList<String> descriptions = taskDescriptions.get(selectedTask);
            StringBuilder descriptionText = new StringBuilder("Task Description:\n");

            if (descriptions != null) {
                for (String desc : descriptions) {
                    descriptionText.append(desc).append("\n");
                }
            }

            // Créer un AlertDialog pour afficher la description de la tâche
            new android.app.AlertDialog.Builder(getActivity())
                    .setMessage(descriptionText.toString())
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        return root;
    }


    private String getTodayDate() {
        return new java.text.SimpleDateFormat("d/M/yyyy").format(new java.util.Date());
    }

    private void updateTaskListView() {
        if (selectedDate.equals(getTodayDate())) {
            taskListView.setAdapter(todayAdapter);
        } else {
            ArrayAdapter<String> otherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, otherTaskList);
            taskListView.setAdapter(otherAdapter);
        }
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void closeApp() {
        getActivity().finish();
        System.exit(0);
    }
}
