package kea.eksamen.models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int votes;

    @ManyToOne
    private Party party;


}
