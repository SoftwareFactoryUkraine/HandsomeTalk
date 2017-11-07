package kr.sofac.handsometalk.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kr.sofac.handsometalk.R;
import kr.sofac.handsometalk.view.fragments.CalendarFragment;
import kr.sofac.handsometalk.view.fragments.ContactsFragment;
import kr.sofac.handsometalk.view.fragments.EventFragment;
import kr.sofac.handsometalk.view.fragments.InfoFragment;
import kr.sofac.handsometalk.view.fragments.PushFragment;
import kr.sofac.handsometalk.view.fragments.SettingsFragment;
import kr.sofac.handsometalk.view.fragments.TalkFragment;

import static kr.sofac.handsometalk.Constants.CALENDAR_FRAGMENT;
import static kr.sofac.handsometalk.Constants.CONTACTS_FRAGMENT;
import static kr.sofac.handsometalk.Constants.EVENT_FRAGMENT;
import static kr.sofac.handsometalk.Constants.INFO_FRAGMENT;
import static kr.sofac.handsometalk.Constants.PUSH_FRAGMENT;
import static kr.sofac.handsometalk.Constants.SETTINGS_FRAGMENT;
import static kr.sofac.handsometalk.Constants.TALK_FRAGMENT;
import static kr.sofac.handsometalk.Constants.TYPE_CONTENT_NAVIGATION;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CalendarFragment calendarFragment;
    ContactsFragment contactsFragment;
    EventFragment eventFragment;
    InfoFragment infoFragment;
    PushFragment pushFragment;
    SettingsFragment settingsFragment;
    TalkFragment talkFragment;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        contactsFragment = new ContactsFragment();
        calendarFragment = new CalendarFragment();
        eventFragment = new EventFragment();
        infoFragment = new InfoFragment();
        pushFragment = new PushFragment();
        settingsFragment = new SettingsFragment();
        talkFragment = new TalkFragment();

        fTrans = getFragmentManager().beginTransaction();
        switch (getIntent().getStringExtra(TYPE_CONTENT_NAVIGATION)){
            case CALENDAR_FRAGMENT:
                setTitle(getString(R.string.make_an_appointment));
                fTrans.add(R.id.id_main_frame_layout, calendarFragment);
                break;
            case CONTACTS_FRAGMENT:
                setTitle(getString(R.string.contacts));
                fTrans.add(R.id.id_main_frame_layout, contactsFragment);
                break;
            case INFO_FRAGMENT:
                setTitle(getString(R.string.about_us));
                fTrans.add(R.id.id_main_frame_layout, infoFragment);
                break;
            case EVENT_FRAGMENT:
                setTitle(getString(R.string.events));
                fTrans.add(R.id.id_main_frame_layout, eventFragment);
                break;
            case PUSH_FRAGMENT:
                setTitle(getString(R.string.notifications));
                fTrans.add(R.id.id_main_frame_layout, pushFragment);
                break;
            case TALK_FRAGMENT:
                setTitle(getString(R.string.help_me));
                fTrans.add(R.id.id_main_frame_layout, talkFragment);
                break;
            case SETTINGS_FRAGMENT:
                setTitle(getString(R.string.settings));
                fTrans.add(R.id.id_main_frame_layout, settingsFragment);
                break;
        }
        fTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        View header = navigationView.getHeaderView(0);
        if(checkAuthorization()){
            header.findViewById(R.id.id_sign_in_background).setVisibility(View.GONE);
        } else {
            header.findViewById(R.id.id_sign_in_button_tv).setOnClickListener(this);
        }


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
        getMenuInflater().inflate(R.menu.navigation, menu);
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
        fTrans = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.id_info:
                setTitle(getString(R.string.about_us));
                fTrans.replace(R.id.id_main_frame_layout, infoFragment);
                break;
            case R.id.id_talk:
                setTitle(getString(R.string.help_me));
                fTrans.replace(R.id.id_main_frame_layout, talkFragment);
                break;
            case R.id.id_calendar:
                setTitle(getString(R.string.make_an_appointment));
                fTrans.replace(R.id.id_main_frame_layout, calendarFragment);
                break;
            case R.id.id_event:
                setTitle(getString(R.string.events));
                fTrans.replace(R.id.id_main_frame_layout, eventFragment);
                break;
            case R.id.id_push:
                setTitle(getString(R.string.notifications));
                fTrans.replace(R.id.id_main_frame_layout, pushFragment);
                break;
            case R.id.id_contacts:
                setTitle(getString(R.string.contacts));
                fTrans.replace(R.id.id_main_frame_layout, contactsFragment);
                break;
            case R.id.id_settings:
                setTitle(getString(R.string.settings));
                fTrans.replace(R.id.id_main_frame_layout, settingsFragment);
                break;
        }
        fTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_sign_in_button_tv:
                startActivity(new Intent(this, AuthorizationActivity.class));
                finishAffinity();
                break;
        }
    }

    public boolean checkAuthorization(){
        return false;
    }
}