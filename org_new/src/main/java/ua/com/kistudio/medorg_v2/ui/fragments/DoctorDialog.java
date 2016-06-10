package ua.com.kistudio.medorg_v2.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.ProfileActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Android on 10.06.2016.
 */
public class DoctorDialog extends DialogFragment {
    ArrayList<String> array;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.choose_doctor_text);

        array = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(Params.CONTENT_URI_DOCTOR,null,null,null,null);

        if (cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                array.add(cursor.getString(cursor.getColumnIndex(Params.DOCTOR_PIB))+ "  -"+cursor.getString(cursor.getColumnIndex(Params.DOCTOR_MAIL))+"");
            } while (cursor.moveToNext());
        }
        else{
            array.add(getResources().getString(R.string.doctor_empty_text));
        }
        //array.add("sasdasdsadasd");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                strName = strName.split("-")[1];
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String email = sharedPreferences.getString(ProfileActivity.MAIL,"");

                /*Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Test");
                intent.setData(Uri.parse("mailto:"+strName));
                intent.putExtra(Intent.EXTRA_TEXT,getArguments().getString("text"));
                startActivity(Intent.createChooser(intent,"Email"));*/
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode(strName) +
                        "?subject=" + Uri.encode("Тест") +
                        "&from="+Uri.encode(email)+
                        "&body=" + Uri.encode(getArguments().getString("text"));
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });


        return builder.create();
    }
}
