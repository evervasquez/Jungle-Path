package com.junglepath.app.main.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.junglepath.app.JunglePath;
import com.junglepath.app.Login.ui.LoginActivity;
import com.junglepath.app.NotFoundPharmaciesException;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.main.MainPresenter;
import com.junglepath.app.main.OnItemClickListener;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.junglepath.app.R;
import com.junglepath.app.main.ui.adapters.PlaceAdapter;
import com.junglepath.app.preferences.MyPreferencesActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCELED = 0;
    public static final String PREFERENCES_DISTANCE = "PREFERENCES_DISTANCE";

    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private JunglePath app;
    LinearLayoutManager mLayoutManager;

    @Inject
    MainPresenter presenter;

    @Inject
    PlaceAdapter adapter;

    private List<Place> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (JunglePath) getApplication();
        setupInjection();
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    //region MENU INFLATER
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getPharmaciesBySearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resetPharmacies(newText.length());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void resetPharmacies(int length) {
        try {
            if (length == 0) {
                verifyPharmacies();
                setItems(placeList);
            }
        } catch (NotFoundPharmaciesException e) {
            Snackbar.make(activityMain, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getPharmaciesBySearch(String query) {
        try {
            verifyPharmacies();
            List<Place> result = filter(placeList, query);
            setItems(result);
            if(result.size() == 0){
                Snackbar.make(activityMain, "El producto no se ha encontrado", Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(activityMain, String.format("El producto se ha encontrado en %d lugar", result.size()), Snackbar.LENGTH_LONG).show();
            }
        } catch (NotFoundPharmaciesException e) {
            Snackbar.make(activityMain, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_location:
                getLocation();
                return true;
            case R.id.action_reset:
                setItems(placeList);
                return true;
            case R.id.action_config:
                navigateToPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verifyPharmacies() throws NotFoundPharmaciesException {
        if (placeList == null) {
            throw new NotFoundPharmaciesException("Espere mientras se cargan los lugares");
        }
    }

    private void getLocation() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                if (longitude != -1 && latitude != -1) {
                    getPharmaciesByLongitude(latitude, longitude);
                }
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }catch (NullPointerException e){
            Snackbar.make(activityMain, "Debe habilitar sus permisos para el gps", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getPharmaciesByLongitude(double latitude, double longitude) {
        try {
            verifyPharmacies();
            List<Place> pharmaciesByLocation = filterByLongitude(placeList, latitude, longitude);
            setItems(pharmaciesByLocation);
            Snackbar.make(activityMain,
                    String.format("Se han encontrado %d Lugares a %s metros", pharmaciesByLocation.size(),
                            getPreference()) , Snackbar.LENGTH_LONG).show();
        } catch (NotFoundPharmaciesException e) {
            Snackbar.make(activityMain, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region MainView

    @Override
    public void initComponents() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        setTitle(getString(R.string.pharmacy_text_title));
    }

    private void refresh() {

    }

    public void setupInjection() {
        app.getMainComponent(this, this, this).inject(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideElements() {

    }

    @Override
    public void showElements() {

    }

    @Override
    public void showListPharmacies(List<Place> places) {
        this.placeList = places;
        setItems(places);
    }

    private void setItems(List<Place> places) {
        adapter.clearList();
        adapter.setItems(places);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showListPharmaciesErrors(String messageError) {
        Snackbar.make(activityMain, getString(R.string.pharmacy_message_success), Snackbar.LENGTH_SHORT).show();
    }

    //endregion

    private void navigateToPreferences() {
        Intent i = new Intent(this, MyPreferencesActivity.class);
        i.putExtra(PREFERENCES_DISTANCE, getPreference());
        startActivityForResult(i, RESULT_OK);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        //intent
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Place place) {
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra("pharmacy", place);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == MainActivity.RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                savePreference(result);
            } else if (resultCode == MainActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void savePreference(int preference) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(PREFERENCES_DISTANCE, preference);
        editor.apply();
        Snackbar.make(activityMain,
                String.format("Se ha configurado la distancia a %d metros", preference),
                Snackbar.LENGTH_LONG).show();
    }

    private int getPreference() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int distance = prefs.getInt(PREFERENCES_DISTANCE, -1);
        if (distance == -1) {
            distance = 2000;
        }
        return distance;
    }

    //region FILTER

    public List<Place> filter(List<Place> places, String query) {
        List<Place> result = new ArrayList<Place>();
        for (Place place : places) {
//            boolean status = false;
//            List<Medicines> medicines = pharmacy.getMedicines();
//            for (Medicines medicine : medicines) {
//                try {
//                    if (medicine.getName().toLowerCase().contains(query)) {
//                        status = true;
//                    }
//                } catch (NullPointerException e) {
//                    break;
//                }
//            }
//
//            if (status) {
//                result.add(pharmacy);
//            }
        }
        return result;
    }

    public List<Place> filterByLongitude(List<Place> placeList, double latitude, double longitude) {
        List<Place> result = new ArrayList<Place>();
        for (Place place : placeList) {
            boolean status = false;
            try {
                float[] results = new float[1];
                Location.distanceBetween(latitude, longitude, Double.parseDouble(place.getLatitude()),
                        Double.parseDouble(place.getLongitud()), results);
                float distanceInMeters = results[0];
                if (distanceInMeters < getPreference()) {
                    status = true;
                }
            } catch (NullPointerException e) {
                break;
            }

            if (status) {
                result.add(place);
            }
        }
        return result;
    }

    //endregion
}

