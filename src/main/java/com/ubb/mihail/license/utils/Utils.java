package com.ubb.mihail.license.utils;

import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.model.CompetitionDTO;
import com.ubb.mihail.license.model.UserModel;

public class Utils {

    public static void setStateForCompetition(CompetitionDTO competition, Steps steps){
        if (steps!=null){
            competition.setIsRegistered("Yes");
        }
        else{
            competition.setIsRegistered("No");
        }
    }

    public static String formatMessageForUsers(UserModel userModel, Integer steps, Integer positionInCompetition){
        return "Hello, we are very happy that you are in our competition !" +
                "        This time you have " + steps + "  and you are  " + positionInCompetition + " in the contest !" +
                " If you're in the top three contact us at licenta.test@yahoo.com";
    }

}
