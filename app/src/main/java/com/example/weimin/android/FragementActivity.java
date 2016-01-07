package com.example.weimin.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FragementActivity extends Activity {
    FragementOne fragementOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement);
        if (fragementOne == null) {
            fragementOne = new FragementOne();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fm, fragementOne);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
