package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_group_id"))
    private List<Diner> diners = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_group_id"))
    private List<Account> accounts = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private Group(
            List<Diner> diners,
            Account account,
            LocalDateTime created_at
    ){
        this.diners=diners;
        this.accounts.add(account);
        this.created_at = created_at;
    }

    public static Group groupCreate(
            List<Diner> diners,
            Account account
    ){
        return new Group(
                diners,
                account,
                LocalDateTime.now()
        );
    }
    public void entrance(
            List<Diner> diners,
            Account account
    ){
        for(Diner diner : diners){ this.diners.add(diner); }
        this.accounts.add(account);
    }
    public void exit(Account account){ this.accounts.remove(account); }

}
