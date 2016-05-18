package com.grability.test.appstoreg.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.adapter.CategoryAdapter;
import com.grability.test.appstoreg.api.model.Entry;

import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
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
        if(getResources().getBoolean(R.bool.portrait)) {
            categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            categoryList.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            categoryList.setHasFixedSize(true);
        }
        categoryList.setAdapter(adapter);
        if (this.categories != null){
            adapter.addAll(categories);
        }

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mCellphone = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        // Verifica la conectividad de internet para
        // mostrar mensaje

        if (!mWifi.isConnected() && !mCellphone.isConnected()) {
            OfflineDialogFragment confirmDialog = OfflineDialogFragment.newInstance(); // En modo offline, muestra un mensaje indicando el modo
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            confirmDialog.show(fragmentManager, "tag");
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

    /**
     * Fragmento para mostrar el mensaje de modo offline activado+
     *
     * @return
     */
    public static class OfflineDialogFragment extends DialogFragment {
        public static OfflineDialogFragment newInstance() {
            OfflineDialogFragment fragment = new OfflineDialogFragment();
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.mode_offline)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
