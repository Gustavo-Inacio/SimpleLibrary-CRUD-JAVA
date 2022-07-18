package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Book;

public class BookDAO {
	private EntityManager em;
	
	public BookDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(Book book){
		this.em.persist(book);
	}
	
	public Book getBook(int id) {
		TypedQuery<Book> query = this.em.createQuery(
			"SELECT bk FROM Book as bk WHERE bk.id=:id",
			Book.class
		);
		
		return query.setParameter("id", id).getSingleResult();
	}
	
	public List<Book> selectAll(){
		String query = "SELECT bk FROM book as bk";
		return this.em.createQuery(query, Book.class).getResultList();
	}
}
