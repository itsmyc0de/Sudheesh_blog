package com.example.kuttan.sudheesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mdatabase= FirebaseDatabase.getInstance().getReference().child("blog");

        recyclerView= (RecyclerView) findViewById(R.id.blog_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton floatingActionButton= (FloatingActionButton) findViewById(R.id.floating_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,post_activity.class));
                Snackbar.make(v,"Sucess",Snackbar.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_now,
                BlogViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder blogViewHolder, Blog blog, int i) {

                blogViewHolder.settitle(blog.getTitle());
                blogViewHolder.setdescription(blog.getdescription());
                blogViewHolder.setimage(getApplicationContext(), blog.getImage());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{



        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mview=itemView;
        }

        public void settitle(String titlte){
            TextView post_title= (TextView) mview.findViewById(R.id.post_text_view);
            post_title.setText(titlte);
        }

       public void  setdescription(String description){
           TextView description_view= (TextView) mview.findViewById(R.id.post_desc_view);
           description_view.setText(description);
       }


        public void setimage(Context context,String image) {
            ImageView imageView= (ImageView) mview.findViewById(R.id.post_image_view);
            Picasso.with(context)
                    .load(image)
                    .resize(100,100)
                     .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
