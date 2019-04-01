package com.blogspot.yourfavoritekaisar.crudmakanan.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan.MakananFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private TextView mTextMessage;

    private MainPresenter presenter = new MainPresenter();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_makanan:
                    mTextMessage.setText(R.string.title_makanan);

                    return true;
                case R.id.navigation_favorite:
                    mTextMessage.setText(R.string.title_favorite);
                    return true;
                case R.id.navigation_profil:
                    mTextMessage.setText(R.string.title_profil);
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

    private void loadFragment(MakananFragment makananFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, makananFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
