package jpql.jpashop.class9.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("book")
public class Book extends Item{
    String author;
    String isbn;
}
