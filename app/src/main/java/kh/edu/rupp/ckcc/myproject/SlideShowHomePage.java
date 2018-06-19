package kh.edu.rupp.ckcc.myproject;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SlideShowHomePage extends PagerAdapter {
    private  int [] imageid;

    public void setImageid(int[] imageid) {
        this.imageid = imageid;
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
        ImageView itemSlide = slide.findViewById(R.id.item_slide);
        itemSlide.setImageResource(imageid[position]);
        container.addView(slide);
        return slide;
    }
}
