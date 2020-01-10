package com.example.quizapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("page")
public class QuizAppController {
    private List<Quiz> quizzes = new ArrayList<>();
    private QuizFileDao quizFileDao = new QuizFileDao();

    @GetMapping("/quiz")
    // 最後のreturnで"quizのhtmlファイル"を戻す為、戻り値をStringとする
    public String quiz(Model model) {
        int index = new Random().nextInt(quizzes.size()); // 乱数: 引数が3の場合 3未満(0~2)

        // モデルに"quizzes.get(index)"をセットする
        model.addAttribute("quiz", quizzes.get(index));
        return "quiz";
    }

    @GetMapping("/show")
    // Modelインスタンスに、quizzesフィールドをセットする
    public String show(Model model) {
        model.addAttribute("quizzes", quizzes);
        return "list";
    }

    @PostMapping("/create")
    public String create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question, answer);
        quizzes.add(quiz);

        return "redirect:/page/show";
    }

    @GetMapping("/check")
    // 画面に正解・不正解を返すために、引数にmodelを追加
    public String check(Model model, @RequestParam String question, @RequestParam boolean answer) {
        // 回答が正しいかどうかチェックして、結果を返却する
        // 指定されたquestionを登録済みのクイズから検索する
        for (Quiz quiz : quizzes) {
            // もしクイズが見つかったら
            if (quiz.getQuestion().equals(question)) {
                // 正解の結果が「○か×」を"answer.html"で表示するために、quizをモデルにセットする
                model.addAttribute("quiz", quiz);
                // 登録されているanswerと回答として渡ってきたanswerが一致している場合
                if (quiz.isAnswer() == answer) {
                    // 「正解」とセットする
                    model.addAttribute("result", "正解!");
                } else {
                    // もし一致していなければ「不正解」とセットする
                    model.addAttribute("result", "不正解!");
                }
            }
        }
        return "answer";
    }

    @PostMapping("/save")
    // ファイル保存に成功したかを返すために、戻り値をStringとしている
    public String save(RedirectAttributes attributes) {
        try {
            quizFileDao.write(quizzes);
            attributes.addFlashAttribute("successMessage","ファイルに保存しました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました");
        }

        return "redirect:/page/show";
    }

    @GetMapping("/load")
    // 戻り値をStringとする理由は、上記/saveと同様
    public String load(RedirectAttributes attributes) {
        try {
            quizzes = quizFileDao.read();
            attributes.addFlashAttribute("successMessage","ファイルを読み込みました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの読み込みに失敗しました");
        }

        return "redirect:/page/show";
    }
}