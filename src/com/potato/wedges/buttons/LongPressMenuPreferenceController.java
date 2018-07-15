/*
 * Copyright (C) 2017 CypherOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.potato.wedges.buttons;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import static android.provider.Settings.System.KEY_MENU_LONG_PRESS_ACTION;

public class LongPressMenuPreferenceController extends AbstractPreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "LongPressMenuPref";

    private final String mLongPressMenuKey;

    private ListPreference mLongPressMenu;

    public LongPressMenuPreferenceController(Context context, String key) {
        super(context);
        mLongPressMenuKey = key;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return mLongPressMenuKey;
    }

    @Override
    public void updateState(Preference preference) {
        final ListPreference mLongPressMenu = (ListPreference) preference;
        final Resources res = mContext.getResources();
        if (mLongPressMenu != null) {
            int defaultLongPressOnMenuKeyBehavior = res.getInteger(
                    com.android.internal.R.integer.config_longPressOnMenuKeyBehavior);
            int longPressOnMenuKeyBehavior = Settings.System.getIntForUser(mContext.getContentResolver(),
                    Settings.System.KEY_MENU_LONG_PRESS_ACTION,
                    defaultLongPressOnMenuKeyBehavior,
                    UserHandle.USER_CURRENT);
            String menuKey = String.valueOf(longPressOnMenuKeyBehavior);
            mLongPressMenu.setValue(menuKey);
            updateLongPressMenuSummary(mLongPressMenu, menuKey);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        try {
            String menuKey = (String) newValue;
            Settings.System.putIntForUser(mContext.getContentResolver(), Settings.System.KEY_MENU_LONG_PRESS_ACTION,
                    Integer.parseInt(menuKey), UserHandle.USER_CURRENT);
            updateLongPressMenuSummary((ListPreference) preference, menuKey);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Could not persist screenshot mode setting", e);
        }
        return true;
    }

    private void updateLongPressMenuSummary(Preference mLongPressMenu, String menuKey) {
        if (menuKey != null) {
            String[] values = mContext.getResources().getStringArray(R.array
                    .action_values);
            final int summaryArrayResId = R.array.action_entries;
            String[] summaries = mContext.getResources().getStringArray(summaryArrayResId);
            for (int i = 0; i < values.length; i++) {
                if (menuKey.equals(values[i])) {
                    if (i < summaries.length) {
                        mLongPressMenu.setSummary(summaries[i]);
                        return;
                    }
                }
            }
        }

        mLongPressMenu.setSummary("");
        Log.e(TAG, "Invalid long press value: " + menuKey);
    }
}
