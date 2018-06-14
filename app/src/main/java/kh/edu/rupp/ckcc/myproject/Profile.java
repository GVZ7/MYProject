package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Profile  {
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

    
}
