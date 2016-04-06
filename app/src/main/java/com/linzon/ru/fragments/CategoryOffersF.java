package com.linzon.ru.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.linzon.ru.App;
import com.linzon.ru.MainActivity;
import com.linzon.ru.R;
import com.linzon.ru.adapters.CategoryAdapter;
import com.linzon.ru.adapters.PopularAdapter;
import com.linzon.ru.common.Constants;
import com.linzon.ru.common.RViewScroll;
import com.linzon.ru.database.DBAsync;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.POffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CategoryOffersF extends Fragment {
    RecyclerView recyclerView;
    View view;
    Spinner categorySortSpinner;
    public int selectedCategory = -1;
    private ArrayList<OOffer> offers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setRecycler();
        setFab();
        setSpinner();
        return view;
    }

    private void setSpinner() {
        categorySortSpinner = (Spinner) view.findViewById(R.id.categorySortSpinner);
        ArrayAdapter<String> adapterBC = new ArrayAdapter<String>(CategoryOffersF.this.getActivity(), R.layout.spinner_item, Constants.SortArray);
        adapterBC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySortSpinner.setAdapter(adapterBC);
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
                            sortByPriceUp();
                            break;
                        }
                        case 2: {
                            sortByPriceDown();
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
            loadCategoryItems();
        }
    }

    public void loadCategoryItems() {
        if(this.selectedCategory == 0) {
            categorySortSpinner.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            ArrayList<POffer> popularOffers = ((App) this.getActivity().getApplication()).getPriceOffers();
            recyclerView.setAdapter(new PopularAdapter(popularOffers, this.getActivity()));
            ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
        } else {
            categorySortSpinner.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
                    ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();
                    /*recyclerView.setAdapter(new CategoryAdapter(offers, CategoryOffersF.this.getActivity()));
                    ((MainActivity) CategoryOffersF.this.getActivity()).hideProgressBar();*/
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
