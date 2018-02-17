package com.example.dell.fedcash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import com.example.dell.fedcash.Fragment1.ListSelectionListener;

public class ApiCalls extends FragmentActivity implements ListSelectionListener{

    public static ArrayList<String> a = new ArrayList<String>();
    public static ArrayList<String> b = new ArrayList<String>();
    private Fragment2 mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and qutoes
        a = getIntent().getStringArrayListExtra("Data1");
        b = getIntent().getStringArrayListExtra("Data2");
        setContentView(R.layout.activity_api_calls);
        mDetailsFragment = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.viewer);

    }

    @Override
    public void onListSelection(int index) {
        if (mDetailsFragment.getShownIndex() != index) {
            mDetailsFragment.showQuoteAtIndex(index);
        }
    }


}
