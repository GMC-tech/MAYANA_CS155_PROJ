package com.example.mayana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mUsername, mYourEmail, mName, mPassword;
    ImageButton mRegisterBtn;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    ProgressDialog pd;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsername = findViewById(R.id.username);
        mYourEmail = findViewById(R.id.yourEmail);
        mName = findViewById(R.id.name);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = (ImageButton) findViewById(R.id.registerBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignUp.this);
                pd.setMessage("Please wait...");
                pd.show();

                String username = mUsername.getText().toString();
                String name = mName.getText().toString();
                String email = mYourEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mYourEmail.setError("Choose an Email Address");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    mYourEmail.setError("Enter name.");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    mYourEmail.setError("Enter username.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Enter Password");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Use 6 characters or more for your password");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            SignUpModel user = new SignUpModel(name, username, email);

                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            users.updateProfile(profileUpdates);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            startActivity(new Intent(getApplicationContext(), SignIn.class));
                        } else {
                            Toast.makeText(SignUp.this, "Your account cannot be created at this time." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}