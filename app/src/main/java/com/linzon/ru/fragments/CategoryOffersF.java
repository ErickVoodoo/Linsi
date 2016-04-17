package com.linzon.ru.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linzon.ru.App;
import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.adapters.CategoryAdapter;
import com.linzon.ru.adapters.PopularAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.RViewScroll;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.POffer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CategoryOffersF extends Fragment {
    RecyclerView recyclerView;
    View view;
    Spinner categorySortSpinner;
    public int selectedCategory = -1;
    private ArrayList<OOffer> offers;
    private TextView filterCountTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setRecycler();
        setFab();
        setSpinner();
        setTextView();
        return view;
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {
        public SpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt, Color.BLACK);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt, Color.WHITE);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent, int color) {
            LayoutInflater inflater = CategoryOffersF.this.getActivity().getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_item, parent, false);
            TextView textView = (TextView) mySpinner.findViewById(R.id.spinnerText);
            textView.setText(Constants.SortArray[position]);
            textView.setTextColor(color);

            ImageView spinnerImage = (ImageView) mySpinner.findViewById(R.id.spinnerImage);
            if(color == Color.BLACK) {
                spinnerImage.setVisibility(View.GONE);
            } else {
                spinnerImage.setVisibility(View.VISIBLE);
            }
            return mySpinner;
        }
    }

    private void setTextView() {
        filterCountTextView = (TextView) view.findViewById(R.id.filterCountTextView);
    }

    private void setSpinner() {
        categorySortSpinner = (Spinner) view.findViewById(R.id.categorySortSpinner);
        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(CategoryOffersF.this.getActivity(), android.R.layout.simple_spinner_dropdown_item, Constants.SortArray);
        SpinnerAdapter adapter = new SpinnerAdapter(this.getActivity().getApplicationContext(), R.layout.spinner_item, Constants.SortArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySortSpinner.setAdapter(adapter);
        categorySortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (offers != null) {
                    switch (position) {
                        case 0: {
                            sortByName();
                            break;
                        }
                        case 1: {
                            sortByPriceDown();
                            break;
                        }
                        case 2: {
                            sortByPriceUp();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            loadCategoryItems(null);
        }
    }

    public void setFilter(String[] params) {
        ((MainActivity) this.getActivity()).showProgressBar();
        this.selectedCategory = -2;
        recyclerView.setAdapter(null);
        loadCategoryItems(params);
    }

    public void loadCategoryItems(String[] filterParams) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        filterCountTextView.setVisibility(View.GONE);
        categorySortSpinner.setVisibility(View.VISIBLE);
        if(this.selectedCategory == 0) {
            categorySortSpinner.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<POffer> popularOffers = ((App) this.getActivity().getApplication()).getPriceOffers();
            recyclerView.setAdapter(new PopularAdapter(popularOffers, this.getActivity()));
            ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
        } else if(this.selectedCategory == -2) {
            ArrayList<OOffer> filteredOffers = DBHelper.getInstance().getFilteredOrders(filterParams[0], filterParams[1], filterParams[2], filterParams[3], filterParams[4]);
            offers = filteredOffers;
            filterCountTextView.setText("Количество результатов: " + filteredOffers.size());
            filterCountTextView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new CategoryAdapter(filteredOffers, this.getActivity()));
        } else {
            DBAsync.asyncGetOfferList(this.selectedCategory, new DBAsync.CallbackGetCategory() {
                @Override
                public void onSuccess(ArrayList<OOffer> success) {
                    offers = success;

                    switch (categorySortSpinner.getSelectedItemPosition()) {
                        case 0: {
                            sortByName();
                            break;
                        }
                        case 1: {
                            sortByPriceUp();
                            break;
                        }
                        case 2: {
                            sortByPriceDown();
                            break;
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
                }
            });
        }
        ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
    }

    private void setFab() {
        recyclerView.addOnScrollListener(new RViewScroll(this.getActivity()));
    }

    private void sortByPriceUp() {
        Collections.sort(offers, new Comparator<OOffer>() {
            @Override
            public int compare(OOffer lhs, OOffer rhs) {
                try {
                    if (Integer.parseInt(lhs.getPrice()) > Integer.parseInt(rhs.getPrice())) {
                        return -1;
                    } else if (Integer.parseInt(lhs.getPrice()) < Integer.parseInt(rhs.getPrice())) {
                        return 1;
                    } else if (Integer.parseInt(lhs.getPrice()) == Integer.parseInt(rhs.getPrice())) {
                        return 0;
                    }
                    return 0;
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                return 0;
            }
        });
        recyclerView.setAdapter(new CategoryAdapter(offers, CategoryOffersF.this.getActivity()));
        ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
    }

    private void sortByPriceDown() {
        Collections.sort(offers, new Comparator<OOffer>() {
            @Override
            public int compare(OOffer lhs, OOffer rhs) {
                try {
                    if (Integer.parseInt(lhs.getPrice()) > Integer.parseInt(rhs.getPrice())) {
                        return 1;
                    } else if (Integer.parseInt(lhs.getPrice()) < Integer.parseInt(rhs.getPrice())) {
                        return -1;
                    } else if (Integer.parseInt(lhs.getPrice()) == Integer.parseInt(rhs.getPrice())) {
                        return 0;
                    }
                    return 0;
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                return 0;
            }
        });
        recyclerView.setAdapter(new CategoryAdapter(offers, CategoryOffersF.this.getActivity()));
        ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
    }

    private void sortByName() {
        Collections.sort(offers, new Comparator<OOffer>() {
            @Override
            public int compare(OOffer lhs, OOffer rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        recyclerView.setAdapter(new CategoryAdapter(offers, CategoryOffersF.this.getActivity()));
    }
}
