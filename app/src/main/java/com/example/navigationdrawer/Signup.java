package com.example.navigationdrawer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private EditText Name,Email,Password,CPassword;
    private TextView nom,em,pass,cpass;
    private Button btn;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Name=findViewById(R.id.editTextName);
        Email=findViewById(R.id.editTextEmail);
        Password=findViewById(R.id.editTextPassword);
        CPassword=findViewById(R.id.editTextCPassword);
        nom=findViewById(R.id.textViewName);
        em=findViewById(R.id.textViewEmail);
        pass=findViewById(R.id.textViewPassword);
        cpass=findViewById(R.id.textViewCPassword);
        mAuth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.signup);
        btn.setEnabled(false);
        btn.setTextColor(Color.parseColor("#B3B5B4"));



        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Password.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Name.getText().toString().isEmpty() && !CPassword.getText().toString().isEmpty()){
                    if(Password.getText().toString().equals(CPassword.getText().toString())){
                        btn.setEnabled(true);
                    }else{
                        btn.setEnabled(false);
                    }
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    nom.setTextColor(Color.parseColor("#FFFFFF"));
                    cpass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));
                }else{
                    if(!Email.getText().toString().isEmpty()){
                        em.setTextColor(Color.parseColor("#FFFFFF"));
                    }else {
                        em.setTextColor(Color.parseColor("#B3B5B4"));
                    }
                    pass.setTextColor(Color.parseColor("#B3B5B4"));
                    nom.setTextColor(Color.parseColor("#B3B5B4"));
                    cpass.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Password.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Name.getText().toString().isEmpty() && !CPassword.getText().toString().isEmpty()){
                    if(Password.getText().toString().equals(CPassword.getText().toString())){
                        btn.setEnabled(true);
                    }else{
                        btn.setEnabled(false);
                    }
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    nom.setTextColor(Color.parseColor("#FFFFFF"));
                    cpass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));
                }else{
                    if(!Name.getText().toString().isEmpty()){
                        nom.setTextColor(Color.parseColor("#FFFFFF"));
                    }else {
                        nom.setTextColor(Color.parseColor("#B3B5B4"));
                    }
                    em.setTextColor(Color.parseColor("#B3B5B4"));
                    pass.setTextColor(Color.parseColor("#B3B5B4"));
                    cpass.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Password.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Name.getText().toString().isEmpty() && !CPassword.getText().toString().isEmpty()){
                    if(Password.getText().toString().equals(CPassword.getText().toString())){
                        btn.setEnabled(true);
                    }else{
                        btn.setEnabled(false);
                    }
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    nom.setTextColor(Color.parseColor("#FFFFFF"));
                    cpass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));
                }else{
                    if(!Password.getText().toString().isEmpty()){
                        pass.setTextColor(Color.parseColor("#FFFFFF"));
                    }else{
                        pass.setTextColor(Color.parseColor("#B3B5B4"));
                    }
                    em.setTextColor(Color.parseColor("#B3B5B4"));
                    nom.setTextColor(Color.parseColor("#B3B5B4"));
                    cpass.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        CPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Password.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Name.getText().toString().isEmpty() && !CPassword.getText().toString().isEmpty()){
                    if(Password.getText().toString().equals(CPassword.getText().toString())){
                        btn.setEnabled(true);
                    }else{
                        btn.setEnabled(false);
                    }
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    nom.setTextColor(Color.parseColor("#FFFFFF"));
                    cpass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));

                }else{
                    if(!CPassword.getText().toString().isEmpty()){
                        cpass.setTextColor(Color.parseColor("#FFFFFF"));
                    }else{
                        cpass.setTextColor(Color.parseColor("#B3B5B4"));
                    }
                    em.setTextColor(Color.parseColor("#B3B5B4"));
                    nom.setTextColor(Color.parseColor("#B3B5B4"));
                    pass.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setTextColor(Color.parseColor("#B3B5B4"));
                    btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    public void Login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view) {

        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Récupérer l'utilisateur actuel
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null) {
                        // Ajouter l'utilisateur à la base de données
                        AddToDB(user.getUid(), name, email);
                    }

                    Toast.makeText(Signup.this, "Compte créé avec succès.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Signup.this, "Échec de l'authentification.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void AddToDB(String userId, String name, String email) {
        // Préparation des données
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);

        // Référence vers la base de données
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users"); // Chemin "users" dans Firebase

        // Ajout des données avec l'UID comme clé
        myRef.child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup.this, "Données enregistrées avec succès.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, "Échec de l'enregistrement des données.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}