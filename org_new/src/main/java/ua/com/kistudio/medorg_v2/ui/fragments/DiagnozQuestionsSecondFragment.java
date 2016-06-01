package ua.com.kistudio.medorg_v2.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 07.04.2016.
 */
public class DiagnozQuestionsSecondFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pos = getArguments().getInt("pos");
        View view = inflater.inflate(R.layout.fragment_diagnoz_radio,container,false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgDiagnozFragment);
        radioGroup.setOnCheckedChangeListener(this);


        String[] questions = getResources().getStringArray(R.array.diagnoz_question);

        ((TextView) view.findViewById(R.id.tvQuestionFragmentDR)).setText(questions[pos-1]);
        ((TextView) getActivity().findViewById(R.id.tvAnswerActivityDiagnoz)).setText("");
        String[] answers = null;

        switch (pos){
            case 2:
                answers = getResources().getStringArray(R.array.diagnoz_answer_2);
                break;
            case 3:
                answers = getResources().getStringArray(R.array.diagnoz_answer_3);
                break;
            case 4:
                answers = getResources().getStringArray(R.array.diagnoz_answer_4);
                break;
            case 5:
                answers = getResources().getStringArray(R.array.diagnoz_answer_5);
                break;
            case 6:
                answers = getResources().getStringArray(R.array.diagnoz_answer_6);
                break;
            case 8:
                answers = getResources().getStringArray(R.array.diagnoz_answer_8);
                break;
            case 11:
                answers = getResources().getStringArray(R.array.diagnoz_answer_10);
                break;
            case 12:
                answers = getResources().getStringArray(R.array.diagnoz_answer_10_2);
                break;
            case 14:
                answers = getResources().getStringArray(R.array.diagnoz_answer_11_2);
                break;
        }

        for (int i=0; i<answers.length; i++){
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setTextSize(18);
            radioButton.setShadowLayer(1,1,1,getResources().getColor(R.color.text_shadow_color));
            radioButton.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            radioButton.setId(i);
            radioButton.setText(answers[i]);
            radioGroup.addView(radioButton);
        }
        if (((DiagnozActivity) getActivity()).answersOnQuestions[pos+1]!="-1"){
            int id = Integer.parseInt(((DiagnozActivity) getActivity()).answersOnQuestions[pos+1]);
            radioGroup.check(id);
            Log.d(Params.LOG_TAG,"From save - "+((DiagnozActivity) getActivity()).answersOnQuestions[pos+1]);
        }

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = ""+checkedId;
        Log.d(Params.LOG_TAG,"CheckArray - "+ Arrays.toString(((DiagnozActivity) getActivity()).answersOnQuestions));
    }
}
