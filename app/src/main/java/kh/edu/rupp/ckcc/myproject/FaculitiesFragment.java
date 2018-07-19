package kh.edu.rupp.ckcc.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FaculitiesFragment extends Fragment {

    private ArrayList<Faculty> arrayList;
    private ArrayAdapter<Faculty> arrayAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_faculities, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lv_fac);

        initListView();
    }

    private void initListView() {

        arrayList = new ArrayList<Faculty>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Faculties");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Faculty faculty = documentSnapshot.toObject(Faculty.class);
                        faculty.setId(documentSnapshot.getId());
                        arrayList.add(faculty);

                    }
                    arrayAdapter = new ArrayAdapter<Faculty>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                    arrayAdapter.notifyDataSetChanged();
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Context context = view.getContext();
                            Intent intent = new Intent(context, activity_department.class);
                            Faculty faculty1 = arrayList.get(position);
                            Gson gson = new Gson();
                            String dep = gson.toJson(faculty1);
                            intent.putExtra("Faculties", dep);
                            context.startActivities(new Intent[]{intent});
                        }
                    });

                } else {
                    Log.d("init_Facs", "Error Documents", task.getException());
                }
            }
        });

    }
}