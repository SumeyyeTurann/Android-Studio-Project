package application.sumeyyeturan.com.instagramclonefirebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadActivity extends AppCompatActivity {


    ImageView takePhoto;
    VideoView recordVideo;
    ImageView imageViewGaleri;
    Button paylas;
    TextView editTextAciklama;

    Uri selectedimage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;


    private static final int VIDEO_ACTION_CODE=101;
    private static final int IMAGE_ACTION_CODE=102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        takePhoto = (ImageView) findViewById(R.id.imageViewCamera);
        recordVideo = (VideoView) findViewById(R.id.videoView);
        imageViewGaleri = (ImageView) findViewById(R.id.imageViewCamera);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();

        mStorageRef=FirebaseStorage.getInstance().getReference();




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
                VideoView videoView=((VideoView) findViewById(R.id.videoView)); //videoviewden bir nesne oluşturup kullandım
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
                ((ImageView)findViewById(R.id.imageViewCamera)).setImageBitmap((Bitmap)extras.get("data"));
                break;
            default:
                break;

        }
    }

    //---------------------------------------------------------------------------------------------------------------------

    public void paylas(View view){

        //kayıt edilen her resime farklı UUID verip kayıt etmesi için
        UUID uuid = UUID.randomUUID();
        final String imageName="images/" + uuid + ".jpg";

        StorageReference storageReference = mStorageRef.child(imageName);
        storageReference.putFile(selectedimage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Veritabanına Kaydedilecekler
                //download url
                StorageReference newReferance = FirebaseStorage.getInstance().getReference(imageName);//resmi nasıl kaydettiysek ona referans vermemiz gerekir
                //kaydedilen resmin yerine gidip referans olacak ver urlsini bize verecek
                newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //url yi stringe çeviriyoruz
                        String downloadUrl = uri.toString();

                        //username
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userEmail =user.getEmail();

                        // comment
                        String userComment = editTextAciklama.getText().toString();

                        UUID uuıd1 = UUID.randomUUID();
                        String uuidString = uuıd1.toString();

                        myRef.child("Posts").child(uuidString).child("useremail").setValue(userEmail);
                        myRef.child("Posts").child(uuidString).child("comemnr").setValue(userComment);
                        myRef.child("Posts").child(uuidString).child("downloadurl").setValue(downloadUrl);

                        Toast.makeText(UploadActivity.this,"Post Eklendi :)", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),AnaSayfaActivity.class);
                        startActivity(intent);
                    }
                });



            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void sec(View view){
        //galeriye erişmek için izin yoksa izin istiyor
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != (PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
        else{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //buradaki URI seçilen resmin yolunu veriyor
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            //izni alabildiysek
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}

