package com.lanbo.baby;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zgx on 17-4-20.
 */

public class TimeRecord {
    private TimerTask mTimerTask;
    private Timer mTimer;
    private final static long DURATION = 1000;
    private long mCurTime;
    private TimeTicker mTimeTicker;
    private Handler mHandler;
    private Runnable mOnTimeTickRunnable;

    public TimeRecord() {
        mCurTime = 0;
        initTimer();
        mHandler = new Handler();
        mOnTimeTickRunnable = new Runnable() {
            @Override
            public void run() {
                mTimeTicker.onTimeTick(mCurTime);
            }
        };
    }

    public long getTime() {
        return mCurTime;
    }

    private void initTimer() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mCurTime++;
                if (mTimeTicker != null) {
                    mHandler.post(mOnTimeTickRunnable);
                }
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }

    public void destroy() {
        stop();
        mTimeTicker = null;
        mOnTimeTickRunnable = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    public void setOnTimeTick(TimeTicker timeTick) {
        mTimeTicker = timeTick;
    }

    interface TimeTicker {
        public void onTimeTick(long time);
    }
}
