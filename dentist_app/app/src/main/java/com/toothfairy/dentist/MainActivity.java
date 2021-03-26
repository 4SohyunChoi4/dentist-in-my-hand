package com.toothfairy.dentist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.toothfairy.dentist.book.MyBookListFragment;
import com.toothfairy.dentist.ui.ReceiptFragment;
import com.toothfairy.dentist.ui.about.AboutFragment;
import com.toothfairy.dentist.ui.doctor.DoctorFragment;
import com.toothfairy.dentist.intro.IntroFragment;
import com.toothfairy.dentist.ui.map.MapFragment;
import com.toothfairy.dentist.ui.subject.SubjectFragment;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyFirebaseMsgService";
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    PatientID patient;
    Bundle bundle = new Bundle();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = this;

        //getHashKey();
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, android.R.style.Theme_DeviceDefault));
        builder.setMessage("로그인이 필요한 기능입니다.");
        builder.setPositiveButton("로그인하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                replaceFragment(LoginFragment.newInstance());
            }
        });

        final AlertDialog alertDialog = builder.create();

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
                if (!(drawer.isDrawerOpen(Gravity.RIGHT))) {
                    drawer.openDrawer(Gravity.RIGHT);
                    updateUI(user);
                }
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
                        break;
                    case R.id.receipt:
                        if (user == null) {
                            alertDialog.show();
                        } else
                            replaceFragment(ReceiptFragment.newInstance());
                        break;
                }
                return true;

            }
        });

    }
    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("token is : ", token);

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d("token is : ", msg);
                    }
                });
    }


    public void onBackPressed() {
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
        //fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment).commit();
        //fragment.onDestroy();
        //fragment.onDetach();
        //fragment = null;
    }

    public void updateUI(FirebaseUser user) {
        final Button loginBtn = findViewById(R.id.loginBtn);
        final Button joinBtn = findViewById(R.id.joinBtn);
        final Button myBookBtn = findViewById(R.id.myBookBtn);
        ImageView navImageView = findViewById(R.id.navImageView);
        final TextView navTextView = findViewById(R.id.navTextView);
        if (user != null) { // 로그인했을때
            navImageView.setVisibility(View.VISIBLE);
            loginBtn.setText("로그아웃");
            joinBtn.setVisibility(View.GONE);
            myBookBtn.setVisibility(View.VISIBLE);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.getReference("patient/" + user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    patient = snapshot.getValue(PatientID.class);
                    navTextView.setText(patient.getName() + getResources().getString(R.string.nav_header_title_login));
                    bundle.putString("name", patient.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            });
            myBookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(MyBookListFragment.newInstance());
                    onBackPressed();
                }
            });
        }
        else { //로그인 x
            navTextView.setText(getResources().getString(R.string.nav_header_title_no_login));
            navImageView.setVisibility(View.GONE);
            loginBtn.setText("로그인");
            joinBtn.setVisibility(View.VISIBLE);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(LoginFragment.newInstance());
                    onBackPressed();
                }
            });
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(JoinFragment.newInstance());
                    onBackPressed();
                }
            });

        }

    }
    /*
    private Fragment getFragment(int position){
        return savedInstanceState == null ? adapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.pager + ":" + position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tabsCount",      adapter.getCount());
        outState.putStringArray("titles", adapter.getTitles().toArray(new String[0]));
    }

     */
}
 /*   @Override
    public void onAttach(Context context) {
        super.onAttach(activity);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

  */
/*
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
    */
