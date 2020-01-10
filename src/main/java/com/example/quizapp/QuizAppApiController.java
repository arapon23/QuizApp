package com.example.quizapp;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class QuizAppApiController {
    private List<Quiz> quizzes = new ArrayList<>();
    private QuizFileDao quizFileDao = new QuizFileDao();

    @GetMapping("/quiz")
    public Quiz quiz() {
        int index = new Random().nextInt(quizzes.size()); // 乱数: 引数が3の場合 3未満(0~2)

        return quizzes.get(index);
    }

    @GetMapping("/show")
    public List<Quiz> show() {
        return quizzes;
    }

    @PostMapping("/create")
    public void create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question, answer);
        quizzes.add(quiz);
    }

    @GetMapping("/check")
    public String check(@RequestParam String question, @RequestParam boolean answer) {
        // 回答が正しいかどうかチェックして、結果を返却する
        // 指定されたquestionを登録済みのクイズから検索する
        for (Quiz quiz : quizzes) {
            // もしクイズが見つかったら
            if (quiz.getQuestion().equals(question)) {
                // 登録されているanswerと回答として渡ってきたanswerが一致している場合
                if (quiz.isAnswer() == answer) {
                    // 「正解」と返却する
                    return "正解!";
                } else {
                    // もし一致していなければ「不正解」と返却する
                    return "不正解";
                }
            }
        }
        // クイズがもし見つからなかった場合は、「問題がありません」と返却する
        return "問題がありません";
    }

    @PostMapping("/save")
    public String save() {
        try {
            quizFileDao.write(quizzes);
            return "ファイルに保存しました";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの保存に失敗しました";
        }
    }

    @GetMapping("/load")
    public String load() {
        try {
            quizzes = quizFileDao.read();
            return "ファイルを読み込みました";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの読み込みに失敗しました";
        }
    }
}