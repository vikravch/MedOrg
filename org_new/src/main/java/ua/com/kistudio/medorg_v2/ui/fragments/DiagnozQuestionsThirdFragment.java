package ua.com.kistudio.medorg_v2.ui.fragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 07.04.2016.
 */
public class DiagnozQuestionsThirdFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    LinearLayout view;
    int pos;
    EditText etOut;
    TextView textView;
    RadioGroup radioGroup;
    CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        pos = getArguments().getInt("pos");
        String[] questions = getResources().getStringArray(R.array.diagnoz_question);

        view = new LinearLayout(getActivity());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView(getActivity());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(18);
        textView.setShadowLayer(1, 1, 1, getResources().getColor(R.color.text_shadow_color));

        textView.setText(questions[pos - 1]);

        Log.d(Params.LOG_TAG, questions[pos - 1] + " position - " + pos);

        radioGroup = new RadioGroup(getActivity());
        radioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOnCheckedChangeListener(this);

        view.addView(textView);
        view.addView(radioGroup);
        createViewCustom();
        return view;
    }

    private void createViewCustom() {

        RadioButton radioButton = new RadioButton(getActivity());
        radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioButton.setTextSize(18);
        radioButton.setShadowLayer(1, 1, 1, getResources().getColor(R.color.text_shadow_color));
        radioButton.setId(1);

        RadioButton radioButton2 = new RadioButton(getActivity());
        radioButton2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioButton2.setTextSize(18);
        radioButton2.setShadowLayer(1, 1, 1, getResources().getColor(R.color.text_shadow_color));
        radioButton2.setId(2);

        checkBox = new CheckBox(getActivity());
        checkBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox.setTextSize(18);
        checkBox.setShadowLayer(1, 1, 1, getResources().getColor(R.color.text_shadow_color));
        checkBox.setOnCheckedChangeListener(this);
        checkBox.setId(3);

        etOut = new EditText(getActivity());
        etOut.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        etOut.setEms(3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            etOut.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_bottom_border, null));
        }


        String[] arrayAnsw;

        String savedAnsw = ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1];

        switch (pos){
            case 7:
                arrayAnsw = getResources().getStringArray(R.array.diagnoz_answer_7);
                radioButton.setText(arrayAnsw[0]);
                radioGroup.addView(radioButton);
                etOut.setEms(3);
                view.addView(etOut);

                radioButton2.setText(arrayAnsw[1]);
                radioGroup.addView(radioButton2);

                switch (savedAnsw){
                    case "":
                    case "-1":

                        break;
                    case "1":
                        radioGroup.check(1);
                        break;
                    default:
                        radioGroup.check(2);
                        etOut.setText(savedAnsw);
                }

                break;
            case 9:
                arrayAnsw = getResources().getStringArray(R.array.diagnoz_answer_9_1);
                checkBox.setText(arrayAnsw[0]);
                view.addView(etOut);
                view.addView(checkBox);

                switch (savedAnsw){
                    case "-1":

                        break;
                    case "1":
                        checkBox.setChecked(true);
                        break;
                    default:
                        checkBox.setChecked(false);
                        etOut.setText(savedAnsw);
                }
                break;
            case 10:
                arrayAnsw = getResources().getStringArray(R.array.diagnoz_answer_9_2);
                checkBox.setText(arrayAnsw[0]);
                view.addView(etOut);
                view.addView(checkBox);

                switch (savedAnsw){
                    case "-1":

                        break;
                    case "1":
                        checkBox.setChecked(true);
                        break;
                    default:
                        checkBox.setChecked(false);
                        etOut.setText(savedAnsw);
                }
                break;
            case 13:
                arrayAnsw = getResources().getStringArray(R.array.diagnoz_answer_11_1);

                LinearLayout localLinear = new LinearLayout(getActivity());
                localLinear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                localLinear.setOrientation(LinearLayout.HORIZONTAL);

                TextView textViewAsw = new TextView(getActivity());
                textViewAsw.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewAsw.setText(arrayAnsw[0]);
                textViewAsw.setTextSize(18);
                textViewAsw.setTypeface(null, Typeface.BOLD);
                textViewAsw.setShadowLayer(1, 1, 1, getResources().getColor(R.color.text_shadow_color));

                localLinear.addView(textViewAsw);
                localLinear.addView(etOut);
                view.addView(localLinear);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String etText = etOut.getText().toString();
        Log.d(Params.LOG_TAG,"id - "+checkedId);
        switch (pos){
            case 7:
                if (checkedId==1){
                    ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = "1";
                } else{
                    ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = etOut.getText().toString();
                }
                break;
            case 9:
                ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = etOut.getText().toString();
                break;
            case 10:

                break;
            case 13:

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String etText = etOut.getText().toString();
        if (isChecked){
            ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = "1";
            Log.d(Params.LOG_TAG,"Checked");
            etOut.setText("");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        String etVal = etOut.getText().toString();
        if ((etVal!=null)||(etVal!="")){
            ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = etOut.getText().toString();
        }

        if(checkBox.isChecked())
            ((DiagnozActivity) getActivity()).answersOnQuestions[pos+1] = "1";
        Log.d(Params.LOG_TAG,"Detach");
    }
}
