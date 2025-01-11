package com.example.navigationdrawer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // Déclarations pour la gestion des tâches
    private CalendarView calendarView;
    private EditText taskInput, taskDescription;
    private Button addTaskButton;
    private ListView taskListView;
    private String selectedDate = "";
    private ArrayList<String> todayTaskList = new ArrayList<>();
    private ArrayList<String> otherTaskList = new ArrayList<>();
    private ArrayAdapter<String> todayAdapter;
    private Map<String, ArrayList<String>> taskDescriptions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("tasks").child(user.getUid()); // Utiliser l'UID de l'utilisateur connecté
        }

        getTasksFromFirebase();

        // Initialiser le layout principal avec la barre d'outils
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuration de la navigation
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboard,R.id.tmsystem, R.id.stime, R.id.analytics, R.id.applimit, R.id.promodo)
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
            updateTaskListView();
        });

        // Gestion de l'ajout d'une tâche
        addTaskButton.setOnClickListener(view -> {
            String task = taskInput.getText().toString();
            String description = taskDescription.getText().toString();
            if (!task.isEmpty() && !selectedDate.isEmpty()) {

                addTaskToFirebase(task, description, selectedDate);

                if (selectedDate.equals(getTodayDate())) {
                    todayTaskList.add(task);
                    taskDescriptions.put(task, new ArrayList<>());
                    todayAdapter.notifyDataSetChanged();
                } else {
                    otherTaskList.add(task);
                    taskDescriptions.put(task, new ArrayList<>());
                }

                if (!description.isEmpty()) {
                    taskDescriptions.get(task).add(description);
                }

                taskInput.setText("");
                taskDescription.setText("");
                updateTaskListView();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task and select a date", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur une tâche pour afficher la description
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = (String) parent.getItemAtPosition(position);
            ArrayList<String> descriptions = taskDescriptions.get(selectedTask);
            StringBuilder descriptionText = new StringBuilder("Task Description:\n");

            if (descriptions != null) {
                for (String desc : descriptions) {
                    descriptionText.append(desc).append("\n");
                }
            } else {
                descriptionText.append("No description available.");
            }

            // Créer un AlertDialog pour afficher la description de la tâche
            new android.app.AlertDialog.Builder(MainActivity.this)
                    // Titre de la boîte de dialogue
                    .setMessage(descriptionText.toString())  // Contenu de la boîte de dialogue
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())  // Bouton OK pour fermer le dialogue
                    .show();  // Afficher le dialogue
        });


        // Gestion des clics sur les éléments du menu
  /*      navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.logout:
                    logout();
                    return true;
                case R.id.close:
                    closeApp();
                    return true;
                default:
                    return false;
            }
        }); */
    }

    private String getTodayDate() {
        return new java.text.SimpleDateFormat("d/M/yyyy").format(new java.util.Date());
    }

    private void updateTaskListView() {
        if (selectedDate.equals(getTodayDate())) {
            taskListView.setAdapter(todayAdapter);
        } else {
            ArrayAdapter<String> otherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, otherTaskList);
            taskListView.setAdapter(otherAdapter);
        }
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void closeApp() {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void closeApp(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        finish();

        System.exit(0);
    }

    private void getTasksFromFirebase() {
        if (mDatabase != null) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    todayTaskList.clear();
                    for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                        Task task = taskSnapshot.getValue(Task.class);  // Convertit chaque tâche en objet Task
                        if (task != null) {
                            if (task.getDate().equals(getTodayDate())) {
                                todayTaskList.add(task.getTaskName());
                                taskDescriptions.put(task.getTaskName(), new ArrayList<>());
                            }  // Ajoute le nom de la tâche à la liste
                        }
                    }
                    todayAdapter.notifyDataSetChanged();  // Met à jour la ListView
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Failed to load tasks.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addTaskToFirebase(String taskName, String taskDescription, String date) {
        if (mDatabase != null) {
            String taskId = mDatabase.push().getKey();  // Crée une clé unique pour la tâche
            if (taskId != null) {
                Task task = new Task(taskName, taskDescription, date);
                mDatabase.child(taskId).setValue(task);  // Ajoute la tâche à Firebase
            }
        }
    }
}
