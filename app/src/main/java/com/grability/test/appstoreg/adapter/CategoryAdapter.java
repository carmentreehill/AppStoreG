package com.grability.test.appstoreg.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.api.model.Entry;
import com.grability.test.appstoreg.fragment.AppListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>   {
    Context context;
    List<Entry> categories;
    List<Entry> allCategories;
    private static List<Entry> categoryNotRepeat = new ArrayList<>();
    ProgressDialog progress;
    FillAppTask fillAppTask;

    public CategoryAdapter(Context context){
        this.context = context;
        this.categories = new ArrayList<>();
        this.allCategories = new ArrayList<>();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View categoryView = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        return new CategoryViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        Entry categoryItem = categories.get(i);
        String completeName = categoryItem.getCategory().getAttributes().getLabel();
        categoryViewHolder.setName(completeName);
        categoryViewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                int pos = position;
                String categoryName = categories.get(position).getCategory().getAttributes().getLabel();
                getAppsByCat(categoryName);
            }
        });
    }

    private void getAppsByCat(String categorySelected){
        fillAppTask = new FillAppTask(context, categorySelected);
        fillAppTask.execute((Void) null);
    }

    public class FillAppTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;
        private String categorySelected;
        List<Entry> appsWithCategory = new ArrayList<>();


        FillAppTask(Context context, String cat){
            activity = context;
            categorySelected = cat;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(activity);
            progress.setMessage("Loading...");
            progress.setProgressStyle(progress.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < allCategories.size(); i++) {
                String category = allCategories.get(i).getCategory().getAttributes().getLabel();
                if (category.equals(categorySelected)){
                    appsWithCategory.add(allCategories.get(i));
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (progress != null)
                progress.dismiss();
            if (appsWithCategory!= null){
                callFragmentApps(appsWithCategory);
            }
            super.onPostExecute(aBoolean);
        }
    }

    private void callFragmentApps(List<Entry> appsWithCategory){
        Fragment fragment = AppListFragment.newInstance(appsWithCategory);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .addToBackStack("2")
                .replace(R.id.main_content, fragment)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    /**
     * Llena la lista de categorias
     * @param categories
     */
    public void addAll(List<Entry> categories) {
        this.categories.clear();
        this.allCategories.clear();
        this.allCategories.addAll(categories);
        if (categories == null)
            throw new NullPointerException("Los campos no pueden estar vacíos");

        for (int i = 0; i < categories.size(); i++) {
            boolean exists = false;
            for (int j = 0; j <categoryNotRepeat.size() ; j++) {
                String fisrtCat = categories.get(i).getCategory().getAttributes().getLabel();
                String secondCat = categoryNotRepeat.get(j).getCategory().getAttributes().getLabel();
                if (fisrtCat.equals(secondCat)) {
                    exists = true;
                }
            }
            if (!exists){
                categoryNotRepeat.add(categories.get(i));
            }
        }
        this.categories.addAll(categoryNotRepeat);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    /**
     *  ViewHolder que configura
     *  la lista de las categorías.
     *
     */
    public class CategoryViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView fistLetter;
        private ItemClickListener clickListener;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_category);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getLayoutPosition(), false);
        }
    }
}
