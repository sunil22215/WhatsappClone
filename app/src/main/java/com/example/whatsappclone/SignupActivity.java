package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappclone.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText editTextUserName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewAccountHave;
    private Button buttonSignup;
    private Button buttonFacebook;
    private Button buttonGoogle;
    String url ="https://whatsappclone-9fec1-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(url);
        initLayout();
        setUpViewWithListener();
    }
    @Override
    protected void onStart() {
        super.onStart();

        long endAt = 100L; // Fixed value: CRASH on third app restart
        //  long endAt = new Date().getTime(); // Dynamic value: NO CRASH

    }

    private void initLayout() {
        editTextUserName=findViewById(R.id.editTextUserName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        textViewAccountHave=findViewById(R.id.textViewUserAccount);
        buttonSignup=findViewById(R.id.buttonSignup);
        buttonFacebook=findViewById(R.id.buttonFacebook);
        buttonGoogle=findViewById(R.id.buttonGoogle);


    }

    private void setUpViewWithListener() {
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Users user = new Users(editTextUserName.getText().toString(),
                                    editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child("id").setValue(user);
                            Toast.makeText(SignupActivity.this, "user created successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
