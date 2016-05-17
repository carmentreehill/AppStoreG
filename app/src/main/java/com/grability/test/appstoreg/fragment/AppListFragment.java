package com.grability.test.appstoreg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.adapter.AppListAdapter;
import com.grability.test.appstoreg.api.model.Entry;

import java.util.List;

/**
 * Created by carmen on 16/05/16.
 */
public class AppListFragment extends Fragment {
    private static List<Entry> appList;
    private RecyclerView appListRecycler;
    private AppListAdapter adapter;

    public static AppListFragment newInstance(List<Entry> apps){
        appList = apps;
        AppListFragment fragment = new AppListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_recyclerview, container, false);
        getActivity().setTitle("Apps");
        appListRecycler = (RecyclerView) root.findViewById(R.id.category_list);
        adapter = new AppListAdapter(getActivity());
        appListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(getResources().getBoolean(R.bool.portrait)) {
            appListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            appListRecycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            appListRecycler.setHasFixedSize(true);
        }
        appListRecycler.setAdapter(adapter);
        if (this.appList != null){
            adapter.addAll(appList);
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.appList != null){
            adapter.addAll(appList);
        }
    }
}
