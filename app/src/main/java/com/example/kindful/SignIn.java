package com.example.kindful;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import android.os.Bundle;

public class SignIn extends AppCompatActivity {
    private Button signinBtn,newuser;
    private TextInputEditText username,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);
        signinBtn= findViewById(R.id.signinBtn);
        newuser= findViewById(R.id.signup_screen);
        mAuth=FirebaseAuth.getInstance();
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,ChooserPage.class);
                startActivity(intent);
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=username.getText().toString();
                String pwd=password.getText().toString();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(SignIn.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignIn.this, "Login Successful..", Toast.LENGTH_SHORT).show();

                            reDirect(email);
                        } else {

                            Toast.makeText(SignIn.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
    public void  reDirect(String email){

        DocumentReference db;
        DocumentReference docRef = db.collection("User").document(email);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Intent i = new Intent(SignIn.this,DonatorHome.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SignIn.this, RecieverHome.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(SignIn.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}