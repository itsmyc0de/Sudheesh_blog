package com.example.kuttan.sudheesh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by KUTTAN on 25-12-2016.
 */
public class googl_signin extends FragmentActivity {

    SignInButton signInButton;
    EditText mail,password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
   private LinearLayout linearLayout;
    private Button google_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        linearLayout= (LinearLayout) findViewById(R.id.s_layout);

        mail= (EditText) findViewById(R.id.mail);
        password= (EditText) findViewById(R.id.password);
        signInButton= (SignInButton) findViewById(R.id.google_btn);
        google_create= (Button) findViewById(R.id.create_text_button);
        google_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(googl_signin.this,login.class));
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            //Start the activity if alreasy logged in

            //The step i am skipping...
        }

        progressDialog=new ProgressDialog(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email=mail.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if(TextUtils.isEmpty(email) &&TextUtils.isEmpty(pass) ){
                    Snackbar.make(linearLayout,"Fields Cant be Empty",Snackbar.LENGTH_SHORT).show();
                }
                progressDialog.setMessage("Registering Please Wait....");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       finish();
                       startActivity(new Intent(googl_signin.this,login.class));
                   }else {
                       Snackbar.make(linearLayout,"Registering failed..",Snackbar.LENGTH_SHORT).show();
                   }
                        progressDialog.dismiss();
                    }
                });

            }
        });

            }


}
