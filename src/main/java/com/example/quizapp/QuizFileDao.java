package com.example.quizapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Dao ・・・ Data access object（各種データにアクセスするクラス）
public class QuizFileDao {

    // 再代入できない
    // 定数となる変数は（= FILE_PATH）は「アンダースコア」を付ける
    // フィールドに定数を作成するときは、"static final"と必ず書く
    private static final String FILE_PATH = "quizzes.txt";

    public void write(List<Quiz> quizzes) throws IOException {
        // 空のListを用意する
        List<String> lines = new ArrayList<>();
        for(Quiz quiz: quizzes) {
            lines.add(quiz.toString());
        }

        Path path = Paths.get(FILE_PATH);  // "quizzes.txt" を事前にPathというクラスにする必要あり

        // 第１引数 ・・・ 書き込み先のファイルパス（場所）
        // 第２引数 ・・・ 書き込むデータ List<String>
        Files.write(path, lines);
    }

    public List<Quiz> read() throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path);

        List<Quiz> quizzes = new ArrayList<>();
        for (String line: lines) {
            quizzes.add(Quiz.fromString(line));
        }

        return quizzes;
    }
}
