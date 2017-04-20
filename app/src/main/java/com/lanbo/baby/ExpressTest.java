package com.lanbo.baby;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgx on 17-4-20.
 */

public class ExpressTest {
    private List<Expression> mExpressions = new ArrayList<>();
    private int mIndex = 0;
    private int mTotalCount = 10;
    private boolean mStopped = false;

    public ExpressTest(int count) {
        mTotalCount = count;
    }

    public void start() {
        mExpressions.clear();
        for (int i = 0; i < mTotalCount; i++) {
            mExpressions.add(Expression.createExpression());
        }
        mIndex = 0;
    }

    public void stop() {
        mStopped = true;
    }

    public boolean isStopped() {
        return mStopped;
    }

    public boolean isFinished() {
        return mIndex >= mTotalCount;
    }

    public String getTitle() {
        if (mIndex < mTotalCount) {
            return "第 " + (mIndex + 1) + " 题，共 " + mTotalCount + " 题";
        } else {
            return "正确率：" + (rightNum() * 100 / mTotalCount) + "%";
        }
    }

    public Expression getCurExpr() {
        return mExpressions.get(mIndex);
    }

    public boolean nextExpression() {
        mIndex++;
        if (mIndex < mTotalCount) {
            return true;
        } else {
            return false;
        }
    }

    public int mRightNum;

    public int rightNum() {
        mRightNum = 0;
        for (int i = 0; i < mIndex; i++) {
            Expression e = mExpressions.get(i);
            if (e.isRightAnswer()) {
                mRightNum++;
            }
        }
        /*for (Expression e : mExpressions) {
            if (e.isRightAnswer()) {
                mRightNum++;
            }
        }*/
        return mRightNum;
    }

    public int getAnsweredNum() {
        return mIndex;
    }
}
