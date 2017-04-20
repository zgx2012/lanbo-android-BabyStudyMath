package com.lanbo.baby;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mExprTv, mTimeTv;
    private ImageView mResultImage;
    private static final long DURATION = 15000;
    private ExpressTest mTest;
    private static final int COUNT = 10;
    private TimeRecord mTimeRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExprTv = (TextView) findViewById(R.id.tv_expr);
        mTimeTv = (TextView) findViewById(R.id.tv_time);
        mResultImage = (ImageView) findViewById(R.id.image_result);

        mTest = new ExpressTest(COUNT);
        mTest.start();

        mTimeRecord = new TimeRecord();
        mTimeRecord.setOnTimeTick(new TimeRecord.TimeTicker() {
            @Override
            public void onTimeTick(long time) {
                mTimeTv.setText(TimeUtil.formatTime(time));
            }
        });

        renderTitle();
        renderExpr();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeRecord.destroy();
    }

    private void renderTitle() {
        setTitle(mTest.getTitle());
    }

    private void renderExpr() {
        Expression expression = mTest.getCurExpr();
        if (expression != null) {
            mExprTv.setText(expression.getExprStr());
        } else {
            mExprTv.setText(null);
        }
    }

    private void renderResult() {
        Expression expression = mTest.getCurExpr();
        if (expression != null && expression.isAnswered()) {
            if (expression.isRightAnswer()) {
                mResultImage.setImageResource(R.mipmap.ic_right);
            } else {
                mResultImage.setImageResource(R.mipmap.ic_wrong);
            }
        } else {
            mResultImage.setImageDrawable(null);
        }
    }

    public void onClickYes(View view) {
        if (mTest.isFinished()) {
            String str = String.format(Locale.getDefault(), "用时 %s，正确率 %d%%", TimeUtil.toTimeStr(mTimeRecord.getTime()), mTest.rightNum() * 100 / COUNT);
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            return;
        }
        Expression expression = mTest.getCurExpr();

        renderResult();
        if (expression != null && expression.isAnswered()) {
            // delay 0.1 second and start next expression
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mResultImage.setImageDrawable(null);
                    boolean hasNext = mTest.nextExpression();
                    if (hasNext) {
                        renderTitle();
                        renderExpr();
                    } else {
                        mTimeRecord.stop();
                        String str = String.format(Locale.getDefault(), "用时 %s，正确率 %d%%", TimeUtil.toTimeStr(mTimeRecord.getTime()), mTest.rightNum() * 100 / COUNT);
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                    }
                }
            }, 100);
        }
    }

    Handler mHandler = new Handler();

    public void onClickStop(View view) {
        mTest.stop();
        mTimeRecord.stop();
        String str = String.format(Locale.getDefault(), "用时 %s，作答%d题，答对%d题", TimeUtil.toTimeStr(mTimeRecord.getTime()), mTest.getAnsweredNum(), mTest.rightNum());
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }

    public void onClickNum(View view) {
        if (mTest.isStopped()) return;
        int id = view.getId();
        if (id == R.id.btn_stop) {
            onClickStop(view);
            return;
        } else if (id == R.id.btn_yes) {
            onClickYes(view);
            return;
        }

        int value = -1;
        switch (id) {
            case R.id.btn_num0:
                value = 0;
                break;
            case R.id.btn_num1:
                value = 1;
                break;
            case R.id.btn_num2:
                value = 2;
                break;
            case R.id.btn_num3:
                value = 3;
                break;
            case R.id.btn_num4:
                value = 4;
                break;
            case R.id.btn_num5:
                value = 5;
                break;
            case R.id.btn_num6:
                value = 6;
                break;
            case R.id.btn_num7:
                value = 7;
                break;
            case R.id.btn_num8:
                value = 8;
                break;
            case R.id.btn_num9:
                value = 9;
                break;
        }
        Expression expression = mTest.getCurExpr();
        if (expression != null) {
            expression.setResultValue(value);
        }
        renderExpr();
    }
}
