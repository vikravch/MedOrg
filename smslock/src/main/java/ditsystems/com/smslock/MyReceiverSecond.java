package ditsystems.com.smslock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiverSecond extends BroadcastReceiver {
    private static final String LOG_TAG = "SmsReceiver";
    public MyReceiverSecond() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"SMS Received action "+intent.getAction(),Toast.LENGTH_LONG).show();


        Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
        SmsMessage[] msgs = null;
        String msg_from;
        String res = "";
        if (bundle != null){
            //---retrieve the SMS message received---
            try{
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for(int i=0; i<msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    msg_from = msgs[i].getOriginatingAddress();
                    String msgBody = msgs[i].getMessageBody();
                    res+=msgBody;
                }
            }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
            }
        }
        Log.d(LOG_TAG, "Is sticky: "+isInitialStickyBroadcast());
        Log.d(LOG_TAG,"Is ordered: "+isOrderedBroadcast());
        Log.d(LOG_TAG,"SMS second - "+res);
        setResult(0,null,null);
        abortBroadcast();
    }
}
