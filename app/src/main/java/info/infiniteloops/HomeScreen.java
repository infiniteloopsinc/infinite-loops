package info.infiniteloops;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import info.infiniteloops.ICardView.ICardView;
import info.infiniteloops.iviewpager.Material_View_pager;
import info.infiniteloops.login.Login_Activity;
import info.infiniteloops.pkg_list_view.Custom_Listview;
import info.infiniteloops.pkg_list_view.Simple_Listview;
import info.infiniteloops.pkg_recycler_view.Custom_RecycelerView;
import info.infiniteloops.pkg_recycler_view.Simple_recycler_view;
import info.infiniteloops.pkg_recycler_view.ThreeRV;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home_screen, menu);
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

        if (id == R.id.simple_list) {
            startActivity(new Intent(HomeScreen.this,Simple_Listview.class));
            // Handle the camera action
        } else if (id == R.id.custom_list) {
            startActivity(new Intent(HomeScreen.this,Custom_Listview.class));

        }
        if (id == R.id.simple_rc) {
            startActivity(new Intent(HomeScreen.this,Simple_recycler_view.class));
            // Handle the camera action
        } else if (id == R.id.custom_rc) {
            startActivity(new Intent(HomeScreen.this,Custom_RecycelerView.class));

        }
        else if (id == R.id.SocialLogin) {
            startActivity(new Intent(HomeScreen.this,Login_Activity.class));

        }

        else if (id == R.id.cardview) {
            startActivity(new Intent(HomeScreen.this,ICardView.class));

        }

        else if (id == R.id.three_rv) {
            startActivity(new Intent(HomeScreen.this,ThreeRV.class));

        }

        else if (id == R.id.viewpager) {
            startActivity(new Intent(HomeScreen.this,Material_View_pager.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
