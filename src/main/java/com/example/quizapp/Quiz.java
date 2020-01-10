package com.example.quizapp;

import org.apache.coyote.http11.filters.SavedRequestInputFilter;

public class Quiz {

    /**
     * 問題文
     */
    private  String question;

    /**
     * クイズの正解
     */
    private boolean answer;

    public Quiz(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        // 条件演算子
        String marubatsu = answer ? "○" : "×";
//        String marubatsu;
//        if (answer) {
//            marubatsu = "○";
//        } else {
//            marubatsu = "×";
//        }
        return question + " " + marubatsu;
    }

    // Quizインスタンスを作るためのメソッド
    // "static"はクラスに属している「クラスメソッド」と呼ばれる
    public static Quiz fromString(String line) {
        // 問題を取り出す
        String question = line.substring(0, line.length() - 2);
        // 答えを取り出す
        boolean answer = line.endsWith("○");

        return new Quiz(question, answer);
    }
}
