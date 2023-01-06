package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class QuizzesService {

    @Autowired
    QuizzesRepository repository;

    public ResponseEntity<?> saveAnswer(String answer) {
        Feedback feedback = new Feedback();
        if (Integer.parseInt(answer) == 2) {
            feedback.setSuccess(true);
            feedback.setFeedback("Congratulations, you're right!");

        } else {
            feedback.setSuccess(false);
            feedback.setFeedback("Wrong answer! Please, try again.");
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    public ResponseEntity<?> createQuestion(Quizzes quizzes, String email) {
        repository.save(new Quizzes(quizzes.getTitle(), quizzes.getText(),
                quizzes.getOptions(), quizzes.getAnswer(), email));
        quizzes.setId(repository.lastId());
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    public ResponseEntity<?> getQuestions() {
        return new ResponseEntity<>(repository.findAllQuizzes(), HttpStatus.OK);
    }

    public ResponseEntity<?> getQuestionById(long idUser) {
        try {
            Quizzes quiz = repository.findById(idUser).get();
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getSolveWithId(long idUser, List<Integer> answer) {
        Feedback feedback = new Feedback();
        try {
            Quizzes quiz = repository.findById(idUser).get();
            if ((quiz.getAnswer() == null && (answer == null || answer.size() == 0))
                    || Arrays.toString(answer.toArray()).equals(Arrays.toString(quiz.getAnswer().toArray()))) {
                feedback.setSuccess(true);
                feedback.setFeedback("Congratulations, you're right!");
            } else {
                feedback.setSuccess(false);
                feedback.setFeedback("Wrong answer! Please, try again.");
            }
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }


}
