package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements OnCompleteListener<AuthResult>
{
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText email;
    private EditText password;
    private EditText usename;

   private Button button1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        usename=findViewById(R.id.signup_user);

        button1= findViewById(R.id.btn_summit);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    //buttom sign up function
    private void signup(){
       firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener( this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            //FirebaseUser user = firebaseAuth.getCurrentUser();


            Intent intent = new Intent(signup.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(this, "Login with Firebase error.", Toast.LENGTH_LONG).show();
        }
    }
}
