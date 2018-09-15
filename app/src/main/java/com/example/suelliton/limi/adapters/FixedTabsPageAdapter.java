package com.example.suelliton.limi.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.suelliton.limi.fragments.FragmentConsumo;
import com.example.suelliton.limi.fragments.FragmentPeso;

public class FixedTabsPageAdapter extends FragmentPagerAdapter {
    public FixedTabsPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentConsumo();
            case 1:
                return new FragmentPeso();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Consumo";
            case 1:
                return "Peso";
            default:
                return null;
        }
    }

}
