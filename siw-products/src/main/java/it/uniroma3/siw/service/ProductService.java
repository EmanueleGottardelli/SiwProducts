package it.uniroma3.siw.service;

import java.util.ArrayList;
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
	
	public List<Product> searchProducts(String name, String type, Double price) {
	    if (name != null && !name.isBlank()) {
	        return this.getProductsByName(name);
	    }
	    if (type != null && !type.isBlank()) {
	        return this.getProductsByType(type);
	    }
	    if (price != null) {
	        return this.getProductsByPrice(price);
	    }
	    return (List<Product>) this.productRepository.findAll();
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
	
	public void addProductSimilar(Long productId,Long similarId) {
		Product product = getProductById(productId);
		Product similar = getProductById(similarId);
		
		product.getSimilarProducts().add(similar);
		similar.getSimilarProducts().add(product);
		
		saveProduct(product);
	}
	
	public void deleteProductSimilar(Long productId, Long similarId) {
		Product product = getProductById(productId);
		Product similar = getProductById(similarId);
		
		product.getSimilarProducts().remove(similar);
		similar.getSimilarProducts().remove(product);
		
		saveProduct(product);
	}

	public List<Product> getAllProducts() {
		List<Product> result=new ArrayList<>();
		Iterable<Product> iterable = this.productRepository.findAll();
		
		for(Product product : iterable)
			result.add(product);
		
		return result;
	}
}
