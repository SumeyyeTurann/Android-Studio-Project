package application.sumeyyeturan.com.sanatdefterimuygulamasi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase myDataBase = this.openOrCreateDatabase("Musician",MODE_PRIVATE,null);
            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS musicians (name VARCHAR, age INT(2))");
            //myDataBase.execSQL("INSERT INTO musicians (name, age) VALUES ('James', 50)");

            Cursor cursor = myDataBase.rawQuery("SELECT * FROM musicians",null);

            int nameIx = cursor.getColumnIndex("name");
            int ageIx = cursor.getColumnIndex("age");
            cursor.moveToFirst(); //cursoru sayfanın en başına götürdü

            while(cursor != null){ //cursor boş bir satıra gelmediği sürece devam et
                System.out.println("Name: " + cursor.getString(nameIx));
                System.out.println("Age: " + cursor.getInt(ageIx));
                
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace(); //bir hata ilr karşılaşınca hatayı monitörde yaz
        }


    }
}
