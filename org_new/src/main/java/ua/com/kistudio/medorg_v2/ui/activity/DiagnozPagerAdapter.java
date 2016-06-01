package ua.com.kistudio.medorg_v2.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.com.kistudio.medorg_v2.ui.fragments.DiagnozQuestionsFirstFragment;
import ua.com.kistudio.medorg_v2.ui.fragments.DiagnozTitleFragment;

/**
 * Created by Вiталя on 18.03.2016.
 */
public class DiagnozPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 4;

    public DiagnozPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos",position);
        switch (position){
            case 0:
                return new DiagnozTitleFragment();
            case 1:
            case 2:
            case 3:
                DiagnozQuestionsFirstFragment diagnozQuestionsFirstFragment = new DiagnozQuestionsFirstFragment();
                diagnozQuestionsFirstFragment.setArguments(bundle);
                return diagnozQuestionsFirstFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
