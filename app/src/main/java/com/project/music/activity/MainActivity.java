package com.project.music.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.project.music.R;
import com.project.music.adapter.AllSongsAdapter;
import com.project.music.fragment.AboutUsFragment;
import com.project.music.fragment.FavoriteFragment;
import com.project.music.fragment.HomeFragment;
import com.project.music.fragment.SettingFragment;
import com.project.music.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frame;
    NavigationView navigationView;
    MenuItem prevMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerLayout);
        coordinatorLayout=findViewById(R.id.coordinateLayout);
        toolbar=findViewById(R.id.toolbar);
        frame=findViewById(R.id.Frame);
        navigationView=findViewById(R.id.navigationView);

        setUpActionBar();
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer);
        actionBarDrawerToggle.syncState();
        displayHome();

    }
    void setUpActionBar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("toolbar");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        navigationView.setNavigationItemSelectedListener(l->{
            int it=l.getItemId();
            if(prevMenu!=null){
                prevMenu.setChecked(false);
            }
            l.setCheckable(true);
            l.setChecked(true);
            prevMenu=l;
            switch (it){
                case R.id.all_songs:
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame, new HomeFragment())
                            .addToBackStack("all songs")
                            .commit();
                    getSupportActionBar().setTitle("All Songs");
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.favourites:
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame, new FavoriteFragment())
                            .addToBackStack("favorite")
                            .commit();
                    getSupportActionBar().setTitle("Favorites");
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.about:
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame, new AboutUsFragment())
                            .addToBackStack("about")
                            .commit();
                    getSupportActionBar().setTitle("About Us");
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame, new SettingFragment())
                            .addToBackStack("settings")
                            .commit();
                    getSupportActionBar().setTitle("Settings");
                    drawerLayout.closeDrawers();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });
        return super.onOptionsItemSelected(item);
    }
     void displayHome(){
        HomeFragment homeFragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,homeFragment).commit();
        getSupportActionBar().setTitle("All Songs");
        navigationView.setCheckedItem(R.id.all_songs);
     }




}