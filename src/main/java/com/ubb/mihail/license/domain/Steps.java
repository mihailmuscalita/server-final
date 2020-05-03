package com.ubb.mihail.license.domain;

import javax.persistence.*;


import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Steps")
public class Steps {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stepId", unique = true, nullable = false)
    private int stepId;

    @Column(name="numberOfSteps")
    private Long numberOfSteps;

    @Column(name="data")
    private String date;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private User admin;


    @ManyToOne
    @JoinColumn(name = "idCompetition")
    private Competition competition;

    public Steps(){}

    public Steps(Long numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public Steps(Long numberOfSteps, User admin, Competition competition, String date) {
        this.numberOfSteps = numberOfSteps;
        this.admin = admin;
        this.competition = competition;
        this.date = date;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public Long getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(Long numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Steps{" +
                "stepId=" + stepId +
                ", numberOfSteps=" + numberOfSteps +
                ", date='" + date + '\'' +
                ", admin=" + admin +
                ", competition=" + competition +
                '}';
    }
}
