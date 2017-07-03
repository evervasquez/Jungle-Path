package com.junglepath.app.place.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junglepath.app.JunglePath;
import com.junglepath.app.R;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.place.OnItemClickListener;
import com.junglepath.app.place.PlacePresenter;
import com.junglepath.app.place.ui.adapters.PlaceAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlaceFragment extends Fragment implements PlaceView, OnItemClickListener {
    private static final String TAG = PlaceFragment.class.getSimpleName();
    private static List<Place> placeList;

    @Inject
    PlaceAdapter adapter;

    @Inject
    PlacePresenter presenter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    LinearLayoutManager mLayoutManager;

    JunglePath app;


    public PlaceFragment() {
        // Required empty public constructor
    }

    public static PlaceFragment newInstance(List<Place> places) {
        PlaceFragment fragment = new PlaceFragment();
        placeList = places;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        ButterKnife.bind(this, view);
        app = (JunglePath) getActivity().getApplication();
        setupInjection();
        initComponents();
        return view;
    }


    public void initComponents() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.setItems(placeList);
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        app.getPlaceComponent(this, this, this).inject(this);
    }

    @Override
    public void onItemClick(Place place) {
        Log.i(TAG, "onItemClick: " + place.getNombre());
    }
}
