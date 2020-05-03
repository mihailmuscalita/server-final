package com.ubb.mihail.license.model;

public class RegisterCompetitionModel {

    private Integer competitionId;


    public RegisterCompetitionModel(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public RegisterCompetitionModel(){}

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "RegisterCompetitionModel{" +
                ", competitionId=" + competitionId +
                '}';
    }
}
