package com.lanbo.baby;

import java.util.Locale;
import java.util.Random;

/**
 * Created by zgx on 17-4-20.
 */

public class Expression {
    private int mValue1, mValue2;
    private int mRightValue, mResultValue;
    private Expr mExpr;

    private enum Expr {
        ADD, SUB
    }

    private Expression() {
        mResultValue = -1;
        mRightValue = -1;
    }

    public boolean isAnswered() {
        return mResultValue >= 0 && mResultValue <= 9;
    }

    public boolean isRightAnswer() {
        return calculate() == mResultValue;
    }

    public void setResultValue(int value) {
        mResultValue = value;
    }

    private int calculate() {
        if (mRightValue == -1) {
            if (mExpr == Expr.ADD) {
                mRightValue = mValue1 + mValue2;
            } else {
                mRightValue = mValue1 - mValue2;
            }
        }
        return mRightValue;
    }

    public static Expression createExpression() {
        Expression expression = new Expression();


        Random random = new Random();
        int e = random.nextInt(2);
        expression.mExpr = e == 0 ? Expr.ADD : Expr.SUB;

        expression.mValue1 = random.nextInt(10);
        expression.mValue2 = random.nextInt(10);
        if (expression.mExpr == Expr.SUB) {
            if (expression.mValue1 < expression.mValue2) {
                expression.swapValue();
            }
        }
        if (expression.mExpr == Expr.ADD) {
            if (expression.mValue2 > 9 - expression.mValue1) {
                expression.mValue2 = random.nextInt(10 - expression.mValue1);
            }
        }

        return expression;
    }

    private void swapValue() {
        int v = mValue2;
        mValue2 = mValue1;
        mValue1 = v;
    }

    public String getExprStr() {
        if (mResultValue > -1) {
            if (mExpr == Expr.ADD) {
                return String.format(Locale.getDefault(), "%d + %d = %d", mValue1, mValue2, mResultValue);
            } else {
                return String.format(Locale.getDefault(), "%d - %d = %d", mValue1, mValue2, mResultValue);
            }
        } else {
            if (mExpr == Expr.ADD) {
                return String.format(Locale.getDefault(), "%d + %d = ", mValue1, mValue2);
            } else {
                return String.format(Locale.getDefault(), "%d - %d = ", mValue1, mValue2);
            }
        }
    }
}
