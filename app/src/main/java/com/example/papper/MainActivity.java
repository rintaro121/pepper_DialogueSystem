package com.example.papper;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
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
        //　layoutで設定したボタンはonclickでボタンにする（メソッドの名前は同じにする）
        // layout上の文字列を変化させるにはidで紐づけて変化させるtextviewを使用する．

        QiSDK.register(this, this);

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
                .withTexts("あまのけん","いまいけん","おおつきけん","かねこけん","こうのけん","さいとうけん","ささせけん","しげのけん",
                        "すぎうらけん","すぎもとけん","たかだけん","てらおかけん","とおやまけん","はぎわらけん","ふじしろけん","まつたにけん","やまさきけん","やまなかけん","こんにちは")
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
                case "あまのけん":
                    return_str = heard_str + "と，ながのめい，で韻が踏めます";
                    break;
                case "いまいけん":
                    return_str = heard_str + "と，しがいせん，で韻が踏めます";
                    break;
                case "おおつきけん":
                    return_str = heard_str + "と，ドル紙幣，で韻が踏めます";
                    break;
                case "こうのけん":
                    return_str = heard_str + "と，オットセイ，で韻が踏めます";
                    break;
                case "さいとうけん":
                    return_str = heard_str + "と，あいふぉん てん，で韻が踏めます";
                    break;
                case "ささせけん":
                    return_str = heard_str + "と，わたべけん，で韻が踏めます";
                    break;
                case "すぎうらけん":
                    return_str = heard_str + "と，スキルがねぇ，で韻が踏めます";
                    break;
                case "たかだけん":
                    return_str = heard_str + "と，アンハサウェイ，で韻が踏めます";
                    break;
                case "てらおかけん":
                    return_str = heard_str + "と，エアジョーダン，で韻が踏めます";
                    break;
                case "はぎわらけん":
                    return_str = heard_str + "と，さいたまけん，で韻が踏めます";
                    break;
                case "まつたにけん":
                    return_str = heard_str + "と，やすだきねん，で韻が踏めます";
                    break;
                case "やまさきけん":
                    return_str = heard_str + "と，またらいねん，で韻が踏めます";
                    break;
                case "やまなかけん":
                    return_str = heard_str + "と，あたまがへん，で韻が踏めます．";
                    break;
                    
                case "こんにちは":
                    return_str = heard_str + "と，おうちじかん，で韻が踏めます";
                    break;
                default:
                    return_str = "";
            }

            Animation animation = AnimationBuilder.with(qiContext).withResources(R.raw.thinking_a001).build();
            Animate animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
            animate.run();

            if (return_str.equals("")){
                Say say = SayBuilder.with(qiContext).withText("すみません，おもいつきません").build();
                say.run();

                animation = AnimationBuilder.with(qiContext).withResources(R.raw.sad_a001).build();
                animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
                animate.run();

            }else{
                Say say = SayBuilder.with(qiContext).withText(return_str).build();
                say.run();

                animation = AnimationBuilder.with(qiContext).withResources(R.raw.left_hand_low_b001).build();
                animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
                animate.run();

            }
        }
        // https://qisdk.softbankrobotics.com/sdk/doc/pepper-sdk/ch4_api/conversation/reference/chat.html
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }
}
