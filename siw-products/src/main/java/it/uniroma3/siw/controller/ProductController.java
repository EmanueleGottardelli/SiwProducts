package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Product;
import it.uniroma3.siw.service.ProductService;
import it.uniroma3.siw.validator.ProductValidator;
import jakarta.validation.Valid;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductValidator productValidator;
	
	@GetMapping(value="/admin/operazioniProduct")
	public String operazioniProduct() {
		return "admin/operazioniProduct.html";
	}
	
	@GetMapping("/admin/fornNewProduct")
	public String formNewProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "admin/formNewProduct.html";
	}
	
	@PostMapping("/admin/products")
	public String newProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
		productValidator.validate(product, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.productService.saveProduct(product);
			model.addAttribute("product", product);
			return "product";
		} else {
			model.addAttribute("messaggioErrore", "Questo prodotto esiste già nel catalogo, inseriscine un altro:");
			return "admin/formNewProduct.html";
		}
	}
	
	@GetMapping("/formSearchProduct")
	public String formSearchProduct() {
		return "formSearchProduct.html";
	}
	
	@PostMapping("/searchProductsByName")
	public String searchProductsByName(Model model,@RequestParam String name) {
		model.addAttribute("products", this.productService.getProductsByName(name));
		return "searchProductsByName.html";
	}
	
	@PostMapping("/searchProductsByPrice")
	public String searchProductsByPrice(Model model,@RequestParam Double price) {
		model.addAttribute("products", this.productService.getProductsByPrice(price));
		return "searchProductsByPrice.html";
	}
	
	@PostMapping("/searchProductsByType")
	public String searchProductsByType(Model model,@RequestParam String type) {
		model.addAttribute("products", this.productService.getProductsByType(type));
		return "searchProductsByType.html";
	}
	
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
