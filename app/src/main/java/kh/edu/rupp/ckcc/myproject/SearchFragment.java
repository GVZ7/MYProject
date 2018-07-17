package kh.edu.rupp.ckcc.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private majors[] majors1;
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lv_search);
        EditText editText = view.findViewById(R.id.txt_search);
        initListView();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initListView() {

        arrayList = new ArrayList<String>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Majors1");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Log.d("init_majors", documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                        //arrayAdapter.add(documentSnapshot.getData().get("name").toString());
                        arrayList.add(documentSnapshot.getString("name"));
                    }
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                    arrayAdapter.notifyDataSetChanged();
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                            //Toast.makeText(getActivity(), "Click parent : " + parent.getItemAtPosition(position) , Toast.LENGTH_LONG).show();
                            String st = (String) parent.getItemAtPosition(position);

                            if (st.equals("Geography"))
                                index = 0;
                            else if (st.equals("Physics"))
                                index = 1;
                            else
                                index = 2;

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Majors1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int i = 0;
                                        majors1 = new majors[task.getResult().size()];
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Convert Firestore document to Majors object
                                            majors major = document.toObject(majors.class);
                                            majors1[i] = major;
                                            i++;
                                        }
                                        Context context = view.getContext();
                                        Intent intent = new Intent(context, majordetail_activity.class);
                                        majors major = majors1[index];
                                        Gson gson = new Gson();
                                        String eventJson = gson.toJson(major);
                                        intent.putExtra("Majors1", eventJson);
                                        context.startActivity(intent);
                                    } else {
                                        Toast.makeText(getActivity(), "Load majors error.", Toast.LENGTH_LONG).show();
                                        Log.d("load data", "Load majors error: " + task.getException());
                                    }
                                }
                            });
                            //
                        }
                    });

                } else {
                    Log.d("init_majors", "Error Documents", task.getException());
                }
            }
        });
    }
}