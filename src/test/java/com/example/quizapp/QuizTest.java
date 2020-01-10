package com.example.quizapp;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuizTest {

    @Test
    public void toStringWhenMaru() {
        Quiz quiz = new Quiz("問題文", true);
        assertThat(quiz.toString(), is("問題文 ○"));
    }

    @Test
    public void toStringWhenBatsu() {
        Quiz quiz = new Quiz("問題文", false);
        assertThat(quiz.toString(), is("問題文 ×"));
    }

    @Test
    public void fromStringMaru() {
        // テストしたい文字列をlineとして書く
        String line = "問題文1 ○";
        // "static"(クラスメソッド)はインスタンスを生成しない状態でも呼び出すことができる
        Quiz quiz = Quiz.fromString(line);

        assertThat(quiz.getQuestion(), is("問題文1"));
        assertThat(quiz.isAnswer(), is(true));
    }


    @Test
    public void fromStringBatsu() {
        // テストしたい文字列をlineとして書く
        String line = "問題文1 ×";
        Quiz quiz = Quiz.fromString(line);

        assertThat(quiz.getQuestion(), is("問題文1"));
        assertThat(quiz.isAnswer(), is(false));
    }
}
