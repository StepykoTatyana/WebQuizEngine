package engine;



import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
public class Users {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long user_id;

    @Column
    @Email
    @Pattern(regexp = ".*[@]+.*[.]+.*")
    private String email;

    @Column
    @NotNull
    @NotBlank
    @Size(min = 5)
    private String password;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public Users() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
