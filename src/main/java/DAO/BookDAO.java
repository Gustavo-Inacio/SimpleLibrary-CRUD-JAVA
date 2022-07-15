package DAO;

import java.util.List;

import javax.persistence.EntityManager;

import model.Book;

public class BookDAO {
	private EntityManager em;
	
	public BookDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(Book book){
		this.em.persist(book);
	}
	
	public List<Book> selectAll(){
		String query = "SELECT bk FROM book as bk";
		return this.em.createQuery(query, Book.class).getResultList();
	}
}
