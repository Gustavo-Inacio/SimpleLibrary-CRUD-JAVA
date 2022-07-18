package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Book;
import model.BookSelling;

public class BookSellingDAO {
	private EntityManager em;
	
	public BookSellingDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(BookSelling bookSelling){
		this.em.persist(bookSelling);
	}
	
	public BookSelling getBookSelling(int id) {
		TypedQuery<BookSelling> query = this.em.createQuery(
			"SELECT bs FROM BookSelling as bs WHERE bs.id=:id",
			BookSelling.class
		);
		
		return query.setParameter("id", id).getSingleResult();
	}
	
	public List<BookSelling> selectAll(){
		String query = "SELECT bs FROM book as bs";
		return this.em.createQuery(query, BookSelling.class).getResultList();
	}
}
