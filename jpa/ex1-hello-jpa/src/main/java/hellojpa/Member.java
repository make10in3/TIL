package hellojpa;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name="Member") // @Entity가 붙은 클래스를 엔티티라 부름, JPA내부에서 사용하는 이름이지만 기본으로 해놔야지 안헷갈린다.
@Table(name="MBR") // 엔티티와 매핑 테이블 지정
public class Member {
    @Id //pk 매
    private Long id;

    @Column(name = "name", updatable = false, nullable = false, unique = false, length = 10, columnDefinition = "varchar(100) default 'EMPTY'")
    // unique 를 true 로 설정하면 알수없는 랜던값이 저장되기때문에 운영할때 어렵
    private String name;

    private Integer age; // db에도 숫자타입으로 자동 생성

    @Enumerated(EnumType.STRING) // 옵션 : ORDINAL - 순서 저장 , STRING(필수) - 이름 저장
    private RoleType roleType;

    // 예전버전 날짜 클래스 사용할때 어노테이션
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // 최신버전 날짜클래스
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;

    @Temporal(TemporalType.TIMESTAMP) // 날짜 + 시간
    private Date lastModifiedDate;

    @Lob // 큰 컨텐츠, 문자타입은 clob 나머지는 blob
    private String description;

    @Transient
    private int temp; // 메모리에서만 임시로 쓰고 싶을 때

    // JPA 에서 사용할 기본 생성자
    public Member() {
    }
}