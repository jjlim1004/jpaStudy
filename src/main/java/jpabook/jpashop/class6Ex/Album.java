package jpabook.jpashop.class6Ex;

import javax.persistence.Entity;

@Entity
public class Album extends Item{
    private String artist;
    private String etc;

    public Album(String artist, String etc) {
        this.artist = artist;
        this.etc = etc;
    }
}
