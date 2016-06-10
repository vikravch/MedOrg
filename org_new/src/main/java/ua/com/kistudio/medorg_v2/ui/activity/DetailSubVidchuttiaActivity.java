package ua.com.kistudio.medorg_v2.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.fragments.DoctorDialog;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Android on 09.06.2016.
 */
public class DetailSubVidchuttiaActivity extends AppCompatActivity implements View.OnClickListener {

    String[] answers;
    String[] answersSecond;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnoz_show_layout);

        answers = getResources().getStringArray(R.array.qs_ans_first);

        TextView tvOut = (TextView) findViewById(R.id.tvDiagnozShowActivity);

        tvOut.setText(compliteText());

        findViewById(R.id.btnSendDiagnozShow).setOnClickListener(this);


    }

    private String compliteText() {

        Intent intent = getIntent();
        String res = intent.getStringExtra(Params.OPROS_RESULT);
        Log.d(Params.LOG_TAG,res+" size - "+res.length());

        String[] resArray = res.split(";");
        String[] questions = null;
        if (res.length()==60){
            questions= getResources().getStringArray(R.array.qs);
        } else if (res.length()==46){
            questions= getResources().getStringArray(R.array.qs_s);
        }

        StringBuffer stringBuffer = new StringBuffer();
        //questArray = getResources().getStringArray(R.array.diagnoz_question);


        //stringBuffer.append(questArray[0]+"  \n");

        //stringBuffer.append("\n");

        //int answPosition = 2;
        for (int i=0; i<questions.length; i++){

            if ((resArray[i].equals("-1"))||(resArray[i].equals(""))){
                stringBuffer.append(questions[i]+"  no data \n\n ");
            }else{
                try {
                    //stringBuffer.append(questArray[i]+"\n  "+getResources().getStringArray(answResources[i-1])[Integer.parseInt(resArray[i+2])]+"\n ");
                    if ((res.length()==60)&&((i==28)||(i==29))){
                        stringBuffer.append(questions[i]+"   "+resArray[i]+"\n ");
                    } else {
                        stringBuffer.append(questions[i]+"   "+answers[Integer.parseInt(resArray[i])-1]+"\n ");
                    }
                } catch (IndexOutOfBoundsException ex){
                    //stringBuffer.append(questArray[i]+"\n  "+resArray[i]+"\n ");
                }
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendDiagnozShow:
               /* Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String email = sharedPreferences.getString(ProfileActivity.MAIL,"");
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Test");
                intent.setData(Uri.parse("mailto:doctor@ukr.net"));
                intent.putExtra(Intent.EXTRA_TEXT,compliteText());
                startActivity(Intent.createChooser(intent,"Email"));*/
                DoctorDialog doctorDialog = new DoctorDialog();
                Bundle bundle = new Bundle();
                bundle.putString("text",compliteText());
                doctorDialog.setArguments(bundle);
                doctorDialog.show(getSupportFragmentManager(),"Doctor");
                break;
        }
    }
}
