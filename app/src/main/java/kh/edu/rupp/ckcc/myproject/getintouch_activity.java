package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class getintouch_activity extends AppCompatActivity
{
    ImageButton b ;
    ImageButton f ;
    ImageButton i ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_in_touch);

        //back button
        b=findViewById(R.id.back_getintouch);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getintouch_activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //facebook button
        f=findViewById(R.id.facebook);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/vinsovann1111"));
                startActivity(intent);
            }
        });

        //insta button
        i=findViewById(R.id.insta);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rule.edu.kh/docsxv/"));
                startActivity(intent);
            }
        });
    }


}
