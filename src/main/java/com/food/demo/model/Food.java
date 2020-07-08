package com.food.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.demo.model.enums.FoodCategory;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity(name = "food")
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String foodName;

    private String foodDescription;

    private BigDecimal price;

    private BigDecimal weight;

    private boolean isActive;

    private Date releaseDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_user_id", nullable = false)
    private User user;


    @ElementCollection(targetClass = FoodCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "food_caregory", joinColumns = @JoinColumn(name = "food_id"))
    @Enumerated(EnumType.STRING)
    private Set<FoodCategory> foodCategory;


    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "food_image")
    private byte[] image;

    public Food() {
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<FoodCategory> getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(Set<FoodCategory> foodCategory) {
        this.foodCategory = foodCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
