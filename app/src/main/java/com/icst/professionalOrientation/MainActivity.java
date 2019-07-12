package com.icst.professionalOrientation;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.Spanned;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import java.util.Random;
import android.content.res.Configuration;
import android.graphics.Typeface;

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
    private boolean ifTestBegin; // проверка на то, что тест запущен и идет
    private boolean ifMainScreen; // проверка на то, что пользователь находится на стартовом экране
    public Typeface face; // переменная для смены шрифта

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        face = Typeface.createFromAsset(getAssets(), "fonts/newFont.ttf");
        recom_prof = new int[3];
        leftButton = -1;
        rightButton = -1;
        RefreshRecomProf();
        lastRobot = 0;
        ifTestBegin = false;
        ifMainScreen = true;
        setContentView(R.layout.activity_main);
        //changeFont();
    }

    // обработка события нажатия на кнопку "НАЧАТЬ ТЕСТ"
    public void ButtonStartClick(View v)
    {
        setContentView(R.layout.test);
        ifTestBegin = true;
        ifMainScreen = false;
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
        ifMainScreen = false;
        ConclusionProfessions(false);
    }

    // обработка события нажатия на кнопку "ИНФОРМАЦИЯ"
    public void ButtonAboutClick(View v)
    {
        ifMainScreen = false;
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
            ShowRobot();
        }
        else
        {
            ConclusionProfessions(true);
            RefreshRecomProf();
            ifTestBegin = false;
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
        ifTestBegin = false;
        ifMainScreen = true;
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
        String[] profFourth = getResources().getStringArray(R.array.profFourth);
        List<Profession> professions = new ArrayList<>();
        if (recomendProf)
        {
            for (int i = 0; recom_prof.length > i; i++)
            {
                if (recom_prof[i] != -1)
                {
                    /*SpannableString[] buf = new SpannableString[5];
                    buf[0] =  new SpannableString(prof[recom_prof[i]] + "\n\n");
                    buf[0].setSpan(new RelativeSizeSpan(2f), 0, prof[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[1] = new SpannableString(desc[recom_prof[i]]);
                    buf[1].setSpan(new RelativeSizeSpan(1.5f), 0, desc[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[2] = new SpannableString(profFirst[recom_prof[i]]);
                    buf[2].setSpan(new RelativeSizeSpan(1.5f), 0, profFirst[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[3] =  new SpannableString(profSecond[recom_prof[i]]);
                    buf[3].setSpan(new RelativeSizeSpan(1.5f), 0, profSecond[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    buf[4] =  new SpannableString(profThird[recom_prof[i]]);
                    buf[4].setSpan(new RelativeSizeSpan(1.5f), 0, profThird[recom_prof[i]].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    professions.add(new Profession(buf[0], buf[1], buf[2], buf[3], buf[4]));*/
                    professions.add(new Profession(prof[recom_prof[i]], desc[recom_prof[i]], profFirst[recom_prof[i]],
                            profSecond[recom_prof[i]], profThird[recom_prof[i]], profFourth[recom_prof[i]]));
                }
            }
        }
        else
        {
            for (int i = 0; i < prof.length; i++)
            {
                /*SpannableString[] buf = new SpannableString[5];
                buf[0] =  new SpannableString(prof[i] + "\n\n");
                buf[0].setSpan(new RelativeSizeSpan(2f), 0, prof[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[1] = new SpannableString(desc[i]);
                buf[1].setSpan(new RelativeSizeSpan(1.5f), 0, desc[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[2] = new SpannableString(profFirst[i]);
                buf[2].setSpan(new RelativeSizeSpan(1.5f), 0, profFirst[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[3] =  new SpannableString(profSecond[i]);
                buf[3].setSpan(new RelativeSizeSpan(1.5f), 0, profSecond[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                buf[4] =  new SpannableString(profThird[i]);
                buf[4].setSpan(new RelativeSizeSpan(1.5f), 0, profThird[i].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                professions.add(new Profession(buf[0], buf[1], buf[2], buf[3], buf[4]));*/
                professions.add(new Profession(prof[i], desc[i], profFirst[i], profSecond[i], profThird[i], profFourth[i]));
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

    // меняет шрифт всех текстовых элементов
    private void changeFont()
    {
        TextView description = findViewById(R.id.oneProfBriefDesc);
        TextView profFirst = findViewById(R.id.content_desc);
        TextView profSecond = findViewById(R.id.content_competence);
        TextView profThird = findViewById(R.id.content_salary);
        TextView profFourth= findViewById(R.id.content_links);

        description.setTypeface(face);
        profFirst.setTypeface(face);
        profSecond.setTypeface(face);
        profThird.setTypeface(face);
        profFourth.setTypeface(face);
    }

    // выполняет свою работу ТОЛЬКО во время прохождения теста или нахождения на стартовом экране, меняет расположение элементов на экране
    // в зависимости от ориентации экрана для более удобного прохождения теста или для более удобного представления стартового меню
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if ((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) && ifTestBegin)
        {
            setContentView(R.layout.test);
            int prog = 1;
            int buf = curQuestionNum;
            while (buf / 2 != 0)
            {
                buf /= 2;
                prog++;
            }
            progress = findViewById(R.id.progress);
            progress.setProgress(prog);
            int questionId = getApplicationContext().getResources().getIdentifier("q" + curQuestionNum, "array", getPackageName());
            String[] questionWithAnswers = getResources().getStringArray(questionId);
            question = findViewById(R.id.question);
            button1 = findViewById(R.id.firstButton);
            button2 = findViewById(R.id.secondButton);
            question.setText(questionWithAnswers[0]);
            button1.setText(questionWithAnswers[1]);
            button2.setText(questionWithAnswers[2]);
            ImageView robot = findViewById(R.id.robot);
            robot.setBackgroundResource(getResources().getIdentifier("robot" + lastRobot,"drawable", getPackageName()));
        }
        if ((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) && ifMainScreen)
        {
            setContentView(R.layout.activity_main);
        }
    }

    // обработка события нажатия на аппаратную кнопку "НАЗАД"
    @Override
    public void onBackPressed()
    {
        if (ifTestBegin && curQuestionNum != 1)
        {
            if (curQuestionNum % 2 != 0)
            {
                curQuestionNum -= 1;
            }
            curQuestionNum /= 2;
            for (int i = 0; recom_prof.length > i; i++)
            {
                if (recom_prof[i] == -1)
                {
                    if (i != 0)
                    {
                        recom_prof[i - 1] = -1;
                    }
                    break;
                }
            }
            progress.incrementProgressBy(-1);
            ShowQuestionAndChoices(curQuestionNum);
        }
        else
        {
            RefreshRecomProf();
            curQuestionNum = 1;
            ifTestBegin = false;
            ifMainScreen = true;
            setContentView(R.layout.activity_main);
        }
    }
}