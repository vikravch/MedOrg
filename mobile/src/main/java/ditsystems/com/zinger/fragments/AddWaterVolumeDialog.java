package ditsystems.com.zinger.fragments;

import android.app.Dialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ditsystems.com.zinger.MainActivity;
import ditsystems.com.zinger.R;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 29.04.2016.
 */
public class AddWaterVolumeDialog extends DialogFragment {
    private ViewGroup root;
    private int checked = -1;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_dialog_add_water,null);
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.rgAddWater);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checked = checkedId;
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Title")
                .setView(v)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int volume = 0;

                                switch (checked){
                                    case -1:
                                        String volStr = ((EditText) v.findViewById(R.id.etAddWaterVolume)).getText().toString();
                                        volume = Integer.parseInt(volStr);
                                        Log.d(Const.TAG_LOG_PROJECT,"edit text - "+volume);
                                        break;
                                    case R.id.rbCoffee:
                                        volume = 100;
                                        Log.d(Const.TAG_LOG_PROJECT,"redio button - "+volume);
                                        break;
                                    case R.id.rbGlass:
                                        volume = 250;
                                        Log.d(Const.TAG_LOG_PROJECT,"redio button - "+volume);
                                        break;
                                    case R.id.rbHalfLiter:
                                        volume = 500;
                                        Log.d(Const.TAG_LOG_PROJECT,"redio button - "+volume);
                                        break;
                                    }


                                ContentValues contentValues = new ContentValues();
                                contentValues.put(Const.FIELD_VOLUME_WATER,volume);

                                Calendar today = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                                String formatted = simpleDateFormat.format(today.getTime());

                                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
                                String formattedTime = simpleDateFormatTime.format(today.getTime());

                                Log.d(Const.TAG_LOG_PROJECT,formatted);
                                Log.d(Const.TAG_LOG_PROJECT,formattedTime);


                                contentValues.put(Const.FIELD_DAY_WATER,formatted);
                                contentValues.put(Const.FIELD_TIME_WATER,formattedTime);


                                Uri resId = getActivity().getContentResolver().insert(Const.URI_VALUE_WATER,contentValues);
                                Toast.makeText(getActivity(),"Insert - "+resId.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                                //((EditProfileDialog) getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();

        return dialog;
    }
}
