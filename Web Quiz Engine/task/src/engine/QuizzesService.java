package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class QuizzesService {

//    @Autowired
//    QuizzesRepository repository;
    @Autowired
    EmailsRepository repositoryEmails;

    @Autowired
    QuizzesRepositoryWithCrud repository;

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
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getQuestionById(long idUser) {
        try {
            Quizzes quiz = repository.findById(idUser).get();
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getSolveWithId(long idUser, List<Integer> answer, UserDetails details) {
        Feedback feedback = new Feedback();
        try {
            System.out.println(idUser);
            System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{idUser}}}}}}}}}}}}}}}}}}}}}}}}}");
            System.out.println(repository.findById(idUser).get().getAnswer());
            Quizzes quiz = repository.findById(idUser).get();
            if ((quiz.getAnswer() == null && (answer == null || answer.size() == 0))
                    || Arrays.toString(answer.toArray()).equals(Arrays.toString(quiz.getAnswer().toArray()))) {
                System.out.println(repositoryEmails.findByQuizId(idUser));
//                if (repositoryEmails.findByQuizId(idUser) == null) {
                    Emails emails = new Emails();
                    emails.setId(idUser);
                    emails.setId(idUser);
                    emails.setEmail(details.getUsername());
                    repositoryEmails.save(emails);
                //}

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

    public ResponseEntity<?> getQuestionsWithPage(Integer pageNo, Integer pageSize) {
        AllQuizzes allQuizzes = new AllQuizzes();
        Page<Quizzes> pagedResult = repository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("id").descending()));
        allQuizzes.setContent(pagedResult.getContent());
        if (!pagedResult.hasContent()) {
            allQuizzes.setContent(new ArrayList<>());
        }
        return new ResponseEntity<>(allQuizzes, HttpStatus.OK);

    }


    public ResponseEntity<?> getCompletedAnswers(UserDetails details, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("completedAt").descending());
        List<Emails> list = repositoryEmails.findByEmail(details.getUsername(), pageable);
        AllQuizzes allQuizzes = new AllQuizzes();
        allQuizzes.setContent(list);
        return new ResponseEntity<>(allQuizzes, HttpStatus.OK);
    }
}
