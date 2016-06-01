package ditsystems.com.zinger.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 27.04.2016.
 */
public class BaseActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION = 1;
    private static final int SEND_SMS_PERMISSION = 2;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_consult:
                startCallForConsult();
                break;
            case R.id.action_sos:
                sendSosSMS();
                break;
            case R.id.action_market:
                openMarket();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openMarket() {
        String url = "http://www.google.com.ua";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void sendSosSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
           // Toast.makeText(this, R.string.no_permission_massage, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION);
            }
        else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("321123", null, "sms message", null, null);
            Toast.makeText(this, R.string.sms_send, Toast.LENGTH_LONG).show();
        }
    }

    private void startCallForConsult() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ getResources().getString(R.string.service_center_number)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, R.string.no_permission_massage, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION);
        }else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCallForConsult();
                } else {

                   Toast.makeText(this,R.string.no_permission_massage,Toast.LENGTH_LONG).show();
                }
                return;

            case SEND_SMS_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSosSMS();
                } else {
                    Toast.makeText(this,R.string.no_permission_massage,Toast.LENGTH_LONG).show();
                }
                return;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
