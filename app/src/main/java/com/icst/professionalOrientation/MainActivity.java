package com.icst.professionalOrientation;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.Spanned;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import java.util.Random;
import android.content.pm.ActivityInfo;

public class MainActivity extends AppCompatActivity
{
    private TextView question; // поле с вопросом
    private Button button1; // первая кнопка (левая)
    private Button button2; // вторая кнопка (правая)
    private int curQuestionNum; // номер текущего вопроса
    private int[] recom_prof; // список рекомендуемых профессий
    private int leftButton; // номер профессии, которая добавится при нажатии левой кнопки
    private int rightButton; // номер профессии, которая добавится при нажатии правой кнопки
    private ProgressBar progress; // прогресс пройденных вопросов
    private int lastRobot; // последний выведенный на экран робот во время теста

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recom_prof = new int[3];
        leftButton = -1;
        rightButton = -1;
        RefreshRecomProf();
        lastRobot = 0;
        getSupportActionBar().hide();
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
        progress = findViewById(R.id.progress);
        progress.incrementProgressBy(1);
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "ПРОФЕССИИ"
    public void ButtonProfessionsClick(View v)
    {
        ConclusionProfessions(false);
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
            //SpannableString buf =  new SpannableString(questionWithAnswers[0]);
            //buf.setSpan(new RelativeSizeSpan(2f), 0, questionWithAnswers[0].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            question.setText(questionWithAnswers[0]);
            button1.setText(questionWithAnswers[1]);
            button2.setText(questionWithAnswers[2]);
            leftButton = Integer.parseInt(questionWithAnswers[3]);
            rightButton = Integer.parseInt(questionWithAnswers[4]);
            ShowRobot();
        }
        else
        {
            ConclusionProfessions(true);
            RefreshRecomProf();
        }
    }

    // обработка события нажатия на кнопку ответа 1
    public void Button1Click(View v)
    {
        curQuestionNum *= 2;
        InputRecomenProf(leftButton);
        progress.incrementProgressBy(1);
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку ответа 2
    public void Button2Click(View v)
    {
        curQuestionNum = 2 * curQuestionNum + 1;
        InputRecomenProf(rightButton);
        progress.incrementProgressBy(1);
        ShowQuestionAndChoices(curQuestionNum);
    }

    // обработка события нажатия на кнопку "МЕНЮ"
    public void ButtonMenuClick(View v)
    {
        RefreshRecomProf();
        curQuestionNum = 1;
        setContentView(R.layout.activity_main);
    }

    // выводит на экран профессии (те, которые есть в массиве рекомендуемых профессий recom_prof, если recomendProf = true, иначе - все профессии)
    private void ConclusionProfessions(boolean recomendProf)
    {
        if (recomendProf)
        {
            setContentView(R.layout.results);
        }
        else
        {
            setContentView(R.layout.all_professions);
        }
        RecyclerView recyclerView = findViewById(R.id.oneProf);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] prof = getResources().getStringArray(R.array.professions);
        String[] desc = getResources().getStringArray(R.array.briefDescriptions);
        String[] profFirst = getResources().getStringArray(R.array.profFirst);
        String[] profSecond = getResources().getStringArray(R.array.profSecond);
        String[] profThird = getResources().getStringArray(R.array.profThird);
        List<Profession> professions = new ArrayList<>();
        if (recomendProf)
        {
            for (int i = 0; recom_prof.length > i; i++)
            {
                if (recom_prof[i] != -1)
                {
                    SpannableString[] buf = new SpannableString[5];
                    buf[0] =  new SpannableString(prof[recom_prof[i]] + "\n\n");
                    buf[0].setSpan(new RelativeSizeSpan(2f), 0, prof[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[1] = new SpannableString(desc[recom_prof[i]]);
                    buf[1].setSpan(new RelativeSizeSpan(1f), 0, desc[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[2] = new SpannableString(profFirst[recom_prof[i]]);
                    buf[2].setSpan(new RelativeSizeSpan(1f), 0, profFirst[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[3] =  new SpannableString(profSecond[recom_prof[i]]);
                    buf[3].setSpan(new RelativeSizeSpan(1f), 0, profSecond[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[4] =  new SpannableString(profThird[recom_prof[i]]);
                    buf[4].setSpan(new RelativeSizeSpan(1f), 0, profThird[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    professions.add(new Profession(buf[0], buf[1], buf[2], buf[3], buf[4]));
                }
            }
        }
        else
        {
            for (int i = 0; i < prof.length; i++)
            {
                SpannableString[] buf = new SpannableString[5];
                buf[0] =  new SpannableString(prof[i] + "\n\n");
                buf[0].setSpan(new RelativeSizeSpan(2f), 0, prof[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[1] = new SpannableString(desc[i]);
                buf[1].setSpan(new RelativeSizeSpan(1f), 0, desc[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[2] = new SpannableString(profFirst[i]);
                buf[2].setSpan(new RelativeSizeSpan(1f), 0, profFirst[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[3] =  new SpannableString(profSecond[i]);
                buf[3].setSpan(new RelativeSizeSpan(1f), 0, profSecond[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[4] =  new SpannableString(profThird[i]);
                buf[4].setSpan(new RelativeSizeSpan(1f), 0, profThird[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                professions.add(new Profession(buf[0], buf[1], buf[2], buf[3], buf[4]));
            }
        }
        ProfAdapter mAdapter = new ProfAdapter(this, professions);
        recyclerView.setAdapter(mAdapter);
    }

    // ввод в список рекомендованных профессий от нажатия кнопки
    private void InputRecomenProf(int Button)
    {
        if (Button != -1)
        {
            for (int i = 0; recom_prof.length > i; i++)
            {
                if (recom_prof[i] == -1)
                {
                    recom_prof[i] = Button;
                    return;
                }
            }
        }
    }

    // очищение списка рекомендуемых профессий
    private void RefreshRecomProf()
    {
        for (int i = 0; recom_prof.length > i; i++)
        {
            recom_prof[i] = -1;
        }
    }

    // вывод картинки робота во время теста
    private void ShowRobot()
    {
        ImageView robot = findViewById(R.id.robot);
        Random random = new Random();
        int robotNum = random.nextInt(9) + 1;
        while (robotNum == lastRobot)
        {
            robotNum = random.nextInt(9) + 1;
        }
        robot.setBackgroundResource(getResources().getIdentifier("robot" + robotNum,"drawable", getPackageName()));
        lastRobot = robotNum;
    }
}