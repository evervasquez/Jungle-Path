package com.junglepath.app.detail.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionMenu;
import com.junglepath.app.JunglePath;
import com.junglepath.app.R;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.libs.Utils;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.place.ui.PlaceFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView{
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsing;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbarImage)
    ImageView image;
    @Bind(R.id.direction)
    FloatingActionMenu floatingActionMenu;

    @Bind(R.id.call)
    com.github.clans.fab.FloatingActionButton floatingActionCall;

    @Bind(R.id.find)
    com.github.clans.fab.FloatingActionButton floatingActionDirection;

    @Inject
    Utils utils;

    private JunglePath app;

    @Inject
    ImageLoader imageLoader;

    private static final int REQUEST_CALL_PHONE = 1;

    Place current;

    private static String[] PERMISSIONS_APP = {
            Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        app = (JunglePath) getApplication();
        initInjection();
        initComponents();
        initDisplayHome();

        //fab
        floatingActionDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMaps();
            }
        });

        floatingActionCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CALL_PHONE)) {


            } else {
                ActivityCompat.requestPermissions(activity,
                        PERMISSIONS_APP,
                        REQUEST_CALL_PHONE);
            }
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + current.getTelefono()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startActivity(intent);
    }

    private void navigateToMaps() {
        String uri = String.format("google.navigation:q=%f,%f", current.getLatitude(),
                        current.getLongitud());
        Uri gmmIntentUri = Uri.parse(uri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void initDisplayHome() {
//        collapsing.setTitle(getString(R.string.title_activity_new_product));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initComponents() {
        current = getIntent().getParcelableExtra(PlaceFragment.ARG_PLACE);
        imageLoader.load(image, current.getImagen());
        collapsing.setTitle(current.getNombre());

        if (utils.verifyVersionMoreLollipop()) {
            verifyStoragePermissions(this);
        }
    }

    private void initInjection() {
        app.getDetailComponent(this, this).inject(this);
    }

}
