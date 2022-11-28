package jpql.jpashop.class9.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Useless {
    @Id
    @GeneratedValue
    private Long id;

    private String naame;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaame() {
        return naame;
    }

    public void setNaame(String naame) {
        this.naame = naame;
    }
}
