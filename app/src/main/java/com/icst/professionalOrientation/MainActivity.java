package com.icst.professionalOrientation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.Spanned;

import java.util.function.ToIntFunction;

public class MainActivity extends AppCompatActivity {

    private TextView question; // поле с вопросом
    private Button button1; // первая кнопка (левая)
    private Button button2; // вторая кнопка (правая)
    private int curQuestionNum; // номер текущего вопроса

    private  int[] recom_prof = new int[3]; // список рекомендуемых профессий
    private int leftButton = -1; // та профессия которая добавится при нажатии левой кнопки
    private int rightButton = -1; // та профессия которая добавится при нажатии правой кнопки


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i=0; recom_prof.length>i;i++)
        {
            recom_prof[i]=-1;
        }
        setContentView(R.layout.activity_main);
    }

    // обработка события нажатия на кнопку "НАЧАТЬ ТЕСТ"
    public void ButtonStartClick(View v)
    {

        setContentView(R.layout.test);
        question = findViewById(R.id.question);
        button1 = findViewById(R.id.firstButton);
        button2 = findViewById(R.id.secondButton);
        curQuestionNum = 1;
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "ПРОФЕССИИ"
    public void ButtonProfessionsClick(View v)
    {
        setContentView(R.layout.all_professions);
        TextView allProfessions = findViewById(R.id.all_professions); // форма для вывода всех IT профессий
        String[] prof = getResources().getStringArray(R.array.professions);
        String[] desc = getResources().getStringArray(R.array.descriptions);
        for (int i = 0; i < prof.length; i++)
        {
            SpannableString buf =  new SpannableString(prof[i]);
            buf.setSpan(new RelativeSizeSpan(2f), 0, prof[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allProfessions.append(buf);
            allProfessions.append("\n\n");
            buf = new SpannableString(desc[i]);
            buf.setSpan(new RelativeSizeSpan(0.5f), 0, desc[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allProfessions.append(buf);
            allProfessions.append("\n\n\n");
        }
    }

    // обработка события нажатия на кнопку "ИНФОРМАЦИЯ"
    public void ButtonAboutClick(View v)
    {
        setContentView(R.layout.about);
    }

    // выводит очередной вопрос и варианты ответа на экран
    // return текст вопроса и варианты ответа на экране, иначе - результаты опроса
    private void ShowQuestionAndChoices(int num)
    {
        int questionId = getApplicationContext().getResources().getIdentifier("q" + num, "array", getPackageName());
        if (questionId != 0)
        {
            String[] questionWithAnswers = getResources().getStringArray(questionId);
            question.setText(questionWithAnswers[0]);
            button1.setText(questionWithAnswers[1]);
            button2.setText(questionWithAnswers[2]);
            leftButton = Integer.parseInt(questionWithAnswers[3]);
            rightButton = Integer.parseInt(questionWithAnswers[4]);
        }
        else
        {
            setContentView(R.layout.results);
            TextView professions = findViewById(R.id.professions); // форма для вывода рекомендуемых IT профессий
            String[] prof = getResources().getStringArray(R.array.professions);
            String[] desc = getResources().getStringArray(R.array.descriptions);
            for (int i=0; recom_prof.length>i;i++)
            {
                if (recom_prof[i] != -1)
                {
                    SpannableString buf =  new SpannableString(prof[recom_prof[i]]);
                    buf.setSpan(new RelativeSizeSpan(2f), 0, prof[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    professions.append(buf);
                    professions.append("\n\n");
                    buf = new SpannableString(desc[recom_prof[i]]);
                    buf.setSpan(new RelativeSizeSpan(0.5f), 0, desc[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    professions.append(buf);
                    professions.append("\n\n\n");

                }
            }
            for (int j=0; recom_prof.length>j;j++)
            {
                recom_prof[j]=-1;
            }
        }
    }

    // обработка события нажатия на кнопку ответа 1
    public void Button1Click(View v)
    {
        curQuestionNum *= 2;
        if (leftButton!=-1) {
            first:
            {
                for (int i=0; recom_prof.length>i;i++) {
                    if (recom_prof[i] == -1) {
                        recom_prof[i] = leftButton;
                        break first;
                    }
                }
            }
        }
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку ответа 2
    public void Button2Click(View v)
    {
        curQuestionNum = 2 * curQuestionNum + 1;
        if (rightButton!=-1) {
            second:
            {
                for (int i=0; recom_prof.length>i;i++) {
                    if (recom_prof[i] == -1) {
                        recom_prof[i] = rightButton;
                        break second;
                    }
                }
            }
        }
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "МЕНЮ"
    public void ButtonMenuClick(View v)
    {
        curQuestionNum = 1;
        setContentView(R.layout.activity_main);
    }


}


