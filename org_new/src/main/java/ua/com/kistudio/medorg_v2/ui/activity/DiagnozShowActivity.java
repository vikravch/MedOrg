package ua.com.kistudio.medorg_v2.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;

/**
 * Created by Android on 25.05.2016.
 */
public class DiagnozShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnoz_show_layout);
        TextView tvRes = (TextView) findViewById(R.id.tvDiagnozShowActivity);


        StringBuffer stringBuffer = new StringBuffer();

        String[] questArray = getResources().getStringArray(R.array.diagnoz_question);
        String[] resArray = getIntent().getStringExtra("res").split(";");

        stringBuffer.append(questArray[0]+"  \n");

        if (resArray[0].equals("-1")){
            stringBuffer.append(" T - no data \n ");
        } else {
            stringBuffer.append(getResources().getStringArray(R.array.tnm_t_array)[Integer.parseInt(resArray[0])]+"\n");
        }

        if (resArray[1].equals("-1")){
            stringBuffer.append(" N - no data \n");
        } else {
            stringBuffer.append(getResources().getStringArray(R.array.tnm_n_array)[Integer.parseInt(resArray[1])]+"\n");
        }

        if (resArray[2].equals("-1")){
            stringBuffer.append(" M - no data \n");
        } else {
            stringBuffer.append(getResources().getStringArray(R.array.tnm_m_array)[Integer.parseInt(resArray[2])]+"\n");
        }

        stringBuffer.append("\n");



        int[] answResources = {R.array.diagnoz_answer_2,R.array.diagnoz_answer_3,R.array.diagnoz_answer_4,R.array.diagnoz_answer_5,
                R.array.diagnoz_answer_6,R.array.diagnoz_answer_7,R.array.diagnoz_answer_8,R.array.diagnoz_answer_9_1,R.array.diagnoz_answer_9_2,
                R.array.diagnoz_answer_10,R.array.diagnoz_answer_11_1,R.array.diagnoz_answer_11_2};

       /* if (resArray[3].equals("-1")){
            stringBuffer.append(questArray[1]+"  no data \n\n ");
        }else{
            stringBuffer.append(questArray[1]+"\n  "+getResources().getStringArray(answResources[0])[Integer.parseInt(resArray[3])]+"\n ");}
*/
        int answPosition = 2;
        for (int i=1; i<questArray.length; i++){

            if ((resArray[i+2].equals("-1"))||(resArray[i+2].equals(""))){
                stringBuffer.append(questArray[i]+"  no data \n\n ");
            }else{
                try {
                    stringBuffer.append(questArray[i]+"\n  "+getResources().getStringArray(answResources[i-1])[Integer.parseInt(resArray[i+2])]+"\n ");
                } catch (IndexOutOfBoundsException ex){
                    stringBuffer.append(questArray[i]+"\n  "+resArray[i+2]+"\n ");
                }
                }
        }

        tvRes.setText(stringBuffer.toString());
    }
}
