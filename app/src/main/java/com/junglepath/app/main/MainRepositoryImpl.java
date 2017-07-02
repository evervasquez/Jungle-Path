package com.junglepath.app.main;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junglepath.app.db.entities.Category;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.domain.FirebaseDatabaseHelper;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.main.events.MainEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainRepositoryImpl implements MainRepository {
    private static final String TAG = MainRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private FirebaseDatabaseHelper helper;
    private List<Category> categories = new ArrayList<>();

    public MainRepositoryImpl(EventBus eventBus, FirebaseDatabaseHelper helper) {
        this.eventBus = eventBus;
        this.helper = helper;
    }

    @Override
    public void getListPlaces() {
        saveListPharmacies();
    }

    private void saveListPharmacies() {
        final FirebaseDatabase database = helper.getDataReference();
        DatabaseReference myRef = database.getReference("places");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                    categories.add(placeSnapshot.getValue(Category.class));
                }

                for (Category category : categories){
                    Log.i(TAG, "showCategories: " + category.getPlaces());
                }
                postEvent(MainEvent.LIST_SUCCESS, categories, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: ");
                postEvent(MainEvent.LIST_ERROR, databaseError.getMessage());
            }
        });

    }

    private void postEvent(int type, List<Category> category, String errorMessage) {
        MainEvent productEvent = new MainEvent();
        productEvent.setEventType(type);

        if (errorMessage != null) {
            productEvent.setErrorMessage(errorMessage);
        }

        if (category != null) {
            productEvent.setCategories(category);
        }
        eventBus.post(productEvent);
    }

    private void postEvent(int type, String error) {
        postEvent(type, null, error);
    }
}
