package com.junglepath.app.main.ui.adapters;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.junglepath.app.R;
import com.junglepath.app.db.entities.Place;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.main.ui.OnItemSearchClickListener;

public class SearchableAdapter extends BaseAdapter implements Filterable {

    private List<Place>originalData = null;
    private List<Place>filteredData = null;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;
    private ItemFilter mFilter = new ItemFilter();
    private OnItemSearchClickListener listener;

    public SearchableAdapter(Context context, List<Place> data, ImageLoader imageLoader, OnItemSearchClickListener listener) {
        this.filteredData = data ;
        this.originalData = data ;
        this.listener = listener;
        this.imageLoader = imageLoader;
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<Place> places) {
        this.originalData = places;
    }
    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            holder.text = (TextView) convertView.findViewById(R.id.list_view);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_place);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(filteredData.get(position).getNombre());
        imageLoader.load(holder.imageView, filteredData.get(position).getImagen());
        holder.setOnClickListener(filteredData.get(position), listener);
        return convertView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView imageView;
        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void setOnClickListener(final Place place, final OnItemSearchClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickSearch(place);
                }
            });
        }

    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Place> list = originalData;

            int count = list.size();
            final ArrayList<Place> nlist = new ArrayList<Place>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getNombre();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Place>) results.values;
            notifyDataSetChanged();
        }

    }
}