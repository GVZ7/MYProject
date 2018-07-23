package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class settingActivity extends AppCompatActivity
{
    ImageButton b ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        b=findViewById(R.id.back_setting);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

       // onBackPressed();
    }

    public void onCheck(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.light_theme_choosen:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.dark_theme_choosen:
                if (checked)
                    // Ninjas rule
                    break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(settingActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
