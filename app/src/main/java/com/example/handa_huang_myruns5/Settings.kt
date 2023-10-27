package com.example.handa_huang_myruns5

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

//inflate preference screen
class Settings : PreferenceFragmentCompat(){



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {


        val prefManager:PreferenceManager= preferenceManager

        prefManager.sharedPreferencesName= MainActivity.prefKey

        setPreferencesFromResource(R.xml.settings,rootKey)
    }


}