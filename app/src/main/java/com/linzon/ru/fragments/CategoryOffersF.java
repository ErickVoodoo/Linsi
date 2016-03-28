package com.linzon.ru.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzon.ru.App;
import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.adapters.CategoryAdapter;
import com.linzon.ru.adapters.PopularAdapter;
import com.linzon.ru.common.RViewScroll;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.POffer;

import java.util.ArrayList;

public class CategoryOffersF extends Fragment {
    RecyclerView recyclerView;
    View view;
    public int selectedCategory = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setRecycler();
        setFab();
        return view;
    }

    public void setRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.categoryRecycler);
        recyclerView.setAdapter(null);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    public void setCategory(int categoryId) {
        if(-1 != categoryId) {
            ((MainActivity) this.getActivity()).showProgressBar();
            this.selectedCategory = categoryId;
            recyclerView.setAdapter(null);
            loadCategoryItems();
        }
    }

    public void loadCategoryItems() {
        if(this.selectedCategory == 0) {
            Log.e("Loaded", String.valueOf(this.selectedCategory));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<POffer> popularOffers = ((App) this.getActivity().getApplication()).getPriceOffers();
            recyclerView.setAdapter(new PopularAdapter(popularOffers, this.getActivity()));
            ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            DBAsync.asyncGetOfferList(this.selectedCategory, new DBAsync.CallbackGetCategory() {
                @Override
                public void onSuccess(ArrayList<OOffer> success) {
                    recyclerView.setAdapter(new CategoryAdapter(success, CategoryOffersF.this.getActivity()));
                    ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
                }

                @Override
                public void onError(String error) {
                    ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
                }
            });
        }
    }

    private void setFab() {
        recyclerView.addOnScrollListener(new RViewScroll(this.getActivity()));
    }
}
