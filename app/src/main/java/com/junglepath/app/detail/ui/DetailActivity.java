package com.junglepath.app.detail.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.junglepath.app.JunglePath;
import com.junglepath.app.R;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.libs.Utils;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.place.ui.PlaceFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView{
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarImage)
    ImageView image;
    @BindView(R.id.direction)
    FloatingActionMenu floatingActionMenu;

    @BindView(R.id.text_description)
    TextView text_description;

    @BindView(R.id.text_direccion)
    TextView text_direccion;

    @BindView(R.id.text_telefono)
    TextView text_telefono;

    @BindView(R.id.share)
    com.github.clans.fab.FloatingActionButton floatingShared;

    @BindView(R.id.call)
    com.github.clans.fab.FloatingActionButton floatingActionCall;

    @BindView(R.id.find)
    com.github.clans.fab.FloatingActionButton floatingActionDirection;

    @Inject
    Utils utils;

    private JunglePath app;

    @Inject
    ImageLoader imageLoader;

    private static final int REQUEST_CALL_PHONE = 1;

    Place current;
    int code;
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

        floatingShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared();
            }
        });

    }

    private void shared(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = String.format("%s - %s -> %s" , current.getNombre(),
                current.getDescripcion(), current.getImagen());
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
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
        String uri = String.format("google.navigation:q=%s,%s", current.getLatitude(),
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
            setResult(code);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initComponents() {
        current = getIntent().getParcelableExtra(PlaceFragment.ARG_PLACE);
        code = getIntent().getIntExtra("code", -1);
        imageLoader.load(image, current.getImagen());
        collapsing.setTitle(current.getNombre());

        if (utils.verifyVersionMoreLollipop()) {
            verifyStoragePermissions(this);
        }

        text_description.setText(current.getDescripcion());
        text_direccion.setText(current.getDireccion());
        text_telefono.setText(current.getTelefono());
    }

    private void initInjection() {
        app.getDetailComponent(this, this).inject(this);
    }

    @Override
    public void onBackPressed() {
        setResult(code);
        super.onBackPressed();
    }
}
