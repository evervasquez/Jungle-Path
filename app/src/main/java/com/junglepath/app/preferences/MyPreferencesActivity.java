/*
 *
 *  *
 *  *  * Copyright (C) 2016 eveR Vásquez.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.junglepath.app.preferences;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.junglepath.app.main.ui.MainActivity;
import com.pavelsikun.seekbarpreference.SeekBarPreference;
import com.junglepath.app.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyPreferencesActivity extends AppCompatActivity {
    private static final String TAG = MyPreferencesActivity.class.getSimpleName();
    public static SeekBarPreference preference;
    public static int DISTANCE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesScreen())
                .commit();

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle("Configurar");
        }
        getDataForIntent();
    }

    private void getDataForIntent(){
        Intent intent = getIntent();
        DISTANCE = intent.getIntExtra(MainActivity.PREFERENCES_DISTANCE, -1);
    }

    public static class PreferencesScreen extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            MyPreferencesActivity.preference = new SeekBarPreference(getActivity());
            preference.setTitle("Distancia de busqueda");
            preference.setSummary("Configura la distancia para buscar tu Farmacia");
            preference.setMinValue(400);
            preference.setMaxValue(10000);
            preference.setCurrentValue(MyPreferencesActivity.DISTANCE);
            preference.setInterval(200);
            preference.setMeasurementUnit("metros");
            preference.setDialogEnabled(false);
            getPreferenceScreen().addPreference(preference);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", preference.getCurrentValue());
        setResult(MainActivity.RESULT_OK, returnIntent);
        super.onBackPressed();

    }
}
