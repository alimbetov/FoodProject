package com.food.demo.repository;

import com.food.demo.model.Food;
import com.food.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface FoodRepositoryCRUD
        extends PagingAndSortingRepository<Food, Long>, QueryByExampleExecutor<Food>
{

    @Query(
            value = "SELECT * FROM Food u WHERE u.isActive = 1",
            nativeQuery = true)
    Collection<User> findAllActiveUsersNative(User myuser);




    @Query(value = "SELECT * FROM Food u WHERE u.is_active=true and u.food_user_id = :id",
            nativeQuery = true)
    Collection<Food>  findUserByStatusAndNameNamedParamsNative(
            @Param("id") Long id);


}
