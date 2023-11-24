package com.example.languagetranslator;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
