package org.example.controllers;

import com.google.gson.Gson;
import org.example.dao.CategoryDAO;
import org.example.dao.ProductDAO;
import org.example.dao.UserDAO;
import org.example.models.Product;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Autowired
    public ProductController(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public String get(HttpServletRequest request, Model model) {
        User user = getUser(request);
        model.addAttribute("user", user);
        if(user == null) model.addAttribute("products", productDAO.getAll());
        else model.addAttribute("products", productDAO.getAll().stream().filter(p -> p.getSeller().getId() != user.getId()));
        return "products/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, HttpServletRequest request) {
        model.addAttribute("product", productDAO.getById(id));
        model.addAttribute("user", getUser(request));
        return "products/detail";
    }

    @GetMapping("/add")
    public String getAdd(@ModelAttribute("product") Product product, Model model, HttpServletRequest request) {
        if(getUser(request) != null) {
            model.addAttribute("categories", categoryDAO.getAll());
            return "products/add";
        } else return "redirect:/account/login";
    }

    @PostMapping
    public String add(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "products/add";
        }
        product.setCreationDate(new Date());
        product.setSeller(getUser(request));
        product.setCategory(categoryDAO.getById(Integer.parseInt(product.getCategory().getName())));
        productDAO.add(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model, HttpServletRequest request) {
        Product product = productDAO.getById(id);
        User user = getUser(request);
        if(user != null) {
            if(product != null) {
                if(product.getSeller().getId() == user.getId()) {
                    model.addAttribute("categories", categoryDAO.getAll());
                    model.addAttribute("product", product);
                    return "products/edit";
                }
            }
            return "products/index";
        } else return "redirect:/account/login";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "products/edit";
        }
        product.setCreationDate(new Date());
        product.setSeller(getUser(request));
        product.setCategory(categoryDAO.getById(Integer.parseInt(product.getCategory().getName())));
        productDAO.update(id, product);
        return "redirect:/products/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        productDAO.delete(id);
        return "redirect:/products";
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
