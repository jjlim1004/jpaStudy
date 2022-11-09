package domain;

import javax.persistence.*;

@Entity
/**
 *  6-1-1-1 조인전략은 strategy = InheritanceType.JOINED 를 쓰면 된다.
 *
@Inheritance(strategy = InheritanceType.JOINED)
 */

/**
 *  6-1-1-4
 *  DTYPE 이라는 걸 생성해준다.
 *  이 칼럼은 식별을 위한 칼럼이다.
 *  이 칼럼에는 하위의 테이블, album, book, movie 의 값이 들어온다.
 *  name으로 칼럼의 이름을 정해줄 수 있다.
 *  그리고 칼럼에 들어오는 값들은 상속을 하는 클래스에서 
 * @DiscriminatorValue("value") 를 통해 설정할 수 있다.
 * */

/**
 *  6-1-2-1
 * 단일테이블전략을 사용하는 경우에는
 * strategy = InheritanceType.SINGLE_TABLE 사용하면 된다.
 *
 * */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

/**
 * 6-1-3-1
 * public abstract class Item 로 클래스를 만들어야 한다.
 * abstract로 만들지 않으면 그냥 Item만으로도 쓰는 경우도 있는것을 상정한다.
 @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 * 를 사용해야한다.
 * */

/**
 * 6-1-2-2
 * 조인테이블 전략에는 @DiscriminatorColumn 쓰지 않으면 아무일도 일어나지 않는다. 필수가 아니기 때문.
 * 단일테이블 전략에는 @DiscriminatorColumn 쓰지 않으면 알아서 DTYPE을 생성해준다. 필수기 때문
 * 각각테이블 전략에는 @DiscriminatorColumn 쓴다 해도 아무일도 일어나지 않는다. 필요가 없기 때문
 * */
@DiscriminatorColumn(name = "DIS_TYPE")
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
