package com.akimi808.quotescollector;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.akimi808.quotescollector.fragments.AuthorFragment;
import com.akimi808.quotescollector.fragments.QuoteListFragment;
import com.akimi808.quotescollector.fragments.SourceFragment;

public class QuotesCollectorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_QUOTES = "quotes";
    public static final String TAG_AUTHORS = "authors";
    public static final String TAG_SOURCES = "sources";

    private String[] activityTitles;
    private int navItemIndex = 0;
    private NavigationView navigationView;
    private String currentTag;
    private Handler handler = new Handler(); // запускает переданный runnable в фоновом трэде

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_collector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        activityTitles = getResources().getStringArray(R.array.activity_titles);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            currentTag = TAG_QUOTES; //значение по умолчанию для текущего тэга
            loadDemandedFragment();
        }

    }

    private void loadDemandedFragment() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);

        if (getSupportFragmentManager().findFragmentByTag(currentTag) != null) {
            return;
        }

        //каждый раз оздается новый экземпляр анонимного класса
        Runnable pendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = qetDemandedFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out); //анимация смены фрагментов
                //в указанном индентификатором месте в активити, удалить существующий фрагмент,
                // а на его место поместит тот, который передан, и назначит ему тэг)
                fragmentTransaction.replace(R.id.fragment_frame, fragment, currentTag);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        handler.post(pendingRunnable);
    }

    private Fragment qetDemandedFragment() {
        if (currentTag.equals(TAG_QUOTES)) {
            return new QuoteListFragment();
        } else if (currentTag.equals(TAG_AUTHORS)) {
            return new AuthorFragment();
        } else if (currentTag.equals(TAG_SOURCES)) {
            return new SourceFragment();
        }
        throw new RuntimeException();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_quotes) {
            navItemIndex = 0;
            currentTag = TAG_QUOTES;
        } else if (id == R.id.nav_authors) {
            navItemIndex = 1;
            currentTag = TAG_AUTHORS;
        } else if (id == R.id.nav_sources) {
            navItemIndex = 2;
            currentTag = TAG_SOURCES;
        }

        loadDemandedFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
