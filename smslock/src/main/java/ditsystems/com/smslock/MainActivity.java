package ditsystems.com.smslock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonCallForwardOn;
    Button buttonCallForwardOff;
    Button buttonAirplane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_forwarding_layout);

        buttonCallForwardOn = (Button) findViewById(R.id.buttonCallForwardOn);
        buttonCallForwardOn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                callforward("**21*+380951451195#  "); // 0123456789 is the number you want to forward the calls.
            }
        });

        buttonCallForwardOff = (Button) findViewById(R.id.buttonCallForwardOff);
        buttonCallForwardOff.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                callforward("#21#");
            }
        });

        buttonAirplane = (Button) findViewById(R.id.buttonAirplaneMode);
        buttonAirplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Click",Toast.LENGTH_SHORT).show();
                airplaneModeOn();
            }
        });
    }


    @SuppressLint("InlinedApi")
    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    @SuppressLint("NewApi")
    public void airplaneModeOn() {
            // TODO Auto-generated method stub

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.System.putInt(
                        getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 1);
            } else {
                Settings.Global.putInt(
                        getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 1);
            }


    }

    private void callforward(String callForwardString)
    {
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager)
                this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Intent intentCallForward = new Intent(Intent.ACTION_CALL);
        Uri mmiCode = Uri.fromParts("tel", callForwardString, "#");
        intentCallForward.setData(mmiCode);
        startActivity(intentCallForward);
    }

    private class PhoneCallListener extends PhoneStateListener
    {
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            if (TelephonyManager.CALL_STATE_RINGING == state)
            {
                // phone ringing
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state)
            {
                // active
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state)
            {
                // run when class initial and phone call ended, need detect flag
                // from CALL_STATE_OFFHOOK
                if (isPhoneCalling)
                {
                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }
            }
        }
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationManagerCompat notificationManagerCompat = (NotificationManagerCompat.from(this));
        notificationManagerCompat.cancelAll();
    }
*/
}
