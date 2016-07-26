package com.pedroaugusto.cobrinha;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity {
    private GameView gm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(gm = new GameView(this));
    }

    @Override
    public void onBackPressed() {
        gm.surfaceDestroyed(null);
        super.onBackPressed();
    }
}
