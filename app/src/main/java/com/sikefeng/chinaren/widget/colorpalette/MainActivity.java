package com.sikefeng.chinaren.widget.colorpalette;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sikefeng.chinaren.R;


public class MainActivity extends Activity {

    private View view;
    private ColorSelectDialog colorSelectDialog;
    private int lastColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        initView();
    }

    private void initView() {
        view = findViewById(R.id.view);
    }

    public void Color(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (colorSelectDialog == null) {
                    colorSelectDialog = new ColorSelectDialog(this);
                    colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
                        @Override
                        public void onSelectFinish(int color) {
                            lastColor=color;
                            MainActivity.this.view.setBackgroundColor(lastColor);
                        }
                    });
                }
                colorSelectDialog.setLastColor(lastColor);
                colorSelectDialog.show();
                break;
        }

    }


}
