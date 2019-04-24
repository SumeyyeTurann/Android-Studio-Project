package application.sumeyyeturan.com.application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class cameraActivity extends AppCompatActivity {

    Button takePhoto;
    Button recordVideo;

    private static final int VIDEO_ACTION_CODE=101;
    private static final int IMAGE_ACTION_CODE=102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        takePhoto = (Button) findViewById(R.id.take_photo);
        recordVideo =(Button) findViewById(R.id.record_video);

        takePhoto.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeNewPhoto();
            }
        }));

        recordVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                revordNewVideo();
            }
        });

    }

    private void takeNewPhoto(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePhotoIntent, IMAGE_ACTION_CODE);
    }

    private void revordNewVideo(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takePictureIntent, VIDEO_ACTION_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // eğer fotoğraf seçilmemişse geri çık
        if(resultCode != RESULT_OK) return;

        //fotoğraf çekiyorsan başka bir yere yönlendirsin video çekiyorsa başka bir yere yönlendirsin
        switch (requestCode){
            //videoyu seçiyorsa
            case VIDEO_ACTION_CODE:
                // hem işlei tanımlayıp hem yönlendirmeyi yapıyor
                VideoView videoView=((VideoView) findViewById(R.id.videoPreview)); //videoviewden bir nesne oluşturup kullandım
                //önce videonun adresi sonra set
                videoView.setVideoURI(data.getData()); //URI tipinde videoyu bu adrese getir
                videoView.setMediaController(new MediaController(this));// videonun kendisini set ediyor

                //focus videonun otomatik başlatılması için kullanılır
                videoView.requestFocus();
                videoView.start();
                break;

             //resim seçilmişse
            case IMAGE_ACTION_CODE:
                //videoyu uri ile yaptık resimde bundle ile yapacaz. İkisiyle de yapılabilir
                Bundle extras = data.getExtras();
                //Bundle iki aktivity arasında veri alışverişi
                ((ImageView)findViewById(R.id.imagePreview)).setImageBitmap((Bitmap)extras.get("data"));
                break;
            default:
                break;

        }
    }


}
