package kh.edu.rupp.ckcc.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class activity_department extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Department> arrayList;
    private ArrayAdapter<Department> arrayAdapter;
    private majors[] majors1;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        listView = findViewById(R.id.lv_department);

        Intent intent = getIntent();
        String dep = intent.getStringExtra("Faculties");
        Gson gson = new Gson();
        Faculty faculty = gson.fromJson(dep, Faculty.class);

        TextView textView = findViewById(R.id.txt_faculty);
        textView.setText(faculty.getName());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Faculties").document(faculty.getId()).collection("departments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                arrayList = new ArrayList<Department>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Department department = documentSnapshot.toObject(Department.class);
                        department.setId(documentSnapshot.getId());
                        arrayList.add(department);
                    }
                    arrayAdapter = new ArrayAdapter<Department>(activity_department.this, android.R.layout.simple_list_item_1, arrayList);
                    arrayAdapter.notifyDataSetChanged();
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                            Object st =  parent.getItemAtPosition(position);

                            String st_majors[] = {"Bioengineering", "Natural Resource Mgt and Development", "Biology", "Tourism", "Economic Development",
                                    "Philosophy", "Higher Education Development and Management", "Educational Studies", "Lifelong Learning",
                                    "Computer Science", "Linguistics", "Telecommunication and Electronic Engineering", "Khmer Literature",
                                    "Sociology", "Geography", "History", "Social Work", "Community Development", "Psychology",
                                    "Environmental Science", "Information Technology Engineering", "Physics", "Mathematics", "Media and Communication","Chemistry"};

                            for(int i=0; i<st_majors.length; i++){
                                if(st.toString().equals(st_majors[i])){
                                    index = i;
                                    break;
                                }
                            }

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Majors1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int i = 0;
                                        majors1 = new majors[task.getResult().size()];
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            majors major = document.toObject(majors.class);
                                            majors1[i] = major;
                                            i++;
                                        }
                                        //Context context = view.getContext();
                                        Intent intent = new Intent(activity_department.this, majordetail_activity.class);
                                        majors major = majors1[index];
                                        Gson gson = new Gson();
                                        String eventJson = gson.toJson(major);
                                        intent.putExtra("Majors1", eventJson);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(activity_department.this, "Load majors error.", Toast.LENGTH_LONG).show();
                                        Log.d("load data", "Load majors error: " + task.getException());
                                    }
                                }
                            });

//                            Context context = view.getContext();
//                            Intent intent = new Intent(context, majordetail_activity.class);
//                            Department department1 = arrayList.get(position);
//                            Gson gson = new Gson();
//                            String major = gson.toJson(department1);
//                            intent.putExtra("departments", major);
//                            context.startActivities(new Intent[]{intent});

                        }

                    });

                } else {
                    Log.d("ListView Department", "Error Documents", task.getException());
                }
            }
        });
    }
}
