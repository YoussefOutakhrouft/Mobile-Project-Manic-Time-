package com.example.navigationdrawer;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.navigationdrawer.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Déclarations pour la gestion des tâches
    private CalendarView calendarView;
    private EditText taskInput, taskDescription;
    private Button addTaskButton;
    private ListView taskListView;
    private String selectedDate = "";
    private ArrayList<String> todayTaskList = new ArrayList<>();
    private ArrayList<String> otherTaskList = new ArrayList<>();
    private ArrayAdapter<String> todayAdapter, otherAdapter;
    private Map<String, ArrayList<String>> taskDescriptions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le layout principal avec la barre d'outils
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuration de la navigation
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashbaord, R.id.tmsystem, R.id.stime)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Initialiser les vues de gestion des tâches
        calendarView = findViewById(R.id.calendarView);
        taskInput = findViewById(R.id.taskInput);
        taskDescription = findViewById(R.id.taskDescription);
        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.todayTaskListView);

        // Initialiser les adaptateurs pour les ListViews
        todayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todayTaskList);
        taskListView.setAdapter(todayAdapter);

        // Gestion de la sélection d'une date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year; // Format de la date
            updateTaskListView(); // Mettre à jour la liste affichée en fonction de la date
        });

        // Gestion de l'ajout d'une tâche
        addTaskButton.setOnClickListener(view -> {
            String task = taskInput.getText().toString();
            String description = taskDescription.getText().toString();
            if (!task.isEmpty() && !selectedDate.isEmpty()) {
                // Ajouter la tâche dans la liste appropriée
                if (selectedDate.equals(getTodayDate())) {
                    todayTaskList.add(task);
                    taskDescriptions.put(task, new ArrayList<>());
                    todayAdapter.notifyDataSetChanged();
                } else {
                    otherTaskList.add(task);
                    taskDescriptions.put(task, new ArrayList<>());
                }

                // Ajouter la description si elle existe
                if (!description.isEmpty()) {
                    taskDescriptions.get(task).add(description);
                }

                taskInput.setText(""); // Réinitialiser le champ de saisie
                taskDescription.setText(""); // Réinitialiser la description
                updateTaskListView(); // Mettre à jour la liste affichée
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task and select a date", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur une tâche pour afficher la description
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = (String) parent.getItemAtPosition(position);
            ArrayList<String> descriptions = taskDescriptions.get(selectedTask);
            StringBuilder descriptionText = new StringBuilder("Task Description:\n");

            for (String desc : descriptions) {
                descriptionText.append(desc).append("\n");
            }

            Toast.makeText(MainActivity.this, descriptionText.toString(), Toast.LENGTH_LONG).show();
        });
    }

    // Méthode pour obtenir la date du jour au format dd/MM/yyyy
    private String getTodayDate() {
        return new java.text.SimpleDateFormat("d/M/yyyy").format(new java.util.Date());
    }

    // Mise à jour des ListViews en fonction de la date sélectionnée
    private void updateTaskListView() {
        if (selectedDate.equals(getTodayDate())) {
            taskListView.setAdapter(todayAdapter);
        } else {
            ArrayAdapter<String> otherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, otherTaskList);
            taskListView.setAdapter(otherAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
