package com.example.kuttan.sudheesh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by KUTTAN on 29-12-2016.
 */
public class login extends AppCompatActivity {

    private EditText email, pass;
    private Button creaete_btn;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        creaete_btn= (Button) findViewById(R.id.create_text_button);
        linearLayout= (LinearLayout) findViewById(R.id.login_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        //Check the users is login or not REFER SIGNIN PROGRAM

        progressDialog = new ProgressDialog(this);

        creaete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,googl_signin.class));
                Snackbar.make(v,"Create An Account First",Snackbar.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mail) && TextUtils.isEmpty(password)) {
                    Snackbar.make(linearLayout, "Fields Cant be Empty", Snackbar.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(login.this, MainActivity.class));
                            } else {
                                Snackbar.make(linearLayout, "Login failed..", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }

        });
    }
}
