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

import it.uniroma3.siw.model.Comment;
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
	
	
	@PostMapping("/admin/product/{id}/addSimilar")
	public String addSimilarProduct(@PathVariable("id") Long id, @RequestParam("similarId") Long similarId) {
		productService.addProductSimilar(id, similarId);
		return "redirect:/admin/manageProducts";
	}
	
	@PostMapping("/admin/product/{id}/removeSimilar")
	public String removeSimilarProduct(@PathVariable("id") Long id, @RequestParam("similarId") Long similarId) {
		productService.deleteProductSimilar(id, similarId);
		return "redirect:/admin/manageProducts";
	}
	
	@GetMapping(value="/admin/formUpdateProduct/{id}")
	public String formUpdateProduct(@PathVariable("id") Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "admin/formUpdateProduct";
	}
	
	@GetMapping(value="/admin/indexProduct")
	public String indexProduct() {
		return "admin/indexProduct";
	}
	
	@PostMapping("/admin/updateProduct/{id}")
	public String updateProduct(@PathVariable Long id,
	                            @ModelAttribute("product") Product formProduct) {
	    Product existing = productService.getProductById(id);

	    if (formProduct.getName() != null && !formProduct.getName().isBlank()) {
	        existing.setName(formProduct.getName());
	    }
	    if (formProduct.getDescription() != null && !formProduct.getDescription().isBlank()) {
	        existing.setDescription(formProduct.getDescription());
	    }
	    if (formProduct.getPrice() != null) {
	        existing.setPrice(formProduct.getPrice());
	    }
	    if (formProduct.getType() != null && !formProduct.getType().isBlank()) {
	        existing.setType(formProduct.getType());
	    }

	    productService.saveProduct(existing);

	    return "redirect:/product/" + id;
	}

	@GetMapping(value="/admin/manageProducts")
	public String operazioniProduct(Model model) {
		model.addAttribute("products", productService.getAllProducts());		
		return "admin/manageProducts";
	}
	
	@GetMapping("/admin/formNewProduct")
	public String formNewProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "admin/formNewProduct.html";
	}
	
	@PostMapping("/admin/product")
	public String newProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
		productValidator.validate(product, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.productService.saveProduct(product);
			model.addAttribute("product", product);
			return "redirect:/product";
		} else {
			model.addAttribute("messaggioErrore", "Questo prodotto esiste già nel catalogo, inseriscine un altro:");
			return "admin/formNewProduct";
		}
	}
	
	@PostMapping("/admin/deleteProduct/{product_id}")
	public String deleteProduct(@PathVariable("product_id") Long id) {
		Product product = this.productService.getProductById(id);
		productService.deleteProduct(product);
		return "redirect:/admin/manageProducts";
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
		model.addAttribute("newComment", new Comment());
		return "product";
	}
	
	@GetMapping("/product")
	public String getProducts(Model model) {
		model.addAttribute("products", this.productService.getAllProducts());
		return "products";
	}
}
