package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Comment;
import it.uniroma3.siw.repository.CommentRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@Transactional
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	@Transactional
	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
	}
	
	public Comment getCommentById(Long id) {
		return this.commentRepository.findById(id).orElse(null);
	}
}
