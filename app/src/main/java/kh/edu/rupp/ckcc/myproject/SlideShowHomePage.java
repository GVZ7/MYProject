package kh.edu.rupp.ckcc.myproject;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

public class SlideShowHomePage extends PagerAdapter {
    private  Slide [] imageid;

    public SlideShowHomePage() {
        this.imageid = new Slide[0];
    }

    public void setImageid(Slide[] imageid) {
        this.imageid = imageid;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageid.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View slide = inflater.inflate(R.layout.activity_home_slide,container,false);
        SimpleDraweeView itemSlide = slide.findViewById(R.id.item_slide);
        Slide slide1= imageid[position];
        itemSlide.setImageURI(slide1.getImg_slide());
        container.addView(slide);
        return slide;
    }
}
