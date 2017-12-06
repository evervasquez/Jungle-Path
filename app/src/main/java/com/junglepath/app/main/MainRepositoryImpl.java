package com.junglepath.app.main;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.junglepath.app.db.entities.Category;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.domain.FirebaseDatabaseHelper;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.main.events.MainEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainRepositoryImpl implements MainRepository {
    private static final String TAG = MainRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private FirebaseDatabaseHelper helper;
    ArrayList<Category> categories;

    public MainRepositoryImpl(EventBus eventBus, FirebaseDatabaseHelper helper) {
        this.eventBus = eventBus;
        this.helper = helper;
    }

    @Override
    public void getListPlaces(JSONObject obj) {
        saveListPharmacies(obj);
    }


    private void saveListPharmacies(JSONObject obj) {
        categories = new ArrayList<>();
        try {
            JSONArray places = obj.getJSONArray("places");
            categories = parseCategory(places);
            postEvent(MainEvent.LIST_SUCCESS, categories, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Category> parseCategory(JSONArray categories){
        ArrayList<Category> result = new ArrayList<>();
        for(int i=0; i<categories.length(); i++){
            ArrayList<Place> listPlaces = new ArrayList<>();
            Category category = new Category();
            try {
                Log.i(TAG, "parseCategory: " + categories.getJSONObject(i).getString("nombre"));
                category.setNombre(categories.getJSONObject(i).getString("nombre"));
                category.setId(categories.getJSONObject(i).getInt("id"));
                JSONArray places = categories.getJSONObject(i).getJSONArray("places");
                for(int j=0; j<places.length(); j++){
                    Place place = new Place();
                    ArrayList<String> imagenes = new ArrayList<String>();
                    place.setDescripcion(places.getJSONObject(j).getString("descripcion"));
                    place.setDireccion(places.getJSONObject(j).getString("direccion"));
                    place.setLatitude(places.getJSONObject(j).getString("latitude"));
                    place.setLongitud(places.getJSONObject(j).getString("longitud"));
                    place.setNombre(places.getJSONObject(j).getString("nombre"));
                    place.setTelefono(places.getJSONObject(j).getString("telefono"));
                    JSONArray images = places.getJSONObject(j).getJSONArray("imagenes");
                    for(int im=0; im<images.length(); im++){
                          imagenes.add(images.getJSONObject(im).getString("url"));
                    }
                    place.setImagenes(imagenes);
                    listPlaces.add(place);
                }
                category.setPlaces(listPlaces);
                result.add(category);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void postEvent(int type, ArrayList<Category> category, String errorMessage) {
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
