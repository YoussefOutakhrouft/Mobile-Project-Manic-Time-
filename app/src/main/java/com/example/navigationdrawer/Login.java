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

public class Login extends AppCompatActivity {

    private EditText Email,Password;
    private TextView em,pass;
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
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        em=findViewById(R.id.textView);
        pass=findViewById(R.id.textView2);
        btn=findViewById(R.id.button);
        mAuth=FirebaseAuth.getInstance();
        btn.setEnabled(false);
        btn.setTextColor(Color.parseColor("#B3B5B4"));


        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Password.getText().toString().isEmpty() || !Email.getText().toString().isEmpty()){
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));
                    btn.setEnabled(true);
                }else{
                    em.setTextColor(Color.parseColor("#B3B5B4"));
                    pass.setTextColor(Color.parseColor("#B3B5B4"));
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
                if (!Password.getText().toString().isEmpty() || !Email.getText().toString().isEmpty()){
                    em.setTextColor(Color.parseColor("#FFFFFF"));
                    pass.setTextColor(Color.parseColor("#FFFFFF"));
                    btn.setTextColor(Color.parseColor("#000000"));
                    btn.setEnabled(true);
                }else{
                    em.setTextColor(Color.parseColor("#B3B5B4"));
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

    public void afficher(View view) {

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(Login.this, "Échec de l'authentification.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void Signup(View view) {

        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        finish();
    }
}