package com.ek.posbridge.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ek.posbridge.UI.OrdersFragment;
import com.ek.posbridge.UI.ProductsFragment;
import com.ek.posbridge.UI.SummaryFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SummaryFragment tab1 = new SummaryFragment();
                return tab1;
            case 1:
                OrdersFragment tab2 = new OrdersFragment();
                return tab2;
            case 2:
                ProductsFragment tab3 = new ProductsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}