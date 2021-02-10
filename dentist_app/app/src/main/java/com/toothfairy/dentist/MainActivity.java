package com.toothfairy.dentist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.ui.about.AboutFragment;
import com.toothfairy.dentist.ui.doctor.DoctorFragment;
import com.toothfairy.dentist.ui.intro.IntroFragment;
import com.toothfairy.dentist.ui.map.MapFragment;
import com.toothfairy.dentist.ui.subject.SubjectFragment;
import net.daum.android.map.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity {

        //FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHashKey();
        //mFirebaseDatabase.setPersistenceEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼
        }
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        NavigationView navigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(drawer.isDrawerOpen(Gravity.RIGHT)))
                    drawer.openDrawer(Gravity.RIGHT);
                //else
                 //   drawer.openDrawer(Gravity.RIGHT);
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_subject, R.id.nav_doctor, R.id.nav_map)
                .setDrawerLayout(drawer)
                .build();*/
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);

        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                //FragmentManager fm = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.nav_about:
                        fragment = AboutFragment.newInstance();
                        break;
                    case R.id.nav_subject:
                        fragment = SubjectFragment.newInstance();
                        break;
                    case R.id.nav_doctor:
                        fragment = DoctorFragment.newInstance();
                        break;
                    case R.id.nav_map:
                        fragment = MapFragment.newInstance();
                }
                if (fragment != null) {
                    replaceFragment(fragment);
                }
                drawer.closeDrawer(Gravity.END);
                // 햄버거 버튼-> 뒤로가기 버튼으로 바뀌고 그 버튼을 누르면 메인화면으로 돌아간다.
                return true;
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //bottomnavigation
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call:
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01036161879"));
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            startActivity(intent);
                        } else {
                            requestPermissions(new String[]{CALL_PHONE}, 1);
                        }
                    case R.id.receipt:
                        //fragment = new();
                        break;
                }
                return true;

            }
        });

    }

    public void onBackPressed(){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else if (!(getSupportFragmentManager().findFragmentById(R.id.nav_intro) instanceof IntroFragment))
            replaceFragment(IntroFragment.newInstance());
        else
            finish();
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment,fragment).commit();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        if(packageInfo == null)
            Log.e("HashKey", "Hashkey:null");

        for (Signature signature : packageInfo.signatures){
            try{
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("HashKey","HashKey Error. signature=" + signature, e);
            }
        }
    }

}