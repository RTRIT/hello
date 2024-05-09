package com.example.webApp.Repository;


import com.example.webApp.Model.User;
import com.example.webApp.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends  CrudRepository<User,Integer> {


    //GET USER LIST
    @Query(nativeQuery = true, value="SELECT * FROM users")
    List<User> getUserList();


}
