package com.tbi_id;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

	protected static final String[] CONTENT = new String[] { "STEP 1", "STEP 2", "STEP 3", };
	
	public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
	
	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            // Top Rated fragment activity
            return new StepOneFragment();
        case 1:
            // Games fragment activity
            return new StepTwoFragment();
        case 2:
            // Movies fragment activity
            return new StepThreeFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
        return 3;
	}

	@Override
	public int getIconResId(int index) {
		return 0;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
      return TabsPagerAdapter.CONTENT[position % CONTENT.length];
    }

}
