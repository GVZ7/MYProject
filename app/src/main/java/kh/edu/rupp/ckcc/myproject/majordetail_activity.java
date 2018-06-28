package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class majordetail_activity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majordetail);

        // Get selected event
        Intent intent = getIntent();
        String eventJson = intent.getStringExtra("Majors1");
        Gson gson = new Gson();
        majors majors = gson.fromJson(eventJson, majors.class);



        SimpleDraweeView imgEvent = findViewById(R.id.img_major_detail);
        imgEvent.setImageURI(majors.getImg_major());

        TextView txtTitle = findViewById(R.id.major_title);
        txtTitle.setText(majors.getName());

        TextView txtDate = findViewById(R.id.major_description);
        txtDate.setText(majors.getDescription());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
