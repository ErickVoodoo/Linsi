package com.linzon.ru;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_basket);

        mainPage = (ViewPager) findViewById(R.id.basketPager);
        setToolbar();
        setProgressBar();
        setTabLayout();
        setupTabIcons();
    }

    private void setupTabIcons() {
        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_shopping_cart_white_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_business_center_white_24dp);
        } catch(NullPointerException np) {
            Log.e("ERROR", np.getMessage());
        }
    }

    private void setTabLayout() {
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
}
