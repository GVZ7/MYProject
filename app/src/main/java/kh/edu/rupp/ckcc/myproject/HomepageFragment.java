package kh.edu.rupp.ckcc.myproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

public class HomepageFragment extends android.support.v4.app.Fragment{

   private ViewPager viewPager;
   private int [] imgSlide= {R.drawable.ic_profile,R.drawable.ic_description};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //slide show
        viewPager = view.findViewById(R.id.view_pager_home);
        SlideShowHomePage slideShowHomePage= new SlideShowHomePage();
        slideShowHomePage.setImageid(imgSlide);
        viewPager.setAdapter(slideShowHomePage);
        final PageIndicatorView pageIndicatorView = view.findViewById(R.id.page_indicator);
        pageIndicatorView.setAnimationType(AnimationType.DROP);
        pageIndicatorView.setCount(imgSlide.length);
        pageIndicatorView.setSelection(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Recycler view
        RecyclerView recyclerView= view.findViewById(R.id.rcl_major);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayout.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        MajorAdapter majorAdapter = new MajorAdapter();
        recyclerView.setAdapter(majorAdapter);
    }

}
