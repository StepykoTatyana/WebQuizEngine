package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class QuizzesController {

    @Autowired
    QuizzesService quizzesService;


    @PostMapping("/api/quizzes")
    public  ResponseEntity<?> createQuestionUser(@AuthenticationPrincipal UserDetails details,
                                                 @Validated @RequestBody() Quizzes quizzes) {
        return quizzesService.createQuestion(quizzes, details.getUsername());
    }

    @GetMapping("/api/quizzes")
    public  ResponseEntity<?> getQuestion(@AuthenticationPrincipal UserDetails details) {
        return quizzesService.getQuestions();
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<?> getById(@AuthenticationPrincipal UserDetails details,
                                     @PathVariable long id) {
        return quizzesService.getQuestionById(id);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public  ResponseEntity<?> postAnswerWithId(@AuthenticationPrincipal UserDetails details,
                                               @PathVariable long id, @RequestBody() Answer answer) {
        return quizzesService.getSolveWithId(id, answer.getAnswer());
    }

    @DeleteMapping("/api/quizzes/{id}")
    public  ResponseEntity<?> deleteAnswerWithId(@AuthenticationPrincipal UserDetails details,
                                                 @PathVariable long id) {
        if (quizzesService.getQuestionById(id).getStatusCode() == HttpStatus.OK) {
            Quizzes quizzes = quizzesService.repository.findById(id).get();
            if (quizzes.getEmail().equals(details.getUsername())) {
                quizzesService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
