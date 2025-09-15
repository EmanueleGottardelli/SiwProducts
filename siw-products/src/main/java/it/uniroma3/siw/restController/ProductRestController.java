package it.uniroma3.siw.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Product;
import it.uniroma3.siw.service.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/rest/product/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		return this.productService.getProductById(id);
	}

	@GetMapping(value = "/rest/products")
	public List<Product> getAllProducts() {
		return this.productService.getAllProducts();
	}

	@GetMapping(value = "/rest/searchProducts")
	public List<Product> getProductsByNameOrPriceOrType(@RequestParam(required = false) String name,
														@RequestParam(required = false) String type, 
														@RequestParam(required = false) Double price) {

		return this.productService.searchProducts(name, type, price);

	}
}
