package by.comatoznik.Project1.controllers;

import by.comatoznik.Project1.entities.Answer;
import by.comatoznik.Project1.entities.Ask;
import by.comatoznik.Project1.entities.Test;
import by.comatoznik.Project1.repositories.AnswerRepository;
import by.comatoznik.Project1.repositories.AskRepository;
import by.comatoznik.Project1.repositories.TestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin-panel/tests")
public class TestsCreateAndUpdateController {
    private final AnswerRepository answerRepository;
    private final AskRepository askRepository;
    private final TestRepository testRepository;

    public TestsCreateAndUpdateController(AnswerRepository answerRepository,
                                          AskRepository askRepository,
                                          TestRepository testRepository) {
        this.answerRepository = answerRepository;
        this.askRepository = askRepository;
        this.testRepository = testRepository;
    }

    @GetMapping("")
    public String showAllTests(Model model) {
        model.addAttribute("tests", testRepository.findAll().stream().sorted(Comparator.comparingInt(Test::getId)).collect(Collectors.toList()));
        return "/admin-panel/testsCreate/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("test", testRepository.findById(id));
        return "/admin-panel/testsCreate/show";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("test", testRepository.findById(id));
        return "/admin-panel/testsCreate/editTest";
    }

    @PatchMapping("/titles/{identification}")
    public String patchTitle(@PathVariable("identification") int id,
                             @ModelAttribute("editTest") @Valid Test test,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/editTitle";
        }
        Test editTest = testRepository.findById(id);
        editTest.setTitle(test.getTitle());
        testRepository.save(editTest);
        return "redirect:/admin-panel/tests/" + id + "/edit";
    }

    @PatchMapping("/asks/{identification}")
    public String patchAsk(@PathVariable("identification") int id,
                           @ModelAttribute("editTest") @Valid Ask ask,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/editAsk";
        }
        Ask editAsk = askRepository.findById(id);
        editAsk.setAsk(ask.getAsk());
        askRepository.save(editAsk);
        return "redirect:/admin-panel/tests/" + editAsk.getTest().getId() + "/edit";
    }

    @GetMapping("/{identification}/editAsk")
    public String editAsk(@PathVariable("identification") int id,
                          Model model) {
        model.addAttribute("editAsk", askRepository.findById(id));
        return "/admin-panel/testsCreate/editAsk";
    }

    @PatchMapping("/answers/{identification}")
    public String patchAnswer(@PathVariable("identification") int id,
                              @ModelAttribute("editTest") @Valid Answer answer,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/editAnswer";
        }
        Answer editAnswer = answerRepository.findById(id);
        editAnswer.setAnswer(answer.getAnswer());
        answerRepository.save(editAnswer);
        return "redirect:/admin-panel/tests/" + editAnswer.getAsk().getTest().getId() + "/edit";
    }

    @GetMapping("/{identification}/editAnswer")
    public String editAnswer(@PathVariable("identification") int id,
                             Model model) {
        model.addAttribute("editAnswer", answerRepository.findById(id));
        return "/admin-panel/testsCreate/editAnswer";
    }

    @GetMapping("/{identification}/editTitle")
    public String editTitle(@PathVariable("identification") int id,
                            Model model) {
        model.addAttribute("editTest", testRepository.findById(id));
        return "/admin-panel/testsCreate/editTitle";
    }

    @GetMapping("/{identification}/newAnswer")
    public String editTitle(@ModelAttribute("newAnswer") Answer answer,
                            @PathVariable("identification") int id,
                            Model model) {
        model.addAttribute("id", id);
        return "/admin-panel/testsCreate/newAnswer";
    }

    @PostMapping("/{identification}/newAnswer")
    public String createAnswer(@ModelAttribute("test") @Valid Answer answer,
                               BindingResult bindingResult,
                               @PathVariable("identification") int id) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/newAnswer";
        }
        Ask ask = askRepository.findById(id);
        ask.addAnswer(answer);
        answerRepository.save(answer);
        return "redirect:/admin-panel/tests/" + askRepository.findById(id).getTest().getId() + "/edit";
    }

    @GetMapping("/{id}/delete")
    public String deleteTestConfirm(@PathVariable("id") int id,
                                    Model model) {
        model.addAttribute("id", id);
        return "/admin-panel/deleteConfirms/deleteConfirmForDeleteTest";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        Test test = testRepository.findById(id);
        for (Ask ask : test.getAsks()) {
            answerRepository.deleteAll(ask.getAnswers());
        }
        askRepository.deleteAll(test.getAsks());
        testRepository.delete(test);
        return "/admin-panel/testsCreate/show";
    }

    @GetMapping("/asks/{identification}/delete")
    public String deleteAskConfirm(@PathVariable("identification") int id,
                                   Model model) {
        model.addAttribute("ask", askRepository.findById(id));
        return "/admin-panel/deleteConfirms/deleteConfirmForDeleteAsk";
    }

    @DeleteMapping("/asks/{identification}")
    public String deleteAsk(@PathVariable("identification") int id) {
        Ask ask = askRepository.findById(id);
        int idTest = ask.getTest().getId();
        answerRepository.deleteAll(ask.getAnswers());
        askRepository.delete(ask);
        return "redirect:/admin-panel/tests/" + idTest + "/edit";
    }

    @GetMapping("/answers/{identification}/delete")
    public String deleteAnswerConfirm(@PathVariable("identification") int id,
                                      Model model) {
        model.addAttribute("answer", answerRepository.findById(id));
        return "/admin-panel/deleteConfirms/deleteConfirmForDeleteAnswer";
    }

    @DeleteMapping("/answers/{identification}")
    public String deleteAnswer(@PathVariable("identification") int id) {
        Answer answer = answerRepository.findById(id);
        int idTest = answer.getAsk().getTest().getId();
        answerRepository.delete(answer);
        return "redirect:/admin-panel/tests/" + idTest + "/edit";
    }

    @PostMapping("/{identification}")
    public String createAsk(@PathVariable("identification") int id, @ModelAttribute("newAsk") @Valid Ask ask,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/newAsk";
        }
        Test test = testRepository.findById(id);
        test.addAsk(ask);
        askRepository.save(ask);
        return "redirect:/admin-panel/tests/" + id + "/edit";
    }


    @GetMapping("/{identification}/new")
    public String createNewAsk(@PathVariable("identification") int id,
                               @ModelAttribute("newAsk") Ask ask,
                               Model model) {
        model.addAttribute("id", id);
        return "/admin-panel/testsCreate/newAsk";
    }

    @PostMapping("")
    public String create(@ModelAttribute("test") @Valid Test test,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin-panel/testsCreate/new";
        }
        testRepository.save(test);
        return "redirect:/admin-panel/tests";
    }

    @GetMapping("/new")
    public String createNew(@ModelAttribute("test") Test test) {
        return "/admin-panel/testsCreate/new";
    }
}
