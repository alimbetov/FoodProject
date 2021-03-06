package com.food.demo.repository;

import com.food.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findUserByUsername(String userName);

}
