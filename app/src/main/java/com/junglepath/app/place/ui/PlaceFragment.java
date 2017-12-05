package com.junglepath.app.place.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.junglepath.app.JunglePath;
import com.junglepath.app.R;
import com.junglepath.app.db.entities.Category;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.detail.ui.DetailActivity;
import com.junglepath.app.place.OnItemClickListener;
import com.junglepath.app.place.PlacePresenter;
import com.junglepath.app.place.ui.adapters.PlaceAdapter;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceFragment extends Fragment implements PlaceView, OnItemClickListener {
    private static final String TAG = PlaceFragment.class.getSimpleName();
    private static final int FRAGMENT_TO_DETAIL = 1;
    private static final String ARG_CATEGORY = "places";
    public static final String ARG_PLACE = "place";
    private Category category;

    @Inject
    PlaceAdapter adapter;

    @Inject
    PlacePresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    LinearLayoutManager mLayoutManager;

    JunglePath app;


    public PlaceFragment() {
        // Required empty public constructor
    }

    public static PlaceFragment newInstance(Category category) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    //    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getParcelable(ARG_CATEGORY);
        }
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
        adapter.clearList();
        adapter.setItems(category.getPlaces());
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        app.getPlaceComponent(this, this, this).inject(this);
    }

    @Override
    public void onItemClick(Place place) {
        navigationToDetail(place);
    }

    private void navigationToDetail(Place place){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(ARG_PLACE, place);
        intent.putExtra("code", FRAGMENT_TO_DETAIL);
        startActivityForResult(intent, FRAGMENT_TO_DETAIL);
    }
}
