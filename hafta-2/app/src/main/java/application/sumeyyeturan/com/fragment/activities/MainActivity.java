package application.sumeyyeturan.com.fragment.activities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import application.sumeyyeturan.com.fragment.R;
import application.sumeyyeturan.com.fragment.fragment.DietFragment;
import application.sumeyyeturan.com.fragment.fragment.HealthFragment;
import application.sumeyyeturan.com.fragment.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView =findViewById(R.id.bottomNavigationView);
        //buraya dönücez
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        //fragmentler arasında geçişler için
        FragmentTransaction transaction = getSupportFragmentManager()
                                            .beginTransaction();
        //hangi fragment üzerinde fragmentler haraket edecek onu tanımladık
        transaction.replace(R.id.frameLayout,new HomeFragment());
        transaction.commit();
    }
    //sayfalar arası geçiş için
    //on ile başlayan metodlar parmağımızla dokunduğumuz faaliyetler
    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() { //ctrl + spaceye new den sonra basınca otomati metodlar gelir
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //dokunma işlemi gerçekleşince işlemleri yapıyor
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_health:
                    selectedFragment=new HealthFragment();
                    break;
                case R.id.nav_diet:
                    selectedFragment=new DietFragment();
                    break;
            }
            //fragmenti seçtik ama içine gömmedik, burada içine gömüyoruz
            FragmentTransaction transaction=getSupportFragmentManager()
                                                            .beginTransaction();
            transaction.replace(R.id.frameLayout,selectedFragment);
            transaction.commit();

            return true;
        }
    };
}
