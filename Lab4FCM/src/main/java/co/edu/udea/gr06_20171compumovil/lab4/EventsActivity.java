package co.edu.udea.gr06_20171compumovil.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import co.edu.udea.gr06_20171compumovil.lab4.fragments.EventDetailFragment;
import co.edu.udea.gr06_20171compumovil.lab4.fragments.EventsFragment;
import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

public class EventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventsFragment.OnListFragmentInteractionListener, EventDetailFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private TextView nameUser, email;
    private FloatingActionButton fab;

    private Context context;
    String nombre;
    SharedPreferences.Editor editor;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String frag = intent.getStringExtra("fragment");
        if (frag != null) {
            if (frag.equals("1")) {
                Fragment fragment = null;
                fragment = new EventsFragment();
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack("fragment");
                    ft.commit();
                }
            }
        }

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor=preferences.edit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        nameUser = (TextView)header.findViewById(R.id.tvNameUser);
        email = (TextView)header.findViewById(R.id.tvEmail);

        Bundle bundle = intent.getExtras();

        if (bundle!=null){
            nombre= bundle.getString("usuario");
            String correo= bundle.getString("email");

            editor.putString("pref_username",nombre);
            editor.putString("pref_email",correo);
            editor.apply();

            nameUser.setText(nombre);
            email.setText(correo);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(EventsActivity.this, AddEventActivity.class);
                //in.putExtra("usuario",nombre);
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


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

   /* private void activateSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, EventsActivity.class)));
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //activateSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_search) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_events:
//                fab.setVisibility(View.VISIBLE);

                fragment = new EventsFragment();
                break;
            /*case R.id.nav_profile:
                fab.setVisibility(View.INVISIBLE);
                fragment = new ProfileFragment();
                break;
            case R.id.nav_sesion:
                editor.putBoolean(getString(R.string.userlogged),false);
                editor.apply();
                finish();
                break;*/
            case R.id.nav_logout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack("fragment");
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onListFragmentInteraction(Event item) {
        Log.d("EVENT", item.getName());
        Fragment fragment = new EventDetailFragment(item);
        Bundle args = new Bundle();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack("fragment");
        ft.commit();
//        Toast.makeText(getApplicationContext(), "You will make it ;)", Toast.LENGTH_SHORT);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
