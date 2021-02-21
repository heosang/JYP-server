package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "diners", indexes = {
        @Index(name = "diners_group_id", columnList = "group_id"),
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Diner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "kind", nullable = false)
    private String kind;

    @Column(name = "selectors-nickname", nullable = false)
    private String selectorsNickname;
    public Diner(
            String name,
            String kind
    ){
        this.name = name;
        this.kind = kind;
    }
    public Diner(
            String name,
            String kind,
            String selectorsNickname
    ){
        this.name = name;
        this.kind = kind;
        this.selectorsNickname=selectorsNickname;
    }
}
