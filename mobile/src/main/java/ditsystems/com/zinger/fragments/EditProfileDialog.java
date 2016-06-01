package ditsystems.com.zinger.fragments;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ditsystems.com.zinger.MainActivity;
import ditsystems.com.zinger.R;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 26.04.2016.
 */
public class EditProfileDialog extends DialogFragment {
    private ViewGroup root;
    SharedPreferences sharedPreferences;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int title = getArguments().getInt("title");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_dialog_edit_profile,null);

        ((EditText) v.findViewById(R.id.etGender)).setText(sharedPreferences.getString(Const.KEY_GENDER_SHARED,""));

        ((EditText) v.findViewById(R.id.etAge)).setText(sharedPreferences.getString(Const.KEY_AGE_SHARED,""));
        ((EditText) v.findViewById(R.id.etWeight)).setText(sharedPreferences.getString(Const.KEY_WEIGHT_SHARED,""));
        ((EditText) v.findViewById(R.id.etHeight)).setText(sharedPreferences.getString(Const.KEY_HEIGHT_SHARED,""));



        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Title")
                .setView(v)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String genderValue = ((EditText) v.findViewById(R.id.etGender)).getText().toString();
                                String ageValue = ((EditText) v.findViewById(R.id.etAge)).getText().toString();
                                String weightValue = ((EditText) v.findViewById(R.id.etWeight)).getText().toString();
                                String heightValue = ((EditText) v.findViewById(R.id.etHeight)).getText().toString();

                                sharedPreferences.edit().putString(Const.KEY_GENDER_SHARED,genderValue).commit();
                                sharedPreferences.edit().putString(Const.KEY_AGE_SHARED,ageValue).commit();
                                sharedPreferences.edit().putString(Const.KEY_WEIGHT_SHARED,weightValue).commit();
                                sharedPreferences.edit().putString(Const.KEY_HEIGHT_SHARED,heightValue).commit();

                                ((MainActivity) getActivity()).resetView();

                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((EditProfileDialog) getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();

        return dialog;
    }
}
