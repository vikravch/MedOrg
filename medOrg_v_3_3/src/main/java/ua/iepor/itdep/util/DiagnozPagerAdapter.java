package ua.iepor.itdep.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.iepor.itdep.ui.DiagnozQuestionsFirstFragment;
import ua.iepor.itdep.ui.DiagnozTitleFragment;

/**
 * Created by Вiталя on 18.03.2016.
 */
public class DiagnozPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 3;

    public DiagnozPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DiagnozTitleFragment();
            case 1:
                return new DiagnozQuestionsFirstFragment();
            case 2:
                return new DiagnozQuestionsFirstFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
