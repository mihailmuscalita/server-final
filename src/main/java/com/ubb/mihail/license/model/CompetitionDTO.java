package com.ubb.mihail.license.model;

public class CompetitionDTO {

    private Integer competitionId;

    private String competitionTitle;

    private String competitionReward;

    private String isRegistered;

    private int adminId;

    public CompetitionDTO(String competitionTitle, String competitionReward) {
        this.competitionTitle = competitionTitle;
        this.competitionReward = competitionReward;
    }

    public CompetitionDTO() {
    }

    public CompetitionDTO(Integer competitionId, String competitionTitle, String competitionReward) {
        this.competitionId = competitionId;
        this.competitionTitle = competitionTitle;
        this.competitionReward = competitionReward;
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


    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public String getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered;
    }


    @Override
    public String toString() {
        return "{" +
                "competitionId=" + competitionId +
                ", competitionTitle='" + competitionTitle + '\'' +
                ", competitionReward='" + competitionReward + '\'' +
                ", isRegistered='" + isRegistered + '\'' +
                '}';
    }
}
