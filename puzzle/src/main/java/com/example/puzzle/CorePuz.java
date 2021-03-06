package com.example.puzzle;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CorePuz extends AppCompatActivity {

    private final float FRAME_BUFFER_WIDTH = 240;
    private final float FRAME_BUFFER_HEIGHT = 135;

    private LoopPuz loopPuz;
    private GraphicsPuz graphicsPuz;
    private ScenePuz scenePuz;
    private TouchListenerPuz touchListenerPuz;

    private AudioPuz audioPuz;

    private Display display;
    private Point sizeDisplay;
    private Bitmap frameBuffer;

    private float sceneWidth;
    private float sceneHeight;

    private boolean stateOnPause;
    private boolean stateOnResume;

    //файл настроек / сохранений
    private SharedPreferences sharedPreferences;

    private final String SETTINGS = "settings";

    //реклама
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE); //обращаемся к нашему файлу с сохранениями

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //отключаем спящий режим для приложения

        sizeDisplay = new Point();

        //получаем размер экрана
        display = getWindowManager().getDefaultDisplay();
        display.getSize(sizeDisplay);

        frameBuffer = Bitmap.createBitmap((int) FRAME_BUFFER_WIDTH, (int) FRAME_BUFFER_HEIGHT, Bitmap.Config.ARGB_8888);
        sceneWidth = FRAME_BUFFER_WIDTH / sizeDisplay.x;
        sceneHeight = FRAME_BUFFER_HEIGHT / sizeDisplay.y;

        loopPuz = new LoopPuz(this, frameBuffer);
        graphicsPuz = new GraphicsPuz(getAssets(), frameBuffer);
        touchListenerPuz = new TouchListenerPuz(loopPuz, sceneWidth, sceneHeight);

        audioPuz = new AudioPuz(this);

        scenePuz = getStartScene();

        stateOnPause = false;
        stateOnResume = false;

        setContentView(loopPuz);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("id вашей рекламы");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public CorePuz() {

    }

    public void onResume() {
        super.onResume();
        scenePuz.resume();
        loopPuz.startGame();
    }

    public void onPause() {
        super.onPause();
        scenePuz.pause();
        loopPuz.stopGame();
        stateOnPause = true;

        if (isFinishing()) {
            scenePuz.dispose();
        }
    }

    public AudioPuz getAudioPuz() {
        return audioPuz;
    }

    public TouchListenerPuz getTouchListenerPuz() {
        return touchListenerPuz;
    }

    public GraphicsPuz getGraphicsPuz() {
        return graphicsPuz;
    }

    public ScenePuz getCurrentScene() {
        return scenePuz;
    }

    public ScenePuz getStartScene() {
        return scenePuz;
    }

    public InterstitialAd getmInterstitialAd() {
        return mInterstitialAd;
    }

    public void setScene(ScenePuz scenePuz) {
        if (scenePuz == null) {
            throw new IllegalArgumentException("Неудалось загрузить сцену");
        }
        this.scenePuz.pause();
        this.scenePuz.dispose();
        scenePuz.resume();
        scenePuz.update();
        this.scenePuz = scenePuz;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
