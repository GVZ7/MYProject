package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Profile_activity extends AppCompatActivity
{
    private SimpleDraweeView imgProfile;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //click image


//        loaddata();
        User user1 = SingleTon.getInstance().getUser();
        if (user1 != null) {
                 imgProfile=findViewById(R.id.draw_profile);
                TextView txtName=findViewById(R.id.username_prfileActivity);
                TextView txtEmail=findViewById(R.id.email_profileActivity);

                imgProfile.setImageURI(user1.getProfilePicture());
                txtEmail.setText(user1.getEmail());
                txtName.setText(user1.getUsername());
        }

    }
    //choose picture
        public void profile_click(View view){
        if(AccessToken.getCurrentAccessToken()==null){
            // Open gallery app to select an image
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }else {
            Log.d("upload ","photo ");
        }

        }

    //upload n store img on firebase
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)//pel activity a jab yok data pi activity b
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Load selected image
        if(resultCode == RESULT_OK && requestCode == 1){
            try {
                // Set image to image view
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgProfile.setImageBitmap(bitmap);

                // Save image to Firebase Storage
                uploadImageToFirebaseStorage(bitmap);

            } catch (IOException e) {
                Toast.makeText(this, "Error while selecting profile image.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage(Bitmap bitmap){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("images").child("Profile").child(user.getUid() + ".jpg");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        profileRef.putBytes(bytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Profile_activity.this, "Upload profile image success.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Profile_activity.this, "Upload profile image fail.", Toast.LENGTH_LONG).show();
                    Log.d("ckcc", "Upload profile image fail: " + task.getException());
                }
            }
        });
    }

}
