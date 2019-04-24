package application.sumeyyeturan.com.application;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;

public class smsActivity extends AppCompatActivity {

    private EditText edtNumber;
    private EditText edtMessage;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        edtMessage = findViewById(R.id.sender_message);
        edtNumber = findViewById(R.id.receiver_phone_number);
        btn=findViewById(R.id.send_sms_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number= edtNumber.getText().toString();
                String message =edtMessage.getText().toString();

                Log.d("NUMBER",number);
                Log.d("MESAJ",message);
                //bir yere veri göndermek için bundle kullanılır

                Uri uri = Uri.parse("smsto:" + number);
                //ACTION_SENDTO mesaj uygulamasını açmaya yarıyor
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body",message);
                startActivity(intent);

            }
        });
    }
}
