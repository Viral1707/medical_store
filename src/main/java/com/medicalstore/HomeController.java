package com.medicalstore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/products-page")
    public String products() {
        return "products";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutus";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProducts(@RequestParam(value = "search", required = false) String search) {
        List<Product> allProducts = productRepository.findAll();
        if (search == null || search.isEmpty()) {
            return allProducts;
        } else {
            return allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        }
    }

    @PostMapping("/add-to-cart/{productName}")
    public String addToCart(@PathVariable String productName, HttpSession session) {
        // Find product by name from database
        Product selected = productRepository.findByName(productName).orElse(null);
        if (selected != null) {
            // Get cart from session or create new
            List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.add(selected);
            session.setAttribute("cartItems", cartItems);
        }
        return "redirect:/cart-summary";
    }

    @PostMapping("/add-to-cart")
    @ResponseBody
    public String addToCartForm(@RequestParam("productName") String productName, HttpSession session) {
        Product selected = productRepository.findByName(productName).orElse(null);
        if (selected != null) {
            List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.add(selected);
            session.setAttribute("cartItems", cartItems);
        }
        return "success";
    }

    @PostMapping("/empty-cart")
    @ResponseBody
    public String emptyCart(HttpSession session) {
        System.out.println("Empty cart endpoint called"); // Log for debugging
        session.setAttribute("cartItems", new ArrayList<>());
        return "success";
    }

    @GetMapping("/cart-summary")
    public String cartSummary(org.springframework.ui.Model model, HttpSession session) {
        // Get cart items from session
        List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        model.addAttribute("cartItems", cartItems);
        return "cart-summary";
    }
}