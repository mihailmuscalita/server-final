package com.ubb.mihail.license.repository;

import com.ubb.mihail.license.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompetitionsRepository extends JpaRepository<Competition,Integer> {

    @Query(value = "select * from competitions c where c.id_admin= :adminId",nativeQuery = true)
    List<Competition> findCompetitionsUsingAnId(@Param("adminId") String adminId);

    @Modifying
    @Query(value = "update competitions c set c.competition_status = 1 where c.competition_id = :competitionId",nativeQuery = true)
    void startCompetition(@Param("competitionId") String competitionId);

    @Query(value = "select * from competitions c where c.competition_status = 1 limit 1",nativeQuery = true)
    Competition getActiveCompetition ();

    @Modifying
    @Query(value = "update competitions c set c.competition_status = 0", nativeQuery = true)
    void  stopCompetition();

}
