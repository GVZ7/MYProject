package kh.edu.rupp.ckcc.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult>,OnCompleteListener<AuthResult> {
    private Button btnsignin,btnsignup;
    private EditText txtemail;
    private EditText txtpassword;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();//for sign in firebase
    private CallbackManager callbackManager;
    private ImageButton btnFBct;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login3);
//        showHashKey();
         txtemail=findViewById(R.id.username);
        txtpassword=findViewById(R.id.login_password);
        //button signin
        btnsignin = findViewById(R.id.btn_signin);
        btnsignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();

            }
        });
        //button signup
        btnsignup = findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,signup.class);
                startActivity(intent);
            }
        });


        checkIfUserAlreadyLoggedIn();
        // Facebook authentication
       LoginButton loginButton = findViewById(R.id.btn_facebook);
       btnFBct=(ImageButton)findViewById(R.id.btn_customFB);
       btnFBct.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
           }
       });

        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager,this);

    }
    private void checkIfUserAlreadyLoggedIn() {
//         Check login via username/password
        SharedPreferences preferences = getSharedPreferences("ckcc", MODE_PRIVATE);
        String userJsonString = preferences.getString("user", null);
        if (userJsonString != null) {
            Gson gson = new Gson();
            User user = gson.fromJson(userJsonString, User.class);
            SingleTon.getInstance().setUser(user);

            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            // Finish current activity
            finish();
        }

        // check login via Facebook
        if (AccessToken.getCurrentAccessToken() != null) {
            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            // Finish current activity
            finish();
        }
    }


    //button sign up function
    private void signin(){

        firebaseAuth.signInWithEmailAndPassword(txtemail.getText().toString(), txtpassword.getText().toString()).addOnCompleteListener(this);
//        String userID=user.getUid();
//        String userEmail=user.getEmail();
//        String userName=user.getDisplayName();
//        Toast.makeText(this,userID + userEmail+"  "+ userName,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        Toast.makeText(this, "Login with facebook succeeded.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onCancel() {
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, "Login with Facebook error.", Toast.LENGTH_LONG).show();
        Log.d("Hello world", "Facebook login error: " + error.getMessage());
    }
    private void showHashKey() {
        // Show hash key for configuration with Facebook developer console
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(messageDigest.digest(), 0));
                Log.d("HEllO WORLD", hashKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loginWithFacebook(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "name"));
    }
    //Sign in with Firebse
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            FirebaseUser user = firebaseAuth.getCurrentUser();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
//            TextView txtEmailNavigation=findViewById(R.id.username_navigation);
//            String email=user.getEmail();
//            txtEmailNavigation.setText(email);
        } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(this, "Login with Firebase error.", Toast.LENGTH_LONG).show();
            Log.d("COMPLETE",task.getException().getMessage());
        }
    }





}
