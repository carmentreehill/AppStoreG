package com.grability.test.appstoreg.adapter;

import android.content.Context;
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
 * Created by carmen on 15/05/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>   {
    Context context;
    List<Entry> categories;
    List<Entry> allCategories;
    private static List<Entry> categoryNotRepeat = new ArrayList<>();

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
        List<Entry> appsWithCategory = new ArrayList<>();
        for (int i = 0; i < this.allCategories.size(); i++) {
            String category = this.allCategories.get(i).getCategory().getAttributes().getLabel();
            if (category.equals(categorySelected)){
                appsWithCategory.add(this.allCategories.get(i));
            }
        }
        if (appsWithCategory!= null){
            Fragment fragment = AppListFragment.newInstance(appsWithCategory);
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .addToBackStack("2")
                    .replace(R.id.main_content, fragment)
                    .commit();
            fragmentManager.executePendingTransactions();
        }
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
        TextView name;
        TextView fistLetter;
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
