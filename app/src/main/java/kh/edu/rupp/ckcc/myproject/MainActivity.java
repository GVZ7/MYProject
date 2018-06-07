package kh.edu.rupp.ckcc.myproject;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {
 DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        drawerLayout = findViewById(R.id.lyt_main);
        NavigationView navigationView = findViewById(R.id.navigation_view);
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId()==R.id.mnu_setting){
                   onSettingClick();
               }
               if(item.getItemId()==R.id.mnu_about){
                   onAboutClick();
               }
               if(item.getItemId()==R.id.mnu_BookMark){
                   onBookmarkClick();
               }
               if(item.getItemId() == R.id.mnu_FeedBack){
                   onFeedbackClick();
               }
               if(item.getItemId()==R.id.mnu_GetInTouch){
                   onGetInTouchClick();
               }
               if(item.getItemId()==R.id.mnu_profile){
                   onProfileClick();
               }
               return false;
           }

       });

    }
    // laoding username Email in drawer
    private void loadProfile(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Profile").document("ndqLK4kQlgD2osE16iOF").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    process(task);
                }
                    else{
                    Toast.makeText(getApplication(), "Load events error.", Toast.LENGTH_LONG).show();
                    Log.d("IMajor", "Load UserName error: " + task.getException());
                };
            }
        });
    }
    private void process(@NonNull Task<DocumentSnapshot> task){

    }
    private void onProfileClick() {
        drawerLayout.closeDrawers();
    }

    private void onGetInTouchClick() {
        drawerLayout.closeDrawers();
    }

    private void onFeedbackClick() {
    drawerLayout.closeDrawers();
    }

    private void onBookmarkClick() {
        drawerLayout.closeDrawers();
    }

    private void onAboutClick() {
    drawerLayout.closeDrawers();
    }

    private void onSettingClick() {
        drawerLayout.closeDrawers();
    }
}
