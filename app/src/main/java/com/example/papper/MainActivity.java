package com.example.papper;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 上の行でres/layout/activait_main.xmlを基にレイアウトの作成を行っている
        //layoutで設定したボタンはonclickでボタンにする（メソッドの名前は同じにする）
        // layout上の文字列を変化させるにはidで紐づけて変化させるtextviewを使用する．

        //QiSDK.register(this, this);
        QiSDK.register(this, this);

        //mediaPlayer = MediaPlayer.create(this, R.raw.test);
        //mediaPlayer.start();

        //mediaPlayer.start(); // no need to call prepare(); create() does that for you

        //mediaPlayer.release();
        //mediaPlayer = null;
    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);

        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;

    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        Log.i("MyTag","onRobotFocusGained");

        /*
        Say say = SayBuilder.with(qiContext).withText("音楽を流します").build();
        say.run();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.test);
        mediaPlayer.start();
         */

        PhraseSet phraseSet = PhraseSetBuilder.with(qiContext)
                .withTexts("いまい","いまいけん","すぎうらけん","こんにちは")
                .build();


        while (true){
            Listen listen = ListenBuilder.with(qiContext)
                    .withPhraseSet(phraseSet)
                    .build();

            ListenResult listenResult = listen.run();
            Log.i("MyTag", "Heard phrase: " + listenResult.getHeardPhrase().getText());


            String heard_str = listenResult.getHeardPhrase().getText();
            String return_str = "";

            switch (heard_str){
                case "いまいけん":
                    return_str = heard_str + "と，しがいせん，で韻が踏めます";
                    break;
                case "こんにちは":
                    return_str = heard_str + "と，おうちじかん，で韻が踏めます";
                    break;
                default:
                    return_str = "";
            }

            if (return_str.equals("")){
                Say say = SayBuilder.with(qiContext).withText("すみません，よくわかりません").build();
                say.run();

            }else{
                Say say = SayBuilder.with(qiContext).withText(return_str).build();
                say.run();
            }
        }




        //Say say = SayBuilder.with(qiContext).withText(listenResult.getHeardPhrase().getText() + "と，しがいせん，で韻が踏めます").build();
        //say.run();

        // https://qisdk.softbankrobotics.com/sdk/doc/pepper-sdk/ch4_api/conversation/reference/chat.html

    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }
}
