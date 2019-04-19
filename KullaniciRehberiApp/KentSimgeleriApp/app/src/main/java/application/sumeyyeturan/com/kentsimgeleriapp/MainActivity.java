package application.sumeyyeturan.com.kentsimgeleriapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listViewi tanıtıyoruz
        ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> landmarkNames = new ArrayList<String>();
        landmarkNames.add("Pisa");
        landmarkNames.add("Colloseo");
        landmarkNames.add("Effel");
        landmarkNames.add("London Bridge");

        Bitmap pisa = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.pisakulesi);
        Bitmap colosseum = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.romecolloseum);
        Bitmap eiffel = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.effeltower2);
        Bitmap bridge = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.londonbridge);
        final ArrayList<Bitmap> landmarkImages=new ArrayList<>();

        landmarkImages.add(pisa);
        landmarkImages.add(colosseum);
        landmarkImages.add(eiffel);
        landmarkImages.add(bridge);

        //bağlantı oluşturmak için
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,landmarkNames);

        listView.setAdapter(arrayAdapter);

        //listelere tıklayınca details sayfasına gitmesi için
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);

                //textviewa başlığı ekleme
                intent.putExtra("name",landmarkNames.get(position));
                //kullanıcı herhangi bir image tıkladığında selected imageyi değiştirmek için
                //kolay olan yol fakat hafızayı yoruyor diye kullanmıyoruz
                //selectedImage=landmarkImages.get(position);

                Bitmap bitmap =landmarkImages.get(position);
                Globals globals = Globals.getInstance();
                globals.setData(bitmap);

                startActivity(intent);
            }
        });
    }
}
