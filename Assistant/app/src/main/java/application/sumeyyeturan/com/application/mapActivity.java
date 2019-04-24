package application.sumeyyeturan.com.application;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mapActivity extends AppCompatActivity {

    Button openMapPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        openMapPage=findViewById(R.id.open_map_page);
        openMapPage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Uri geoLocation = Uri.parse("geo:41.0138400,28.9496600");  //geo yazınca lokasyona gidiyor
                showMap(geoLocation);

            }
        });

    }
    public void showMap(Uri geoLocation){
        //telefonda haritalar uygulamasını açması için bir intent oluşturuyoruz
        Intent intent = new Intent(Intent.ACTION_VIEW); //ACTION_VIEW haritalar uygulamasını açıyor
        intent.setData(geoLocation); //intenti set ediyoruz
        if(intent.resolveActivity(getPackageManager())!=null){ //haritalar uygulamasının telefonda olup olmadığını kontrol ediyor
            startActivity(intent); //uygulamayı başlat
        }
    }
}


