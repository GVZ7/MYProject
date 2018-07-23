package kh.edu.rupp.ckcc.myproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Function;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.annotation.Nullable;

import io.grpc.Context;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public SharedPreferences sharedPreferences;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth Auth=FirebaseAuth.getInstance();
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
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.lyt_main);
        navigationView = findViewById(R.id.navigation_view);

        tabLayout = findViewById(R.id.abbtab);
        viewPager = findViewById(R.id.viewpager);
        AddFragments();
        loaddata();
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

        User user1 = SingleTon.getInstance().getUser();
        if (user1 != null) {
            View headerView = navigationView.getHeaderView(0);
            SimpleDraweeView imgProfile = headerView.findViewById(R.id.img_profile_navigation);
            imgProfile.setImageURI(user1.getProfilePicture());
            TextView txtEmail =headerView.findViewById(R.id.email);
            txtEmail.setText(user1.getEmail());
            TextView txtUsername = headerView.findViewById(R.id.username_navigation);
            txtUsername.setText(user1.getUsername());
            SimpleDraweeView imgProfileHOme=findViewById(R.id.img_home);
            imgProfileHOme.setImageURI(user1.getProfilePicture());
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

    public void onRestart() {
        super.onRestart();
        Intent intent=getIntent();
        finish();
        startActivity(intent);
    }


    //setup icon for tab
    private void setIconTab(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        //    tabLayout.getTabAt(3).setIcon(tabIcons[3]);
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

    }

    private void onFeedbackClick() {
        drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, feeback_activity.class);
        startActivity(intent);
        // Finish current activity
    }

    private void onBookmarkClick() {
        drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, bookmark_activity.class);
        startActivity(intent);
        // Finish current activity


    }

    private void onAboutClick() {
        drawerLayout.closeDrawers();
        // Start MainActivity
        Intent intent = new Intent(this, about_activity.class);
        startActivity(intent);
        // Finish current activity

    }

    private void onSettingClick() {
        drawerLayout.closeDrawers();

        // Start MainActivity
        Intent intent = new Intent(this, settingActivity.class);
        startActivity(intent);
        // Finish current activity

    }
    //click pic
    public void click_to_profile(View view) {

        drawerLayout.openDrawer(Gravity.LEFT);
    }
    public void onLogoutClick() {
        // Remove current user
        SingleTon.getInstance().setUser(null);
        SharedPreferences preferences = getSharedPreferences("MyProject", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.apply();
        LoginManager.getInstance().logOut();
        Auth.signOut();
        // Move to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    // loading username Email in drawer
    public void loaddata() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //read data
        if (Auth.getCurrentUser() != null) {
            db.collection("Profile").document(user.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot == null) {
                        Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();
                        Log.d("Ckcc", "LoadDataError: " + e);
                    } else {
                        final kh.edu.rupp.ckcc.myproject.Profile profile = documentSnapshot.toObject(kh.edu.rupp.ckcc.myproject.Profile.class);
                        User person = new User();
                        person.setEmail(profile.getEmail());
                        person.setId(documentSnapshot.getId());
                        person.setUsername(profile.getUsername());
                        person.setProfilePicture(profile.getImgUrl());
                        saveProfileInSharedPref(person);
                        SingleTon.getInstance().setUser(person);

                    View headerView = navigationView.getHeaderView(0);
                    SimpleDraweeView imgProfile = headerView.findViewById(R.id.img_profile_navigation);
                    imgProfile.setImageURI(person.getProfilePicture());
                    TextView txtEmail =headerView.findViewById(R.id.email);
                    txtEmail.setText(person.getEmail());
                    TextView txtUsername = headerView.findViewById(R.id.username_navigation);
                    txtUsername.setText(person.getUsername());
                    SimpleDraweeView imgProfileHOme=findViewById(R.id.img_home);
                    imgProfileHOme.setImageURI(person.getProfilePicture());
                    }
                }
            });
        }
    }
    private void saveProfileInSharedPref(User user) {
        sharedPreferences = getSharedPreferences("MyProject", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        editor.putString("user", userJsonString);
        editor.apply();
    }
}
