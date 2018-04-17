package com.potato.wedges;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import com.potato.wedges.navigation.BottomNavigationViewCustom;
import com.potato.wedges.fragments.WedgesFragment;
import com.potato.wedges.fragments.UpdatesFragment;
import com.potato.wedges.fragments.SocialFragment;
import com.potato.wedges.fragments.AboutFragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import java.util.prefs.PreferencesFactory;
public class PotatoWedges extends SettingsPreferenceFragment {
    private ActionBar toolbar;
    private BottomNavigationViewCustom navigation;

    ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
    MenuItem menuitem;

    private BottomNavigationViewCustom.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewCustom.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wedges:
                    setAppColors(R.color.wedges_icon);
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_updates:
                    setAppColors(R.color.updates_icon);
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_social:
                    setAppColors(R.color.social_icon);
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_about:
                    setAppColors(R.color.about_icon);
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        navigation = view.findViewById(R.id.navigation);
        mViewPager = view.findViewById(R.id.viewpager);
        mPagerAdapter = new PagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	@Override
        	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        	}

        	@Override
        	public void onPageSelected(int position) {
        	    if(menuitem != null) {
        	        menuitem.setChecked(false);
        	    } else {
        	        navigation.getMenu().getItem(0).setChecked(false);
        	    }
        	    navigation.getMenu().getItem(position).setChecked(true);
                switch (position)
                {
                        case 0: setAppColors(R.color.wedges_icon);
                                break;
                        case 1: setAppColors(R.color.updates_icon);
                                break;
                        case 2: setAppColors(R.color.social_icon);
                                break;
                        case 3: setAppColors(R.color.about_icon);
                                break;

                }
        	    menuitem = navigation.getMenu().getItem(position);
        	}

        	@Override
        	public void onPageScrollStateChanged(int state) {
        	}
        });
        setHasOptionsMenu(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return view;
    }

    class PagerAdapter extends FragmentPagerAdapter {

        String titles[] = getTitles();
        private Fragment frags[] = new Fragment[titles.length];

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            frags[0] = new WedgesFragment();
            frags[1] = new UpdatesFragment();
            frags[2] = new SocialFragment();
            frags[3] = new AboutFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private String[] getTitles() {
        String titleString[];
        titleString = new String[]{
                getString(R.string.title_wedges),
                getString(R.string.title_updates),
                getString(R.string.title_social),
                getString(R.string.title_about)};
        return titleString;
    }

    private void setAppColors(int iconColorResource) {
        navigation.setItemIconTintList(getResources().getColorStateList(iconColorResource,
                super.getActivity().getApplicationContext().getTheme()));
        navigation.setItemTextColor(getResources().getColorStateList(iconColorResource,
                super.getActivity().getApplicationContext().getTheme()));
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.POTATO_WEDGES;
    }
}
