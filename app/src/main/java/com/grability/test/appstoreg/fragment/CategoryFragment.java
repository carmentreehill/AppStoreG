package com.grability.test.appstoreg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.adapter.CategoryAdapter;
import com.grability.test.appstoreg.api.model.Entry;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by carmen on 15/05/16.
 */
public class CategoryFragment extends Fragment {
    private RecyclerView categoryList;
    private CategoryAdapter adapter;
    private static List<Entry> categories;

    public static CategoryFragment newInstance(List<Entry> categoryCardList){
        categories = categoryCardList;
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    public CategoryFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_recyclerview, container, false);
        getActivity().setTitle("Category");
        categoryList = (RecyclerView) root.findViewById(R.id.category_list);
        adapter = new CategoryAdapter(getActivity());
        categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryList.setAdapter(adapter);
        if (this.categories != null){
            adapter.addAll(categories);
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.categories != null){
            adapter.addAll(categories);
        }
    }
}
