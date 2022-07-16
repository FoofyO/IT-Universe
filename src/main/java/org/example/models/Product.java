package org.example.models;

import org.example.validators.StringField;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="Name")
    @StringField(notEmpty = true, min = 4, max = 30,
            pattern = "^([A-Z]|[a-z]){1}([A-Z]|[a-z]|[0-9]|[_]){3,15}$",
            messageNotEmpty = "* Product name should not be empty",
            messageMinLength = "* Product name is too short",
            messageMaxLength = "* Product name is too long",
            messagePattern = "* Incorrect product name format")
    private String name;

    @Column(name="Description", length = 500)
    @StringField(notEmpty = false, min = 0, max = 500,
            messageMaxLength = "* Product description is too long")
    private String description;

    @Column(name="CreationDate")
    private Date creationDate;

    @Column(name="Price")
    private double price;

    @OneToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER)
    private User seller;

    public Product() {}

    public Product(String name, String description, Date creationDate, double price, Category category, User seller) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.price = price;
        this.category = category;
        this.seller = seller;
    }

    public Product(int id, String name, String description, Date creationDate, double price, Category category, User seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.price = price;
        this.category = category;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
