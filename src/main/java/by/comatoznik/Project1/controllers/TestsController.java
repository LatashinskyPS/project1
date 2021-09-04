package by.comatoznik.Project1.controllers;

import by.comatoznik.Project1.entities.*;
import by.comatoznik.Project1.models.Counter;
import by.comatoznik.Project1.models.UserAnswerValidation;
import by.comatoznik.Project1.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
public class TestsController {

    private final TestRepository testRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;
    private final UserTestRepository userTestRepository;

    public TestsController(TestRepository testRepository, UserAnswerRepository userAnswerRepository,
                           UserRepository userRepository, UserTestRepository userTestRepository) {
        this.testRepository = testRepository;
        this.userAnswerRepository = userAnswerRepository;
        this.userRepository = userRepository;
        this.userTestRepository = userTestRepository;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("tests", testRepository.findAll().stream().sorted(Comparator.comparingInt(Test::getId)).collect(Collectors.toList()));
        return "tests/index";
    }

    @GetMapping("/{id1}/{id2}/addUserAnswer")
    public String passTests(Model model,
                            @PathVariable("id1") int idTest,
                            @PathVariable("id2") int idAsk,
                            Principal principal) {
        User user = userRepository.findByUserName(principal.getName());
        Test test = testRepository.findById(idTest);
        if (user == null || test == null) {
            return "redirect:/tests";
        }
        if (test.getAsks().size() <= idAsk) {
            model.addAttribute("test", test);
            return "tests/end";
        }
        UserTest userTest = userTestRepository.findByTestAndUser(test, user);
        if (userTest == null) {
            userTest = new UserTest();
            userTest.setTest(test);
            userTest.setUser(user);
            userTestRepository.save(userTest);
        }
        model.addAttribute("id1", idTest);
        model.addAttribute("id2", idAsk);
        model.addAttribute("ask", new ArrayList<>(test.getAsks()).get(idAsk));
        model.addAttribute("counter", new Counter());
        UserAnswerValidation userAnswerValidation = new UserAnswerValidation();
        if (userTest.getUserAnswers() != null && userTest.getUserAnswers().size() > idAsk) {
            UserAnswer userAnswer = new ArrayList<>(userTest.getUserAnswers()).get(idAsk);
            if (userAnswer != null) {
                int count = 0;
                for (Answer answer : new ArrayList<>(test.getAsks()).get(idAsk).getAnswers()) {
                    if (userAnswer.getAnswer() == answer) {
                        userAnswerValidation.setNumberOfAnswer(count);
                        break;
                    }
                    ++count;
                }
            }
        }
        model.addAttribute("userAnswerValidation", userAnswerValidation);
        return "/tests/firstAsk";
    }

    @PostMapping("/{id1}/{id2}")
    public String passAsks(Model model,
                           @PathVariable("id1") int idTest,
                           @PathVariable("id2") int idAsk,
                           @ModelAttribute("userAnswerValidation") UserAnswerValidation userAnswerValidation,
                           Principal principal) {
        Test test = testRepository.findById(idTest);
        if (test == null) {
            return "redirect:/tests";
        }
        if (idAsk >= test.getAsks().size()) {
            return "redirect:/tests";
        }
        Ask ask = new ArrayList<>(test.getAsks()).get(idAsk);
        if (ask == null) {
            return "redirect:/tests";
        }
        User user = userRepository.findByUserName(principal.getName());
        UserTest userTest = userTestRepository.findByTestAndUser(test, user);
        if (userTest == null) {
            return "redirect:/tests";
        }
        if (idAsk == 0 || userTest.getUserAnswers().size() >= idAsk) {
            int id = userAnswerValidation.getNumberOfAnswer();
            UserAnswer userAnswer = null;
            if (userTest.getUserAnswers().size() > idAsk) {
                userAnswer = new ArrayList<>(userTest.getUserAnswers()).get(idAsk);
            }
            if (userAnswer == null) {
                userAnswer = new UserAnswer();
            }
            userAnswer.setUserTest(userTest);
            userAnswer.setAnswer(new ArrayList<>(ask.getAnswers()).get(id));
            userAnswer.setAsk(ask);
            userAnswerRepository.save(userAnswer);
        } else {
            return "redirect:/tests";
        }
        return "redirect:/tests/" + idTest + "/" + (idAsk + 1) + "/addUserAnswer";
    }

}
