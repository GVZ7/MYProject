package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class feeback_activity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private Button sendfeedback;
    private EditText inputtext;
    private RadioButton radioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        radioGroup = findViewById(R.id.radiogroup);
        inputtext = findViewById(R.id.txtfeedback_input);
        sendfeedback = findViewById(R.id.btnsend_feedback);
        sendfeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);
        String value = (String) radioButton.getText().toString();

        String write = inputtext.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, value);
        intent.putExtra(Intent.EXTRA_TEXT, write);
        // get this in description
        intent.setType("Message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose app to send mail"));

    }

}
