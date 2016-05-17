package com.grability.test.appstoreg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.api.model.Entry;

/**
 * Created by carmen on 16/05/16.
 */
public class AppSummaryFragment extends Fragment {
    private static Entry appSelected;

    public static AppSummaryFragment newInstance(Entry app){
        AppSummaryFragment fragment = new AppSummaryFragment();
        appSelected = app;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.app_summary , container, false);
        getActivity().setTitle("Detalles");
        TextView appName = (TextView) rootView.findViewById(R.id.appName);
        String nameApp = appSelected.getName().getLabel();
        int index = nameApp.indexOf("-");
        if (index > 0){
            nameApp = nameApp.substring(0, index -1);
        }
        appName.setText(nameApp);
        TextView appPrice = (TextView) rootView.findViewById(R.id.appPrice);
        float price = appSelected.getPrice().getAttributePrice().getAmount();
        if (price == 0){
            appPrice.setText("Free");
        }else{
            String currency = appSelected.getPrice().getAttributePrice().getCurrency();
            appPrice.setText(String.valueOf(price) +" "+ currency);
        }
        TextView summary = (TextView) rootView.findViewById(R.id.appSummary);
        summary.setText(appSelected.getSummary().getLabel());
        return rootView;
    }
}
