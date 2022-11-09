package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
/**
 * 1-1-1-5
 * 자식클래스에스는 @DiscriminatorValue
 * 어노테이션을 통해 식별자 칼럼에 대한 값을 정의할 수 있다.
 * 기본값을 엔티티 클래스 이름이다.
 * */
@DiscriminatorValue("book")
public class Book extends Item{

    String author;
    String isbn;
}
