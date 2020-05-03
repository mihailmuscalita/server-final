package com.ubb.mihail.license.repository;

import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select * from users u where u.user_name= :name and u.user_password= :password", nativeQuery = true)
    User findUser(@Param("name") String name,@Param("password") String password);


    @Query(value = "select * from users u where u.user_name= :name", nativeQuery = true)
    User findUserByName(@Param("name") String name);

}
