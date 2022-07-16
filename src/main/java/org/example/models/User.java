package org.example.models;

import org.example.validators.StringField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="Login")
    @StringField(notEmpty = true, min = 4, max = 16,
            pattern = "^([A-Z]|[a-z]){1}([A-Z]|[a-z]|[0-9]|[_]){3,15}$",
            messageNotEmpty = "* Login should not be empty",
            messageMinLength = "* Login is too short",
            messageMaxLength = "* Login is too long",
            messagePattern = "* Incorrect Login format")
    private String login;

    @Column(name="Password")
    @StringField(notEmpty = true, min = 4, max = 35,
            pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,25}$",
            messageNotEmpty = "* Password should not be empty",
            messageMinLength = "* Password is too short",
            messageMaxLength = "* Password is too long",
            messagePattern = "* Password is very easy")
    private String password;

    @Column(name="FullName")
    @StringField(notEmpty = true, min = 7, max = 38,
            pattern = "^[A-Z]{1}[a-z]{2,15}[ ][A-Z]{1}[a-z]{2,20}$",
            messageNotEmpty = "* Full name should not be empty",
            messageMinLength = "* Full name is too short",
            messageMaxLength = "* Full name is too long",
            messagePattern = "* Incorrect Full name format")
    private String fullName;

    @Column(name="City")
    @StringField(notEmpty = true, min = 3, max = 31,
            pattern = "^[A-Z]{1}([a-z]|[-]){2,30}$",
            messageNotEmpty = "* City should not be empty",
            messageMinLength = "* City is too short",
            messageMaxLength = "* City is too long",
            messagePattern = "* Incorrect City format")
    private String city;

    @Column(name="Email")
    @StringField(notEmpty = true, min = 4, max = 16,
            pattern = "^([-!#-'*+-9=?A-Z^-~]+(\\.[-!#-'*+-9=?A-Z^-~]+)*|\"([]!#-[^-~ \\t]|(\\\\[\\t -~]))+\")@([-!#-'*+-9=?A-Z^-~]+(\\.[-!#-'*+-9=?A-Z^-~]+)*|\\[[\\t -Z^-~]*])$",
            messageNotEmpty = "* Email should not be empty",
            messageMinLength = "* Email is too short",
            messageMaxLength = "* Email is too long",
            messagePattern = "* Incorrect Email format")
    private String email;

    public User() { }

    public User(int id, String login, String password, String fullName, String city, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
    }

    public User(String login, String password, String fullName, String city, String email) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
