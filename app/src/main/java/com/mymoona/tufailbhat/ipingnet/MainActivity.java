package com.mymoona.tufailbhat.ipingnet;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager;
        TabLayout tabLayout;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void closeKeyboard(View vv){
      try{  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(vv.getWindowToken(),InputMethodManager.RESULT_SHOWN);}
      catch(Exception ee){
          ee.getMessage();
      }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "IPV4");
        adapter.addFragment(new TwoFragment(), "IPV6");
        adapter.addFragment(new ThreeFragment(), "Net Tool");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private boolean MyStarActivity(Intent aIntent){
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch(ActivityNotFoundException ee)
        {
            return  false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.rateus:
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.mymoona.tufailbhat.ipingnet"));
                if(!MyStarActivity(intent)){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mymoona.tufailbhat.ipingnet"));
                    if(!MyStarActivity(intent)){
                        Toast.makeText(this, "Could not open google play,please install Google play", Toast.LENGTH_SHORT).show();
                     }
                }
                return true;
            case R.id.share:
                try{
                    Intent i=new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"IPingNet");
                    String aux="\n IPV4/IPV6/Ping/Routing Table/Phone Info and many more amazing Network Utilities in just one App.\n\n";
                    aux=aux+"https://play.google.com/store/apps/details?id=com.mymoona.tufailbhat.ipingnet  \n\n";
                    i.putExtra(Intent.EXTRA_TEXT,aux);
                    startActivity(Intent.createChooser(i,"Choose one"));
                }catch(Exception ee){

                    Toast.makeText(this, "Error: while sharing"+ ee.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.exit:
                if (getIntent().getBooleanExtra("EXIT", false))
                    finish();
                System.exit(0);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }
}
