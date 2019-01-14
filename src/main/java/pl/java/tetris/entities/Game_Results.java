package pl.java.tetris.entities;

import javax.persistence.*;


@Entity
public class Game_Results {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String result;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Game_Results(String result, User user) {
        this.result = result;
        this.user = user;
    }

    public Game_Results() {
    }
}
