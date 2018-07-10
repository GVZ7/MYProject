package kh.edu.rupp.ckcc.myproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registor extends AppCompatActivity implements OnCompleteListener<AuthResult>
{
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText usename;
    private Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_profile);

        usename=findViewById(R.id.username_registration);

        register=findViewById(R.id.registor);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (task.isSuccessful())
        {
            //signup get user id
//           String userid=firebaseAuth.getCurrentUser().getUid();
//            DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("Profile").child(userid);
//
//            String usernamee=usename.getText().toString();
//
//            Map newpost = new HashMap();
//            newpost.put("username",usernamee);
//
//            current_user_db.setValue(newpost);

            if (user!=null)
            {
                String name = user.getDisplayName();
                String email = user.getEmail();
            }


        }else
        {
            Toast.makeText(this, "signup with real time database error.", Toast.LENGTH_LONG).show();
        }
    }
}
