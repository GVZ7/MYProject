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
import com.google.firebase.firestore.QuerySnapshot;

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
    // loading username Email in drawer
   private void loaddata()
   {
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       //read data
       db.collection("Profile").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           //catch all data put into task
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               //check task error
               if (task.isSuccessful())
               {
                   QuerySnapshot document=task.getResult();
                   document.getDocuments();
                   document.toString();

               }else
               {
                   Toast.makeText(getApplication(),"Error Loading Prfile",Toast.LENGTH_SHORT).show();
                   Log.d("MY App","Error Loading"+task.getException().getMessage());
               }

           }
       });

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
