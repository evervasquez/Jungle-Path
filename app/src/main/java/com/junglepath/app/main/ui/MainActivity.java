package com.junglepath.app.main.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.junglepath.app.JunglePath;
import com.junglepath.app.Login.ui.LoginActivity;
import com.junglepath.app.db.entities.Category;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.detail.ui.DetailActivity;
import com.junglepath.app.main.MainPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.junglepath.app.R;
import com.junglepath.app.main.ui.adapters.SearchableAdapter;
import com.junglepath.app.main.ui.adapters.ViewPagerAdapter;
import com.junglepath.app.place.ui.PlaceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView,
        SearchView.OnQueryTextListener, OnItemSearchClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SEARCH_TO_DETAIL = 0;
    private ViewPagerAdapter adapter;
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCELED = 0;
    public static final String PREFERENCES_DISTANCE = "PREFERENCES_DISTANCE";

    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.search_list)
    ListView search_list;

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private JunglePath app;

    @Inject
    MainPresenter presenter;

    @Inject
    SearchableAdapter filterAdapter;

    SearchView searchView;

    List<Place> placesListSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region MainView

    @Override
    public void initComponents() {
        setTitle(getString(R.string.pharmacy_text_title));
        search_list.setAdapter(filterAdapter);
    }


    public void setupInjection() {
        app.getMainComponent(this, this, this, this).inject(this);
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
    public void showCategories(ArrayList<Category> categories) {
        parseData(categories);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.clear();
        for (Category category : categories) {
            adapter.addFragment(PlaceFragment.newInstance(category), category.getNombre());
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void parseData(List<Category> categories) {
        placesListSearch = new ArrayList<Place>();
        for (Category category : categories) {
            for (Place place : category.getPlaces()) {
                placesListSearch.add(place);
            }
        }
        initListView(placesListSearch);
    }

    private void initListView(List<Place> places) {
        search_list.setVisibility(View.GONE);
        filterAdapter.setItems(places);
    }

    @Override
    public void showCategoriesErrors(String messageError) {
        Snackbar.make(activityMain, getString(R.string.pharmacy_message_success), Snackbar.LENGTH_SHORT).show();
    }

    //endregion


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: " + resultCode);
        if (requestCode == SEARCH_TO_DETAIL){
            searchView.setIconified(true);

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
    //endregion


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.length() > 0) {
            showSearchList();
        } else {
            hideSearchList();
        }

        filterAdapter.getFilter().filter(newText);
        return false;
    }

    private void showSearchList() {
        search_list.setBackgroundColor(Color.WHITE);
        search_list.setVisibility(View.VISIBLE);
    }

    private void hideSearchList() {
        search_list.setBackgroundColor(Color.TRANSPARENT);
        search_list.setVisibility(View.GONE);
    }

    @Override
    public void onClickSearch(Place place) {
        navigationToDetail(place);
    }


    private void navigationToDetail(Place place) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(PlaceFragment.ARG_PLACE, place);
        intent.putExtra("code", SEARCH_TO_DETAIL);
        startActivityForResult(intent, SEARCH_TO_DETAIL);
    }
}

