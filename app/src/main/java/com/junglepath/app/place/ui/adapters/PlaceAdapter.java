package com.junglepath.app.place.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.place.OnItemClickListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.junglepath.app.R;

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Place> placeList;
    private ImageLoader imageLoader;
    private OnItemClickListener listener;

    public PlaceAdapter(List<Place> places, ImageLoader imageLoader, OnItemClickListener listener) {
        this.placeList = places;
        this.imageLoader = imageLoader;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PlaceHolder(inflater.inflate(R.layout.place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaceHolder productHolder = (PlaceHolder) holder;
        productHolder.textDescription.setText(placeList.get(position).getNombre());
        productHolder.textAddress.setText(placeList.get(position).getDireccion());
        productHolder.textHora.setText(placeList.get(position).getTelefono());
        productHolder.setOnClickListener(placeList.get(position), listener);
        imageLoader.load(productHolder.imgProduct, placeList.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void setItems(List<Place> places) {
        for (Place place : places) {
            add(place);
        }
    }

    private void add(Place pharmacy) {
        placeList.add(pharmacy);
        notifyItemInserted(placeList.size());
    }

    private Place getItem(int position) {
        return placeList.get(position);
    }

    public void clearList() {
        placeList.clear();
        notifyDataSetChanged();
    }

    //region ViewHolders
    public static class PlaceHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.textDescription)
        TextView textDescription;
        @BindView(R.id.text_hora)
        TextView textHora;
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.text_address)
        TextView textAddress;

        PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        void setOnClickListener(final Place place, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(place);
                }
            });
        }
    }
}
