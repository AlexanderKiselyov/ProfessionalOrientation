package com.icst.professionalOrientation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    private TextView question; // поле с вопросом
    private Button button1; // первая кнопка (левая)
    private Button button2; // вторая кнопка (правая)
    private int curQuestionNum = 1; // номер текущего вопроса

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = (TextView)findViewById(R.id.question);
        button1 = (Button)findViewById(R.id.firstButton);
        button2 = (Button)findViewById(R.id.secondButton);

        ShowQuestionAndChoices(curQuestionNum);
    }

    // выводит очередной вопрос и варианты ответа на экран
    // return текст вопроса и варианты ответа на экране, иначе - результаты опроса
    private void ShowQuestionAndChoices(int num)
    {
        String curQuestionText = CurQuestionText(num);
        if (curQuestionText != "")
        {
            String curAnswer1Text = CurAnswerText(2 * num);
            String curAnswer2Text = CurAnswerText(2 * num + 1);
            question.setText(curQuestionText);
            button1.setText(curAnswer1Text);
            button2.setText(curAnswer2Text);
        }
    }

    // ищет текущий вопрос по номеру
    // return текст текущего вопроса, если есть, иначе - пустая строка
    private String CurQuestionText(int num)
    {
        String questionText = "";
        int questionId = getApplicationContext().getResources().getIdentifier("q" + num, "string", "com.icst.professionalOrientation");
        if (questionId != 0)
        {
            questionText = getResources().getString(questionId);
        }
        return questionText;
    }

    // ищет текущий вариант ответа по номеру
    // return текст текущего варианта ответа, если есть, иначе - пустая строка
    private String CurAnswerText(int num)
    {
        int answerId = getResources().getIdentifier("a" + num, "string", "com.icst.professionalOrientation");
        String answerText = getApplicationContext().getResources().getString(answerId);
        return answerText;
    }

    // обработка события нажатия на кнопку 1
    public void Button1Click(View v)
    {
        curQuestionNum *= 2;
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку 2
    public void Button2Click(View v)
    {
        curQuestionNum = 2 * curQuestionNum + 1;
        ShowQuestionAndChoices(curQuestionNum);
    }
}
