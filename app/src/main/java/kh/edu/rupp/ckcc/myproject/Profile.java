package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private String Username;
    private String Email;
    private String ImgUrl;

    public Profile() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public Profile(String username, String email, String imgUrl) {
        Username = username;
        Email = email;
        ImgUrl = imgUrl;

    }
    public void onProfileClick(){
        Intent intent = new Intent();
        intent.setType("Image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    
}
