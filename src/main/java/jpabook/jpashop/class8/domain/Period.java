package jpabook.jpashop.class8.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * 8-2-2-3
 * 값으로 쓸거라는 어노테이션을 사용한다.
 * */
@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
