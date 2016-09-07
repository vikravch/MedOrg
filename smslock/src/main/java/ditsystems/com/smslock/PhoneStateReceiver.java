package ditsystems.com.smslock;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Android on 05.07.2016.
 */
    public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String LOG = "SmsReceiver";
    private Context mContext;
    public static String TAG="PhoneStateReceiver";
    Method methodEndCall;
    Object telephonyInterface;

    @Override
        public void onReceive(Context context, Intent intent) {
        mContext = context;
            if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                Log.d(TAG,"PhoneStateReceiver**Call State=" + state);

                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Log.d(TAG,"PhoneStateReceiver**Idle");
                } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    // Incoming call
                    String incomingNumber =
                            intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Log.d(TAG,"PhoneStateReceiver**Incoming call " + incomingNumber);

                    if (!killCall(context)) { // Using the method defined earlier
                        Log.d(TAG,"PhoneStateReceiver **Unable to kill incoming call");
                    }

                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    Log.d(TAG,"PhoneStateReceiver **Offhook");
                }
            } else if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                // Outgoing call
                String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.d(TAG,"PhoneStateReceiver **Outgoing call " + outgoingNumber);

                setResultData(null); // Kills the outgoing call

            } else {
                Log.d(TAG,"PhoneStateReceiver **unexpected intent.action=" + intent.getAction());
            }
        }

        public boolean killCall(Context context) {
            try {
                // Get the boring old TelephonyManager
                TelephonyManager telephonyManager =
                        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                // Get the getITelephony() method
                Class classTelephony = Class.forName(telephonyManager.getClass().getName());
                Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

                // Ignore that the method is supposed to be private
                methodGetITelephony.setAccessible(true);

                // Invoke getITelephony() to get the ITelephony interface
                telephonyInterface = methodGetITelephony.invoke(telephonyManager);

                // Get the endCall method from ITelephony
                Class telephonyInterfaceClass =
                        Class.forName(telephonyInterface.getClass().getName());
                methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

                // Doesn't work
                // telephonyManager.getClass().getMethod("answerRingingCall").invoke(telephonyManager);

                Log.d(LOG,"Before answer");
                //delayCall();
                //wakeDevice();
                //callforward("**21*+380951451195#  ");
                //Thread.sleep(4000);
                //answerCall();
                enableFly();


                // Invoke endCall()
                //methodEndCall.invoke(telephonyInterface);

            } catch (Exception ex) { // Many things can go wrong with reflection calls
                Log.d(TAG,"PhoneStateReceiver **" + ex.toString());
                return false;
            }
            return true;
        }

    private void enableFly() {
        boolean isEnabled = Settings.System.getInt(
                mContext.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;

        // Toggle airplane mode.
        Settings.System.putInt(
                mContext.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

// Post an intent to reload.
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !isEnabled);
        mContext.sendBroadcast(intent);
    }

    /*private void delayCall() {
        TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        Class c;
        final Object telephonyService;
        try {
            c = Class.forName("android.telephony.TelephonyManager");//telephony.getClass().getName());
            Log.i("TelephonyClass Name", telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (TelephonyManager) m.invoke(telephony);


            Method m2 = telephonyService.getClass().getDeclaredMethod("silenceRinger");
            Method m3 = telephonyService.getClass().getDeclaredMethod("endCall");
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    try {
                        if (telephonyService.isIdle()
                                || telephonyService.isOffhook()
                                || telephonyService.isRinging())
                            telephonyService.endCall();
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            long delay = 4 * 1000;
            new Timer().schedule(task, delay);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/


   /* private void answerCall() {
        Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
        buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(
                KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
        mContext.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
    }*/

    private void answerCall() {
        new Thread(new Runnable() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void run() {

                Log.d(LOG,"Up");
                String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                        Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_HEADSETHOOK));
                Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                        Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                KeyEvent.KEYCODE_HEADSETHOOK));

                mContext.sendOrderedBroadcast(btnDown, enforcedPerm);
                mContext.sendOrderedBroadcast(btnUp, enforcedPerm);

                Intent intent = new Intent();
                String packageName = mContext.getPackageName();
                PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                if (pm.isIgnoringBatteryOptimizations(packageName))
                    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                else {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                try {
                    Runtime.getRuntime().exec("input keyevent " +
                            Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                    Log.d(LOG,"Exec 1 ");
                } catch (IOException e) {
                    Log.d(LOG,"Exception 1 ");
                    e.printStackTrace();
                }

                /*try {
                    Runtime.getRuntime().exec("input keyevent " +
                            Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                } catch (IOException e) {
                    // Runtime.exec(String) had an I/O problem, try to fall back
                    String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                    Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                    KeyEvent.KEYCODE_HEADSETHOOK));
                    Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                    KeyEvent.KEYCODE_HEADSETHOOK));

                    mContext.sendOrderedBroadcast(btnDown, enforcedPerm);
                    mContext.sendOrderedBroadcast(btnUp, enforcedPerm);
                    mContext.sendOrderedBroadcast(btnDown, enforcedPerm);

                }*/

              /*  try {
                    Thread.sleep(4000);
                    Log.d(LOG,"Test Log");
                    methodEndCall.invoke(telephonyInterface);
                } catch (InterruptedException e1) {
                    Log.d(LOG,"Test Exception 1");
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    Log.d(LOG,"Test Exception 2");
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    Log.d(LOG,"Test Exception 3");
                    e1.printStackTrace();
                }*/
            }

        }).start();
    }

    PowerManager.WakeLock fullWakeLock;

    public void wakeDevice() {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        fullWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "bbbb");
        Log.d(LOG,"Wake 0");
        if ((fullWakeLock != null) &&           // we have a WakeLock
                (fullWakeLock.isHeld() == false)) {  // but we don't hold it
            fullWakeLock.acquire();
        }
        Log.d(LOG,"Wake 1");
    }

    private void callforward(String callForwardString)
    {
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager)
                mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Intent intentCallForward = new Intent(Intent.ACTION_CALL);
        Uri mmiCode = Uri.fromParts("tel", callForwardString, "#");
        intentCallForward.setData(mmiCode);
        intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intentCallForward);
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
                    Intent i = mContext.getPackageManager()
                            .getLaunchIntentForPackage(mContext.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);
                    isPhoneCalling = false;
                }
            }
        }
    }


}

