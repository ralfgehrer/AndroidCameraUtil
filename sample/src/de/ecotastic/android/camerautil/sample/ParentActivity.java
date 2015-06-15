package de.ecotastic.android.camerautil.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Example activity that holds the CameraIntentFragment and forwards onActivityResult events to all
 * child fragments.
 *  
 * @author Ralf Gehrer <ralf@ecotastic.de>
 */
public class ParentActivity extends FragmentActivity {
    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new CameraIntentFragment();
            ft.add(android.R.id.content,fragment,FRAGMENT_TAG);
            ft.commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}