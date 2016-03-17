package com.linzon.ru.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.linzon.ru.Activity.fragments.History_Diagramm;
import com.linzon.ru.Activity.fragments.History_Row;
import com.linzon.ru.Activity.models.PostModel.HistoryModel;
import com.linzon.ru.Activity.models.SearchModel;
import com.linzon.ru.R;

import java.util.ArrayList;

/**
 * Created by erick on 22.10.15.
 */
public class History extends AppCompatActivity {
    Intent intent;
    Toolbar toolbar;
    SearchModel searchModel;

    Fragment selectedFragment;
    History_Row history_row;
    History_Diagramm history_diagramm;

    private static final String HISTORY_ROW = "history_row";
    private static final String HISTORY_DIAGRAMM = "history_diagramm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        intent = getIntent();

        searchModel = (SearchModel) intent.getSerializableExtra("user");

        setToolbar();
        showRow();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.history_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle(searchModel.getFirst_name() + " " + searchModel.getLast_name());
        }
    }

    private void showRow() {
        if (!(selectedFragment instanceof History_Row)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == history_row) {
                history_row = new History_Row();
                history_row.setIntent(searchModel);
                fragmentTransaction.add(R.id.historyPage, history_row, HISTORY_ROW);
            }
            fragmentTransaction.show(history_row);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = history_row;
        }
    }

    private void showDiagramm() {
        if (!(selectedFragment instanceof History_Diagramm)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            hideFragments();
            if (null == history_diagramm) {
                history_diagramm = new History_Diagramm();
                history_diagramm.setIntent(searchModel);
                fragmentTransaction.add(R.id.historyPage, history_diagramm, HISTORY_DIAGRAMM);
            }
            fragmentTransaction.show(history_diagramm);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
            selectedFragment = history_diagramm;
        }
    }

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        history_row = (History_Row) getFragmentManager().findFragmentByTag(HISTORY_ROW);
        if (null != history_row) {
            fragmentTransaction.hide(history_row);
        }

        history_diagramm = (History_Diagramm) getFragmentManager().findFragmentByTag(HISTORY_DIAGRAMM);
        if (null != history_diagramm) {
            fragmentTransaction.hide(history_diagramm);
        }

        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.historyRaws: {
                showRow();
                break;
            }
            case R.id.historyDiagramm: {
                showDiagramm();
                break;
            }
            case android.R.id.home: {
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    public ArrayList<HistoryModel> currentUserHistory;

    public void setHistoryForDiagramm(ArrayList<HistoryModel> model) {
        currentUserHistory = model;

        if(history_diagramm != null)
            history_diagramm.setMainHistory(model);
    }
}
