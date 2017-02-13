package com.example.kuttan.sudheesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;


public class post_activity extends ActionBarActivity {

    public ImageButton imageButton_post;
    public static final int GALLERY_REQUEST=1;

    private EditText titile,desc;
    Button post;
    private ProgressDialog progressBar;
    private Uri image_uri=null;
    private StorageReference mstorage;
    private DatabaseReference mdatabase;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);





        imageButton_post= (ImageButton) findViewById(R.id.post_image);

    //Database and storage references

    mstorage= FirebaseStorage.getInstance().getReference();
    mdatabase= FirebaseDatabase.getInstance().getReference().child("blog");


        progressBar=new ProgressDialog(this);
        imageButton_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For calling the intent as open the gallery to choose image
                Intent gallery_intent=new Intent(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent,GALLERY_REQUEST);

            }
        });


        titile= (EditText) findViewById(R.id.post_text);
        desc= (EditText) findViewById(R.id.post_desc);
        post= (Button) findViewById(R.id.post_button);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpost();
            }
        });

    }

    private void startpost() {


        //For Uploading to the Storage
        progressBar.setMessage("Posting...");
        progressBar.show();

         final  String title_value=titile.getText().toString().trim();
        final String desc_value=desc.getText().toString().trim();
        if(!TextUtils.isEmpty(title_value) && !TextUtils.isEmpty(desc_value) && image_uri !=null ){

            StorageReference filepath=mstorage.child("Blog_Images").child(image_uri.getLastPathSegment());
            filepath.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri=taskSnapshot.getDownloadUrl();



                    //FOr Uploading to the Dstabase

                    DatabaseReference new_post=mdatabase.push();
                    new_post.child("title").setValue(title_value);
                    new_post.child("description").setValue(desc_value);
                    new_post.child("image").setValue(downloaduri.toString());

                    //Dismiss the Progess Bar
                    progressBar.dismiss();

                    //pushing to the Main activity
                    startActivity(new Intent(post_activity.this,MainActivity.class));
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
           image_uri=data.getData();
            imageButton_post.setImageURI(image_uri);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
