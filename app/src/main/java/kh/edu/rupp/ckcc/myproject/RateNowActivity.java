package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RateNowActivity extends AppCompatActivity implements View.OnClickListener {
    private Button feedback,rate_now;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_now);
        feedback = findViewById(R.id.feedback);
        rate_now = findViewById(R.id.btnRateNow);

        feedback.setOnClickListener(this);
        rate_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == feedback){
            //Start MainActivity
            Intent intent = new Intent(this,feeback_activity.class);
            startActivity(intent);
            //Finish current activity
        }else if(v == rate_now){
            Intent intent = new Intent(this,RateNow.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Please Other Click Button", Toast.LENGTH_SHORT).show();
        }
    }
}
