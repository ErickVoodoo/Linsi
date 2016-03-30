package com.linzon.ru;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.linzon.ru.adapters.ViewPagerAdapter;
import com.linzon.ru.fragments.ArchiveF;
import com.linzon.ru.fragments.BasketF;

/**
 * Created by Admin on 26.03.2016.
 */
public class Basket extends AppCompatActivity {
    Toolbar basketToolbar;
    ProgressBar basketProgressBar;
    TabLayout tabLayout;
    ViewPager mainPage;

    private final String BASKET_TAG = "BASKET_TAG";
    private final String ARCHIVE_TAG = "ARCHIVE_TAG";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_basket);

        mainPage = (ViewPager) findViewById(R.id.basketPager);

        setToolbar();
        setProgressBar();
        setTablayout();
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_shopping_cart_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_business_center_white_24dp);
    }

    private void setTablayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BasketF(), "КОРЗИНА");
        adapter.addFragment(new ArchiveF(), "АРХИВ");
        mainPage.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mainPage);
    }

    private void setToolbar() {
        basketToolbar = (Toolbar) findViewById(R.id.basketToolbar);
        setSupportActionBar(basketToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        basketToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket.this.finish();
            }
        });
    }

    private void setProgressBar() {
        basketProgressBar = (ProgressBar) findViewById(R.id.basketProgressBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        basketToolbar.setTitle("Покупки");
    }

    /*private void showBasket() {
        if(!(selectedragment instanceof BasketF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if(null != basketFragment) {
                basketFragment = new BasketF();
                fragmentTransaction.add(R.id.basketPager, basketFragment, BASKET_TAG);
            }
            fragmentTransaction.show(basketFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedragment = basketFragment;
        }
    }

    private void showArchive() {
        if(!(selectedragment instanceof ArchiveF)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if(null != archiveFragment) {
                archiveFragment = new ArchiveF();
                fragmentTransaction.add(R.id.basketPager, archiveFragment, ARCHIVE_TAG);
            }
            fragmentTransaction.show(archiveFragment);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedragment = archiveFragment;
        }
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        basketFragment = (BasketF) getFragmentManager().findFragmentByTag(BASKET_TAG);
        if(null != basketFragment) {
            fragmentTransaction.hide(basketFragment);
        }

        archiveFragment = (ArchiveF) getFragmentManager().findFragmentByTag(ARCHIVE_TAG);
        if(null != archiveFragment) {
            fragmentTransaction.hide(archiveFragment);
        }

        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basket, menu);
        return true;
    }*/
}
