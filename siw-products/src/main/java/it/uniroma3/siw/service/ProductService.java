package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Product;
import it.uniroma3.siw.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	public Product getProductById(Long id) {
		return this.productRepository.findById(id).get();
	}
	
	public List<Product> getProductsByName(String name){
		return this.productRepository.findByName(name);
	}
	
	public List<Product> getProductsByPrice(double price){
		return this.productRepository.findByPrice(price);
	}
	
	public List<Product> getProductsByType(String type){
		return this.productRepository.findByType(type);
	}
	
	public Product saveProduct(Product product) {
		return this.productRepository.save(product);
	}
	
	public void deleteProduct(Product product) {
		this.productRepository.delete(product);
	}

	public Iterable<Product> getAllProducts() {
		return this.productRepository.findAll();
	}
}
