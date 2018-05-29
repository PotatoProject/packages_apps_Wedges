package com.potato.wedges;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.transition.Fade;
import com.potato.wedges.navigation.BottomNavigationViewCustom;
import com.potato.wedges.fragments.WedgesFragment;
import com.potato.wedges.fragments.UpdatesDummyFragment;
import com.potato.wedges.fragments.SocialFragment;
import com.potato.wedges.fragments.AboutFragment;
import android.view.MenuItem;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import java.util.prefs.PreferencesFactory;
public class PotatoWedges extends SettingsPreferenceFragment {

    private BottomNavigationViewCustom navigation;
    private BottomNavigationViewCustom.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewCustom.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() != navigation.getSelectedItemId()) {
                switch (item.getItemId()) {
                    case R.id.navigation_wedges:
                        setAppColors(R.color.wedges_icon);
                        setFragment(new WedgesFragment());
                        return true;
                    case R.id.navigation_updates:
                        setAppColors(R.color.updates_icon);
                        setFragment(new UpdatesDummyFragment());
                        return true;
                    case R.id.navigation_social:
                        setAppColors(R.color.social_icon);
                        setFragment(new SocialFragment());
                        return true;
                    case R.id.navigation_about:
                        setAppColors(R.color.about_icon);
                        setFragment(new AboutFragment());
                        return true;
                }
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(new WedgesFragment());
        return view;
    }

    private void setAppColors(int iconColorResource) {
        navigation.setItemIconTintList(getResources().getColorStateList(iconColorResource,
                super.getActivity().getApplicationContext().getTheme()));
        navigation.setItemTextColor(getResources().getColorStateList(iconColorResource,
                super.getActivity().getApplicationContext().getTheme()));
    }

    private void setFragment(Fragment newFragment) {
        newFragment.setEnterTransition(new Fade(1));
        newFragment.setExitTransition(new Fade(2));
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, newFragment).commit();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.POTATO_WEDGES;
    }
}
