package ua.com.kistudio.medorg_v2.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 18.03.2016.
 */
public class DiagnozQuestionsFirstFragment extends Fragment implements AdapterView.OnItemClickListener {
    String[] array;
    ListView listView;
    int positionQw = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        positionQw =getArguments().getInt("pos");
        array = new String[]{};
        switch (positionQw){
            case 1:
                array = getResources().getStringArray(R.array.tnm_t_array);
                break;
            case 2:
                array = getResources().getStringArray(R.array.tnm_n_array);
                break;
            case 3:
                array = getResources().getStringArray(R.array.tnm_m_array);
                break;
            case 4:
                array = getResources().getStringArray(R.array.tnm_g_array);
                break;
        }

        View view = inflater.inflate(R.layout.fragment_diagnoz_list,container,false);
        listView = (ListView) view.findViewById(R.id.lvDiagnozQuestion);
        if (positionQw!=4){
            ((TextView) view.findViewById(R.id.tvFragmentDiagnozList)).setText(getResources().getStringArray(R.array.diagnoz_question)[0]);
        } else{
            ((TextView) view.findViewById(R.id.tvFragmentDiagnozList)).setText(getResources().getStringArray(R.array.diagnoz_question)[1]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.menu3_item,
                R.id.tvPibDoctor,
                array);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        if(!(((DiagnozActivity) getActivity()).answersOnQuestions[positionQw-1].equals("-1"))){
        ((TextView) getActivity().findViewById(R.id.tvAnswerActivityDiagnoz)).
                setText(getResources().getString(R.string.choosed_text) + " " + array[Integer.parseInt(((DiagnozActivity) getActivity()).answersOnQuestions[positionQw-1])]);
        }
        else{((TextView) getActivity().findViewById(R.id.tvAnswerActivityDiagnoz)).
                setText(getResources().getString(R.string.choosed_text));
        }
        return view;
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {


        String[] masStr = new String[]{""};
        Log.d("MedOrg", " " + position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        String selectedFromList =(String) (listView.getItemAtPosition(position));
        char[] masch = selectedFromList.toCharArray();

        if (masch[0]=='T'){masStr = getResources().getStringArray(R.array.t);
        //    answ[0]=position; answStr[0]=selectedFromList;
        }
        if (masch[0]=='N'){masStr = getResources().getStringArray(R.array.n);
        //    answ[1]=position; answStr[1]=selectedFromList;
        }
        if ((masch[0]=='M')||(masch[0]=='c')){masStr = getResources().getStringArray(R.array.m);
        //    answ[2]=position; answStr[2]=selectedFromList;
        }
        if (masch[0]=='G'){masStr = getResources().getStringArray(R.array.g);
            //    answ[2]=position; answStr[2]=selectedFromList;
        }
        String resTitle = masStr[position];
        alertDialogBuilder.setTitle(selectedFromList);
        Log.d("MedOrg","fromclick1");
        //answ[0]=position;
        alertDialogBuilder.setMessage(resTitle)
                .setPositiveButton("Так",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        ((DiagnozActivity) getActivity()).answersOnQuestions[positionQw-1] = ""+position;
                        ((TextView) getActivity().findViewById(R.id.tvAnswerActivityDiagnoz)).setText(getResources().getString(R.string.choosed_text)+" "+array[position]);
                        Log.d(Params.LOG_TAG,"CheckArray - "+ Arrays.toString(((DiagnozActivity) getActivity()).answersOnQuestions));
                    }
                })
                .setNegativeButton("Ні",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }});
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
