package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/product/{id}")
	public String getProduct(@PathVariable("id") Long id,Model model) {
		model.addAttribute("product", this.productService.getProductById(id));
		return "product.html";
	}
	
	@GetMapping("/product")
	public String getProducts(Model model) {
		model.addAttribute("products", this.productService.getAllProducts());
		return "products.html";
	}
}
