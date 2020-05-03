package com.ubb.mihail.license.repository;

import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.model.UserSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StepsRepository extends JpaRepository<Steps,Integer> {

    @Query(value = "select * from steps s where s.id_admin= :userId limit 1", nativeQuery = true)
    Optional<Steps> findUserIsRegistered(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "update steps s set s.number_of_steps = :steps where s.id_competition = :competitionId and s.id_admin = :userId and s.data = :date",nativeQuery = true)
    void updateSteps(@Param("competitionId") Integer competitionId, @Param("userId") Integer userId, @Param("steps") Integer steps, @Param("date")String date);

    @Query(value = "select * from steps s where s.id_admin = :userId order by s.data desc limit 1",nativeQuery = true)
    Optional<Steps> findSteps(@Param("userId") Integer userId);

    @Query(value="select id_admin, SUM(number_of_steps) from steps group by id_admin",nativeQuery = true)
    Optional<List<?>> countStepsForEachUser();

    @Query(value = "select * from steps s where s.id_admin = :userId", nativeQuery = true)
    Optional<List<Steps>> getStepsForFriend(@Param("userId") Integer userId);
}
