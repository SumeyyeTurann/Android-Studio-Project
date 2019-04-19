package application.sumeyyeturan.com.application;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class voiceActivity extends AppCompatActivity {

    private static final int REQUEST_AUDIO_PERMISSION_CODE =200 ;
    private final String filepath = Environment.getExternalStorageDirectory().getPath() + "/record.3gp";

    private Button btnKaydet;
    private Button btnCal;
    private Button btnDurdur;

    //kayıt yapacağım için MediaRecorder kullandım. Mikrofona ulaşır
    private MediaRecorder recorder;
    private MediaPlayer player;

    //+ dedikten sonra ses dosyasının ismini veriyoruz ve 3gp dosya boyutunun küçük olmasını sağlıyor.
    private final String path = Environment.getExternalStorageDirectory().getPath() + "gizlises.3gp";

    //ses kaydetmenin izin kodu 200
    public static final int PERMISSON_CODE=200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        btnCal=findViewById(R.id.record_voice_button);
        btnCal.setOnClickListener(this);
        btnDurdur=findViewById(R.id.stop_voice_button);
        btnDurdur.setOnClickListener(this);
        btnKaydet=findViewById(R.id.record_voice_button);
        btnKaydet.setOnClickListener(this);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecord();
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecord();
            }
        });
    }
    public void startRecord(){
        recorder=new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filepath);

        try{
            recorder.prepare();
            recorder.start();
            Toast.makeText(getApplicationContext(),
                    "Kayıt Yapılıyor",
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace(); //bunu yazmasak hata çıksa logcatte gözükmez
        }

    }
    public void stopRecord(){
        //recorder çalışıyor mu?
        if(recorder != null){
            Toast.makeText(getApplicationContext() //ctrl+q yazınca toastın dökümantasyonunu verir
                    , "Kayıt Durdu"
                    , Toast.LENGTH_SHORT).show();
            recorder.stop();
            recorder.reset(); //açılan başka kayıtın üzerine açılmaması için resetledik
            recorder.release();
        }
    }
    public void startPlay(){
        player=new MediaPlayer();
        player.setVolume(1.0f,1.0f); //sol kulaklığa veya sağ kulaklığa gelen sesi belirleyebiliyoruz
        //play butonuna basınca hata ile karşılaşırsak diye
        try{
            player.setDataSource(filepath);
            player.prepare();
            player.start();
            //ses kaydının bittiğini belirten fonksiyon
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.stop();
                    //tekrar çalışır duruma getir
                    player.release();
                    player=null;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean checkPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(voiceActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}
