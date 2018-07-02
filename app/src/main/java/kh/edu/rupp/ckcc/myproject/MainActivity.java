package kh.edu.rupp.ckcc.myproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String userId="Tommy";

    private int[] tabIcons = {
            R.drawable.home1,
            R.drawable.list1,
            R.drawable.search1,
            R.drawable.ic_setting
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
      //  setContentView(R.layout.activity_homepage);
            setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.lyt_main);
         navigationView = findViewById(R.id.navigation_view);

         tabLayout = findViewById(R.id.abbtab);
         viewPager = findViewById(R.id.viewpager);
         AddFragments();
         loadProfileInfoFromFacebook();
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
               if(item.getItemId()==R.id.mnu_logout){
                   onLogoutClick();
               }
               if(item.getItemId()==R.id.mnu_profile){
                   onProfileClick();
               }
               return false;
           }

       });
        // Load profile image from Firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("images").child("Profile").child(userId + ".jpg");
        profileRef.getBytes(10240000).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);//get pic from firebase
                    //create img in view
                    View headerview =navigationView.getHeaderView(0);
                    ImageView img=headerview.findViewById(R.id.img_profile_navigation);
                    img.setImageBitmap(bitmap);//put pic in img
                } else {
                    Toast.makeText(MainActivity.this, "Load profile image fail.", Toast.LENGTH_LONG).show();
                    Log.d("ckcc", "Load profile image fail: " + task.getException());
                }
            }
        });
        loaddata();
        User user = SingleTon.getInstance().getUser();
        if (user != null) {
            View headerView = navigationView.getHeaderView(0);
            SimpleDraweeView imgProfile = headerView.findViewById(R.id.img_profile_navigation);
            imgProfile.setImageURI(user.getProfilePicture());
            TextView txtEmail =headerView.findViewById(R.id.email);
            TextView txtUsername = headerView.findViewById(R.id.username);
            txtUsername.setText(user.getName());
        }
    }
    private void AddFragments(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        adapter.AddFragments(new HomepageFragment(),"");
        adapter.AddFragments(new FaculitiesFragment(),"");
        adapter.AddFragments(new SearchFragment(),"");
    //    adapter.AddFragments(new SettingFragment(),"Setting");

        //adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setIconTab();
    }
    //setup icon for tab
    private void setIconTab(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
   //    tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    // loading username Email in drawer
   private void loaddata() {

       FirebaseFirestore db = FirebaseFirestore.getInstance();
       //read data
       db.collection("Profile").document("ndqLK4kQlgD2osE16iOF").addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot==null){
                        Toast.makeText(getApplication(),"Error",Toast.LENGTH_LONG).show();
                        Log.d("Ckcc","LoadDataError: "+e);
                    }
                    else{
                        Profile profile = documentSnapshot.toObject(Profile.class);
                        View headerView= navigationView.getHeaderView(0);
                        TextView txtName = headerView.findViewById(R.id.username);
                        txtName.setText(profile.getUsername());
                        TextView txtEmail= headerView.findViewById(R.id.email);
                        txtEmail.setText(profile.getEmail());

                        //imgURL
                        SimpleDraweeView imgUrl =headerView.findViewById(R.id.img_profile_navigation);
                        imgUrl.setImageURI(profile.getImgUrl());
                    }
           }
       });
   }




    private void onProfileClick() {
        drawerLayout.closeDrawers();

        Intent intent=new Intent(this,Profile_activity.class);
        startActivity(intent);


    }

    private void onGetInTouchClick() {
        drawerLayout.closeDrawers();
        Intent intent =new Intent(this,getintouch_activity.class);
        startActivity(intent);

        // Finish current activity
        finish();
    }

    private void onFeedbackClick() {
    drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, feeback_activity.class);
        startActivity(intent);

        // Finish current activity
        finish();
    }

    private void onBookmarkClick() {
        drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, bookmark_activity.class);
        startActivity(intent);

        // Finish current activity
        finish();

    }

    private void onAboutClick() {
    drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, about_activity.class);
        startActivity(intent);

        // Finish current activity
        finish();
    }

    private void onSettingClick() {
        drawerLayout.closeDrawers();

        // Start MainActivity
        Intent intent = new Intent(this, settingActivity.class);
        startActivity(intent);

        // Finish current activity
        finish();
    }
    //click pic
    public void click_to_profile(View view) {
        Intent intent=new Intent(this,Profile_activity.class);
        startActivity(intent);
    }
    public void onLogoutClick() {
        // Remove current user
        SingleTon.getInstance().setUser(null);
        SharedPreferences preferences = getSharedPreferences("MyProject", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.apply();
       AccessToken accessToken= AccessToken.getCurrentAccessToken();
       accessToken=null;

        // Move to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }





    private void loadProfileInfoFromFacebook(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String profileUrl = "http://graph.facebook.com/" + id + "/picture?type=large";
                            String name = object.getString("name");
                            String email = object.getString("email");


                             SimpleDraweeView imgProfile = findViewById(R.id.img_profile_navigation);
                            imgProfile.setImageURI(profileUrl);
                            TextView txtName = findViewById(R.id.username);
                            txtName.setText(name);
                            TextView txt_email = findViewById(R.id.email);
                            txt_email.setText(email);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
