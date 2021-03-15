package hellojpa;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name="Member_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

    // 일대다 양방향
    // 읽기 전용
    @ManyToOne
    @JoinColumn(name="TEAM_ID", insertable = false, updatable = false)
    private Team team;

    // 일대일 양방향
    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private Locker locker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}