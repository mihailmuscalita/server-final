package com.ubb.mihail.license.repository;

import com.ubb.mihail.license.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {

    @Query(value ="select * from friendsrequest fr where fr.sendby = :userSendBy and fr.id_user = :userId limit 1" ,nativeQuery = true)
    Optional<FriendRequest> existThisRequest(@Param("userSendBy") String userSendBy, @Param("userId") Integer userId);

    @Modifying
    @Query(value ="delete from friendsrequest where sendby = :userSendBy and id_user = :userId " ,nativeQuery = true)
    void deleteRequest(@Param("userSendBy") String userSendBy, @Param("userId") Integer userId);

    @Query(value ="select * from friendsrequest fr where fr.id_user = :userId",nativeQuery = true)
    Optional<List<FriendRequest>> getRequestByAUser(@Param("userId") Integer userId);

}
