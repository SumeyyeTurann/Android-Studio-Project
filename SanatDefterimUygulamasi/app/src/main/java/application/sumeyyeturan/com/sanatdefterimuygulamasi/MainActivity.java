package application.sumeyyeturan.com.sanatdefterimuygulamasi;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDataBase = this.openOrCreateDatabase("Musician",MODE_PRIVATE,null);
    }
}