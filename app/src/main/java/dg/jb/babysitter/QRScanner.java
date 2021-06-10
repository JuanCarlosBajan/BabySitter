package dg.jb.babysitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QRScanner extends AppCompatActivity {


    private static final int REQUEST_CAMERA_PERMISSION = 0;
    private CodeScanner codeScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        codeScanner = new CodeScanner(this,findViewById(R.id.scanner_view));
        codeScannerFunction();

    }

    private void codeScannerFunction(){
        if(checkPermission()){

            codeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(QRScanner.this, result.getText(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QRScanner.this, IPCamera.class);
                            intent.putExtra("ip", result.getText().toString());
                            startActivity(intent);
                        }
                    });
                }
            });
            (findViewById(R.id.scanner_view)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeScanner.startPreview();
                }
            });
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkPermission())
            codeScanner.startPreview();
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

}