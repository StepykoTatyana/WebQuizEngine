package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Emails {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name = "emails_id",columnDefinition = "bigint not null")
    private Long emails_id;

    public Long getEmails_id() {
        return emails_id;
    }

    public void setEmails_id(Long emails_id) {
        this.emails_id = emails_id;
    }

    @Column(name = "quiz_id")
    private Long id;

    @Column
    private final String completedAt= LocalDateTime.now().toString();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    @JsonIgnore
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

}
