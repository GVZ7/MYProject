package kh.edu.rupp.ckcc.myproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.MajorHolder>{

//load data to recycler view
    private majors[] majors1;

    public MajorAdapter(){majors1=new majors[0];}

    //pel pull data mor
    public void setMajors(majors[] majors){
        this.majors1=majors;
        notifyDataSetChanged();//oy refresh data

    }


    @NonNull
    @Override
    public MajorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.activity_home_holder,parent,false);
        MajorHolder majorHolder = new MajorHolder(view);
        return  majorHolder;
    }

    @Override
    //bind data to view holder
    public void onBindViewHolder(@NonNull MajorHolder holder, int position) {
        //display major name
         majors major = majors1[position];
        holder.majorname.setText(major.getName());

        // Display major image
        holder.imgmajor.setImageURI(major.getImg_major());

    }



    @Override
    public int getItemCount() {
        return majors1.length;
    }
    public class MajorHolder extends RecyclerView.ViewHolder{
        private TextView majorname;
        private SimpleDraweeView imgmajor;

        public MajorHolder(View itemView) {

            super(itemView);

            majorname = itemView.findViewById(R.id.major_name);
            imgmajor=itemView.findViewById(R.id.img_major);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, majordetail_activity.class);

                    // Pass selected emajor to EventDetailActivity
                    int index = getAdapterPosition();
                    majors major  = majors1[index];
                    Gson gson = new Gson();
                    String eventJson = gson.toJson(major);
                    intent.putExtra("Majors1", eventJson);

                    context.startActivity(intent);


                }
            });
        }
    }
}
