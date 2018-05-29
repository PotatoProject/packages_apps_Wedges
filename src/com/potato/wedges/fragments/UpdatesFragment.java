/*
 * Copyright (C) 2018 The Potato Open Sauce Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.potato.wedges.fragments;

import android.os.Bundle;
import android.os.Build;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.applications.LayoutPreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;
import com.potato.wedges.views.CircleProgressBar;

public class UpdatesFragment extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    static final String KEY_UPDATES_HEADER = "updates_header";
    private LayoutPreference mUpdatesLayoutPref;
    TextView mDeviceName;
    TextView mSummary1;
    TextView mSummary2;
    CircleProgressBar updateProgress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.updates_main);
        mUpdatesLayoutPref = (LayoutPreference) findPreference(KEY_UPDATES_HEADER);
        mDeviceName = mUpdatesLayoutPref.findViewById(R.id.device_name);
        mSummary1 = mUpdatesLayoutPref.findViewById(R.id.text1);
        mSummary2 = mUpdatesLayoutPref.findViewById(R.id.text2);
        updateProgress = mUpdatesLayoutPref.findViewById(R.id.update_progress);
        updateProgress.setColor(getResources().getColor(R.color.colorUpdates, getContext().getTheme()));
        updateProgress.setProgressWithAnimation(100);
        mDeviceName.setText(Build.DEVICE);
        mSummary1.setText(Build.MANUFACTURER);
        mSummary2.setText("");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        return false;
    }


    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.POTATO_WEDGES;
    }
}
