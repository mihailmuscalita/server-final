package com.ubb.mihail.license.repository;

import com.ubb.mihail.license.domain.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends,Long> {

    @Query(value = "select * from friends f where f.first_user = :userId or f.second_user = :userId",nativeQuery = true)
    Optional<List<Friends>> getFriends(@Param("userId") Integer userId);

    @Query(value = "select * from friends f where f.first_user = :firstId and f.second_user = :secondId limit 1",nativeQuery = true)
    Optional<Friends> checkFriends(@Param("firstId") Integer firstId, @Param("secondId") Integer secondId);

}