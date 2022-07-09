package com.example.system.voting;

import com.example.system.demand.Demand;
import com.example.system.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Demand demand;

    private Boolean vote;

    public Voting(User user, Demand demand, Boolean vote) {
        this.user = user;
        this.demand = demand;
        this.vote = vote;
    }
}
