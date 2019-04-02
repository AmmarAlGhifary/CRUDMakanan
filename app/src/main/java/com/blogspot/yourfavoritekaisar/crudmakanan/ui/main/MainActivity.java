package com.blogspot.yourfavoritekaisar.crudmakanan.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.favorite.FavoriteFragment;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan.MakananFragment;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.makananUser.MakananByUserFragment;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.profil.ProfilFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView mTextMessage;

    private MainPresenter presenter = new MainPresenter();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_makanan:
                    MakananFragment makananFragment = new MakananFragment();
                    loadFragment(makananFragment);
                    return true;
                case R.id.navigation_kelola_makanan:
                    getSupportActionBar().setTitle("Kelola Makanan");
                    MakananByUserFragment makananByUserFragment = new MakananByUserFragment();
                    loadFragment(makananByUserFragment);
                    return true;
                case R.id.navigation_profil:
                    ProfilFragment profilFragment = new ProfilFragment();
                    loadFragment(profilFragment);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MakananFragment makananFragment = new MakananFragment();
        loadFragment(makananFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                // Melakukan perintah logout presenter
                presenter.logoutSession(this);
                // Menutup mainActvity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadFragment(Fragment fragment) {
        // Menampilkan fragment menggunakan fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
