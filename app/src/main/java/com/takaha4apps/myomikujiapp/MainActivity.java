package com.takaha4apps.myomikujiapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private BgmPlayer bgm;

    private SoundPool mSoundPool;
    private int[] mSoundId = new int[4];
    private int[] mMusicId = new int[1];

    @Override
    protected void onResume() {
        super.onResume();
        //予め音声データを読み込む
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundId[0] = mSoundPool.load(getApplicationContext(), R.raw.select01, 0);
        mSoundId[1] = mSoundPool.load(getApplicationContext(), R.raw.select02, 0);
        mSoundId[2] = mSoundPool.load(getApplicationContext(), R.raw.select03, 0);
        mSoundId[3] = mSoundPool.load(getApplicationContext(), R.raw.select04, 0);
        //BGMの再生
        bgm.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //リリース
        mSoundPool.release();
        //BGMの停止
        bgm.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextViewの取得
        TextView scoreLabel = (TextView) findViewById(R.id.myTextView);
        TextView omikujiLabel = (TextView) findViewById(R.id.omikujiLabel);
        TextView omikujiButton = (TextView) findViewById(R.id.omikujiButton);
        TextView unnoyosaLabel = (TextView) findViewById(R.id.unnoyosaLabel);
        TextView unnoyosaLevelLabel = (TextView) findViewById(R.id.unnoyosaLevelLabel);


        //フォントの変更
        setFontType(scoreLabel);
        setFontType(omikujiButton);
        setFontType(omikujiLabel);
        setFontType(unnoyosaLabel);
        setFontType(unnoyosaLevelLabel);

        //プレイヤーの初期化
        this.bgm = new BgmPlayer(this);

    }

    public void getOmikuji(View view) {

        //TextViewの取得
        TextView tv = (TextView) findViewById(R.id.myTextView);
        String[] results = {
                "大吉",
                "中吉",
                "吉",
                "小吉",
                "凶",
                "大凶"
        };
        //乱数の生成
        Random randomGenerator = new Random();
        int num = randomGenerator.nextInt(results.length);//0-2
        //結果の表示
        tv.setText(results[num]);

        if (num == 0) {
            tv.setTextColor(Color.RED);
        } else {
            tv.setTextColor(Color.rgb(255, 255, 255));
        }
        //効果音再生
        PlaySound();
        //画像の更新
        ChangeMonsterIcon(num);
    }

    void ChangeMonsterIcon(int num) {
        //うんのよさ　ゾーマ級　とか
        //大吉 - ゾーマ 中吉 - バラモス 吉 - ドラキー　小吉 - バブルスライム 凶 - スライム 大凶 - 墓

        //うんのよさラベル更新
        TextView tv = (TextView) findViewById(R.id.unnoyosaLevelLabel);
        String[] monsters = {
                "ゾーマ",
                "バラモス",
                "ドラキー",
                "バブルスライム",
                "スライム",
                "墓"
        };
        //結果の表示
        tv.setText(monsters[num] + "レベル");
        //画像の更新
        switch (num) {
            case 0:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.monsterzoma);
                break;
            case 1:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.monsterbaramosu);
                break;
            case 2:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.monsterdoraki);
                break;
            case 3:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.monsterbabbleslime);
                break;
            case 4:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.monsterslime);
                break;
            case 5:
                ((ImageView) findViewById(R.id.monsterImageView)).setImageResource(R.drawable.dqhaka);
                break;
        }


    }

    void PlaySound() {
        //乱数の生成
        Random random = new Random();
        int tmp = random.nextInt(mSoundId.length);
        //ランダムで選択音を鳴らす
        mSoundPool.play(mSoundId[tmp], 1.0f, 1.0f, 0, 0, 1.0f);
    }

    //フォントタイプ指定
    //setFontType()
    public void setFontType(TextView txt) {
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "JF-Dot-K14-2004.ttf"));
    }
}
