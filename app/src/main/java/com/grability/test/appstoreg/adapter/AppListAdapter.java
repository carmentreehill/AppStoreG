package com.grability.test.appstoreg.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.api.model.Entry;
import com.grability.test.appstoreg.api.model.Image;
import com.grability.test.appstoreg.fragment.AppSummaryFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 16/05/16.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder>{
    Context context;
    List<Entry> appList;

    public AppListAdapter(Context context){
        this.context = context;
        appList = new ArrayList<>();
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View appView = LayoutInflater.from(context).inflate(R.layout.item_apps, parent, false);

        return new AppListViewHolder(appView);
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        Entry appItem = appList.get(position);
        String category = appItem.getCategory().getAttributes().getLabel();
        String appName = appItem.getName().getLabel();
        int index = appName.indexOf("-");
        if (index > 0){
            appName = appName.substring(0, index -1);
        }
        holder.setName(appName);
        holder.setIndex(String.valueOf(position+1));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Entry appSelected = appList.get(position);
                Fragment fragment = AppSummaryFragment.newInstance(appSelected);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .addToBackStack("3")
                        .replace(R.id.main_content, fragment)
                        .commit();
                fragmentManager.executePendingTransactions();
            }
        });

        for (Image image : appItem.getImage()) {
            if (image.getAttribute().getHeight() == 100)
                holder.setImage(image.getLabel());
        }

    }

    /**
     * Llena la lista de aplicaciones
     * @param apps
     */
    public void addAll(List<Entry> apps) {
        this.appList.clear();
        if (apps == null)
            throw new NullPointerException("Los campos no pueden estar vacíos");

        this.appList.addAll(apps);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    /**
     * ViewHolder que configura
     *  la lista de aplicaciones.
     */
    public class AppListViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView index;
        private ItemClickListener clickListener;
        private ImageView image;
        private int IMG_SIZE_PX;
        private static final int IMG_SIZE_DP = 112;

        public AppListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_app);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image_app);
            index = (TextView) itemView.findViewById(R.id.index);
            setupDimensions();
        }

        public TextView getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index.setText((index)+".");
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        public ImageView getImage() {
            return image;
        }

        private void setupDimensions() {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            IMG_SIZE_PX = IMG_SIZE_DP * (metrics.densityDpi / 160);
        }

        public void setImage(String urlImage) {
            Picasso.with(context)
                    .load(urlImage)
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(IMG_SIZE_PX, IMG_SIZE_PX)
                    .error(R.mipmap.ic_launcher)
                    .into(image);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getLayoutPosition(), false);
        }
    }
}
