package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Product;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CommentService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ProductService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/comments/{id}/edit")
	public String editCommentForm(@PathVariable Long id, 
	                              @AuthenticationPrincipal UserDetails userDetails, 
	                              Model model) {
	    Comment comment = commentService.getCommentById(id);

	    // Controllo: solo l'autore può modificare
	    if (!comment.getAuthor().getCredentials().getUsername().equals(userDetails.getUsername())) {
	        return "redirect:/product/" + comment.getProduct().getId();
	    }

	    model.addAttribute("comment", comment);
	    return "editComment"; // template Thymeleaf per il form di modifica
	}

	@PostMapping("/comments/{id}/edit")
	public String updateComment(@PathVariable Long id,
	                            @ModelAttribute("comment") Comment updatedComment,
	                            @AuthenticationPrincipal UserDetails userDetails) {
	    Comment comment = commentService.getCommentById(id);

	    // Controllo: solo l'autore può modificare
	    if (!comment.getAuthor().getCredentials().getUsername().equals(userDetails.getUsername())) {
	        return "redirect:/product/" + comment.getProduct().getId();
	    }

	    comment.setText(updatedComment.getText());
	    commentService.saveComment(comment);

	    return "redirect:/product/" + comment.getProduct().getId();
	}


	@GetMapping("/comments/{id}/delete")
	public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
		// recupero commento per ottenere anche il product associato
		Comment comment = commentService.getCommentById(id);
		Long productId = comment.getProduct().getId();

		commentService.deleteComment(comment);

		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			// redirect alla pagina di update del prodotto
			return "redirect:/admin/formUpdateProduct/" + productId;
		}
		return "redirect:/product/" + productId;
	}

	@PostMapping("/product/{productId}/comments")
	public String addComment(@PathVariable Long productId, @ModelAttribute("newComment") Comment newComment,
			@AuthenticationPrincipal UserDetails userDetails) {

		// forzo il login se non si è registrati
		if (userDetails == null)
			return "redirect:/login";

		// recupera il prodotto
		Product product = productService.getProductById(productId);

		// collego il commento al prodotto
		newComment.setProduct(product);
		
		// collego credenziali e user al commento 
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		newComment.setAuthor(user);

		// salva
		commentService.saveComment(newComment);

		// redirect alla pagina del prodotto
		return "redirect:/product/" + productId;
	}

}
