package com.example.appliction_manic_time;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    private EditText Name,Email,Password,CPassword;
    private TextView nom,em,pass,cpass;
    private Button btn;

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
    }

    public void signup(View view) {

    }
}