package kh.edu.rupp.ckcc.myproject;

import android.bluetooth.BluetoothClass;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.MajorHolder>{
    @NonNull
    @Override
    public MajorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.activity_home_holder,parent,false);
        MajorHolder majorHolder = new MajorHolder(view);
        return  majorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MajorHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 10;
    }
    public class MajorHolder extends RecyclerView.ViewHolder{

        public MajorHolder(View itemView) {
            super(itemView);
        }
    }
}
