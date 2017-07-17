package com.example.tacademy.fbtest186.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.fragment.LikeBbsFragment;
import com.example.tacademy.fbtest186.fragment.MyBbsFragment;
import com.example.tacademy.fbtest186.fragment.TotalBbsFragment;

public class BBSActivity extends RootActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 글 작성 파트 이동
                startActivity(new Intent(BBSActivity.this, NewPostActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bb, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {               // 로그아웃
            singOut();
            finish();
            return true;
        }else if (id == R.id.action_settings1) {        // 회원정보수정
            return true;
        }else if (id == R.id.action_settings2) {        // 채팅
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    // 페이저에 공급하는 데이터(Fragment)를 관리하는 아답터
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new TotalBbsFragment();
                case 1:
                    return new LikeBbsFragment();
                default:
                    return new MyBbsFragment();
            }
            //return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "전체글보기";
                case 1:
                    return "좋아요순목록";
                case 2:
                    return "내가쓴글목록";
            }
            return null;
        }
    }
}
