package com.icst.professionalOrientation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.R.xml;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends AppCompatActivity {

    private TextView question; // поле с вопросом
    private Button button1; // первая кнопка (левая)
    private Button button2; // вторая кнопка (правая)
    private int curQuestionNum; // номер текущего вопроса
    private TextView professions; // форма для вывода рекомендуемых IT профессий
    private TextView allProfessions; // форма для вывода всех IT профессий

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // обработка события нажатия на кнопку "НАЧАТЬ ТЕСТ"
    public void ButtonStartClick(View v)
    {
        setContentView(R.layout.test);
        question = (TextView) findViewById(R.id.question);
        button1 = (Button) findViewById(R.id.firstButton);
        button2 = (Button) findViewById(R.id.secondButton);
        curQuestionNum = 1;
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "СПЕЦИАЛЬНОСТИ"
    public void ButtonProfessionsClick(View v)
    {
        setContentView(R.layout.all_professions);
        allProfessions = (TextView) findViewById(R.id.all_professions);
        String[] prof = getResources().getStringArray(R.array.professions);
        String[] desc = getResources().getStringArray(R.array.description);
        for (int i = 0; i < prof.length; i++)
        {
            allProfessions.append(prof[i]);
            allProfessions.append("\n\n");
            allProfessions.append(desc[i]);
            allProfessions.append("\n\n\n");
        }
    }

    // выводит очередной вопрос и варианты ответа на экран
    // return текст вопроса и варианты ответа на экране, иначе - результаты опроса
    private void ShowQuestionAndChoices(int num) {
        String curQuestionText = CurQuestionText(num);
        if (curQuestionText != "")
        {
            String curAnswer1Text = CurAnswerText(2 * num);
            String curAnswer2Text = CurAnswerText(2 * num + 1);
            question.setText(curQuestionText);
            button1.setText(curAnswer1Text);
            button2.setText(curAnswer2Text);
        }
        else
        {
            setContentView(R.layout.results);
            professions = (TextView) findViewById(R.id.professions);
            int[] recomProf = RecomProf(num);
            String[] prof = getResources().getStringArray(R.array.professions);
            String[] desc = getResources().getStringArray(R.array.description);
            for (int i = 0; i < recomProf.length; i++)
            {
                if (recomProf[i] != 0)
                {
                    professions.append(prof[recomProf[i] - 1]);
                    professions.append("\n\n");
                    professions.append(desc[recomProf[i] - 1]);
                    professions.append("\n\n\n");
                }
            }
        }
    }

    // ищет текущий вопрос по номеру
    // return текст текущего вопроса, если есть, иначе - пустая строка
    private String CurQuestionText(int num) {
        String questionText = "";
        int questionId = getApplicationContext().getResources().getIdentifier("q" + num, "string", "com.icst.professionalOrientation");
        if (questionId != 0) {
            questionText = getResources().getString(questionId);
        }
        return questionText;
    }

    // ищет текущий вариант ответа по номеру
    // return текст текущего варианта ответа (он имеется, т.к. есть текст вопроса, к которому он принадлежит)
    private String CurAnswerText(int num) {
        int answerId = getResources().getIdentifier("a" + num, "string", "com.icst.professionalOrientation");
        String answerText = getApplicationContext().getResources().getString(answerId);
        return answerText;
    }

    // обработка события нажатия на кнопку 1
    public void Button1Click(View v) {
        curQuestionNum *= 2;
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку 2
    public void Button2Click(View v) {
        curQuestionNum = 2 * curQuestionNum + 1;
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "МЕНЮ"
    public void ButtonMenuClick(View v) {
        curQuestionNum = 1;
        setContentView(R.layout.activity_main);
    }

    // находит список рекомендуемых профессий по номеру последнего ответа
    // return массив номеров профессий для вывода
    private int[] RecomProf(int num)
    {
        int[] prof = new int[3];
        for (int i = 0; i < prof.length; i++) {
            prof[i] = 0;
        }
        switch (num) {
            case 8:
                prof[0] = 8;
                prof[1] = 3;
                prof[2] = 2;
                break;
            case 9:
                prof[0] = 8;
                prof[1] = 2;
                break;
            case 10:
                prof[0] = 3;
                prof[1] = 8;
                break;
            case 11:
                prof[0] = 5;
                break;
            case 12:
                prof[0] = 4;
                prof[1] = 9;
                break;
            case 13:
                prof[0] = 11;
                break;
            case 14:
                prof[0] = 10;
                break;
            case 15:
                prof[0] = 1;
                prof[1] = 3;
                break;
        }
        return prof;
    }
}
