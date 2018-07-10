package kh.edu.rupp.ckcc.myproject;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class Myapplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
