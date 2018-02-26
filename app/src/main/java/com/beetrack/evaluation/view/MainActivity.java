package com.beetrack.evaluation.view;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.beetrack.evaluation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ArticlesFragment.OnArticlesFragmentInteractionListener {

    @BindView(R.id.coordinatorlayout) CoordinatorLayout coordinatorlayout;

    private String[] sections;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Create the adapter that will return a fragment for each of the three
        SectionsPagerAdapter mSectionsPagerAdapter  = new SectionsPagerAdapter(getSupportFragmentManager());
        sections                                    = getResources().getStringArray(R.array.sections);
        ViewPager mViewPager                        = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout                         = (TabLayout) findViewById(R.id.tabLayout);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Config and set up the TabLayout with the ViewPager.
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(coordinatorlayout, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0  : return ArticlesFragment.newInstance(false);
                case 1  : return ArticlesFragment.newInstance(true);
                default : return ArticlesFragment.newInstance(false);
            }
        }

        @Override
        public int getCount() {
            return sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sections[position];
        }
    }
}
