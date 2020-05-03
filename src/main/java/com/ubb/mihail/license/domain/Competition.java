package com.ubb.mihail.license.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "competitionId", unique = true, nullable = false)
    private int idCompetition;

    @Column(name="competitionTitle", length = 255)
    private String competitionTitle;

    @Column(name="competitionReward", length = 100)
    private String competitionReward;

    @Column(name="competitionStatus")
    private int competitionStatus;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private User admin;



    public Competition(){}

    public Competition(int idCompetition,String competitionTitle, String competitionReward) {
        this.idCompetition = idCompetition;
        this.competitionTitle = competitionTitle;
        this.competitionReward = competitionReward;
    }

    public Competition(int idCompetition,String competitionTitle, String competitionReward,int competitionStatus, User admin) {
        this.idCompetition = idCompetition;
        this.competitionTitle = competitionTitle;
        this.competitionReward = competitionReward;
        this.competitionStatus = competitionStatus;
        this.admin = admin;
    }

    public Competition(String competitionTitle, String competitionReward,int competitionStatus, User admin) {
        this.competitionTitle = competitionTitle;
        this.competitionReward = competitionReward;
        this.competitionStatus = competitionStatus;
        this.admin = admin;
    }

    public int getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }

    public String getCompetitionReward() {
        return competitionReward;
    }

    public void setCompetitionReward(String competitionReward) {
        this.competitionReward = competitionReward;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public int getCompetitionStatus() {
        return competitionStatus;
    }

    public void setCompetitionStatus(int competitionStatus) {
        this.competitionStatus = competitionStatus;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "idCompetition=" + idCompetition +
                ", competitionTitle='" + competitionTitle + '\'' +
                ", competitionReward='" + competitionReward + '\'' +
                '}';
    }

}
