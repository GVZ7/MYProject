package kh.edu.rupp.ckcc.myproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class settingActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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


}
