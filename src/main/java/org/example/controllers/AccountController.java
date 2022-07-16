package org.example.controllers;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ProductDAO;
import org.example.dao.UserDAO;
import org.example.models.Product;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
public class AccountController {
    private UserDAO userDAO;
    private ProductDAO productDAO;

    @Autowired
    public AccountController(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public String get(HttpServletRequest request) {
        User existsUser = getUser(request);
        if(existsUser != null) return "redirect:/account/" + existsUser.getId();
        return "redirect:/account/login";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        User user = userDAO.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("products", productDAO.getAll().stream().filter(p -> p.getSeller().getId() == user.getId()).collect(Collectors.toList()));
        return "account/detail";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("user") User user, HttpServletRequest request) {
        User existsUser = getUser(request);
        if(existsUser != null) return "redirect:/account/" + existsUser.getId();
        return "account/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "account/login";
        }

        User currentUser = userDAO.getAll().stream().filter(usr-> {
            return user.getLogin().equals(usr.getLogin())
                    && DigestUtils.md5Hex(user.getPassword()).equals(usr.getPassword());
        }).findAny().orElse(null);

        if(currentUser == null) {
            bindingResult.addError(new FieldError("user", "login", "* Incorrect login or password"));
            return "account/login";
        }

        Gson gson = new Gson();
        String jsonData = gson.toJson(currentUser);
        HttpSession session = request.getSession();
        session.setAttribute("user", jsonData);
        return "redirect:/account/" + currentUser.getId();
    }

    @GetMapping("/registration")
    public String getRegistration(Model model, HttpServletRequest request) {
        User existsUser = getUser(request);
        if(existsUser != null) return "redirect:/account/" + existsUser.getId();
        else {
            User newUser = new User();
            model.addAttribute("user", newUser);
            return "account/registration";
        }
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "account/registration";
        }

        User existsUser = userDAO.getAll().stream().filter(usr-> {
            return user.getLogin().equals(usr.getLogin()) || user.getEmail().equals(usr.getEmail());
        }).findAny().orElse(null);

        if(existsUser != null) {
            bindingResult.addError(new FieldError("user", "login", "* User already exists"));
            return "account/registration";
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userDAO.add(user);
        Gson gson = new Gson();
        String jsonData = gson.toJson(user);
        HttpSession session = request.getSession();
        session.setAttribute("user", jsonData);
        return "redirect:/products";
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request) {
        if(getUser(request) != null) {
            HttpSession session = request.getSession();
            session.invalidate();
        }
        return "redirect:/account/login";
    }

    @ModelAttribute("userS")
    public User getUser(HttpServletRequest request) {
        Gson gson = new Gson();
        HttpSession session = request.getSession();
        String jsonData = (String)session.getAttribute("user");
        User user = gson.fromJson(jsonData, User.class);
        return user;
    }
}
