package com.arfeenkhan.multiplerecyclerviewinjava;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arfeenkhan.multiplerecyclerviewinjava.Interface.IFirebaseLoaderListener;
import com.arfeenkhan.multiplerecyclerviewinjava.adapter.MyGroupAdapter;
import com.arfeenkhan.multiplerecyclerviewinjava.adapter.MyItemAdapter;
import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemData;
import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemGroup;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IFirebaseLoaderListener {
    ProgressDialog dialog;
    IFirebaseLoaderListener iFirebaseLoaderListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //init
        myData = FirebaseDatabase.getInstance().getReference("MyData");
        iFirebaseLoaderListener = this;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);

        my_recycler_view = findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //Load data
        getFirebaseData();

        Crashlytics.setUserName("Mahesh Prasad");
        Crashlytics.setUserEmail("mahesh@arfeenkhan.com");
        Crashlytics.setUserIdentifier("Developer");
    }

    private void getFirebaseData() {
        dialog.show();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapShot : dataSnapshot.getChildren()) {
                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeaderTitle(groupSnapShot.child("headerTitle").getValue(true).toString());
                    GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>() {
                        @Override
                        public int hashCode() {
                            return super.hashCode();
                        }
                    };

                    itemGroup.setListItem(groupSnapShot.child("listitem").getValue(genericTypeIndicator));
                    itemGroups.add(itemGroup);
                }
                iFirebaseLoaderListener.onFirebaseLoadSuccess(itemGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoaderListener.onFirebaseFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyGroupAdapter adapter = new MyGroupAdapter(this, itemGroupList);
        my_recycler_view.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
