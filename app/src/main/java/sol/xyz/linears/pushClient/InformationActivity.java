package sol.xyz.linears.pushClient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import sol.xyz.linears.pushClient.fragment.InfoFragment;

public class InformationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 1;
        private String tabTitles[] = new String[] { "list" };
        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public Fragment getItem(int position) {
            return InfoFragment.newInstance(position);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
