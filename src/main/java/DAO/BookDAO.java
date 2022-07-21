package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import model.Authour;
import model.Book;

public class BookDAO {
	private EntityManager em;
	private AuthourDAO authourDao ;
	
	public BookDAO(EntityManager em) {
		this.em = em;
		this.authourDao = new AuthourDAO(em);
	}
	
	public void insert(Book book){
		
		
		this.em.persist(book);
	}
	
	public void insert(List<Book> bookList){
		bookList.forEach(book -> {
			System.out.println(book.getName());
			this.insert(book);
		});
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
	
	public List<Book> selectByAuthour(Authour authour, boolean selectAllStatus){
		return this.selectByAuthourPrivate(authour, selectAllStatus);
	}
	
	public List<Book> selectByAuthour(Authour authour){
		return this.selectByAuthourPrivate(authour, false);
	}

	private List<Book> selectByAuthourPrivate(Authour authour, boolean selectAllStatus) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Book> query = builder.createQuery(Book.class);
		Root<Book> from = query.from(Book.class);
		from.join("authour");
		
		Predicate filters = builder.and();
//		if(authour.getId() != null) {
//			filters = builder.and(filters, builder.equal(from.get("authour.id"), authour.getId()));
//		}
//		else {
//			if(authour.getName() != null) {
//				filters = builder.and(filters, builder.equal(from.get("authour.name"), authour.getName()));
//			}
//			if(authour.getSurname() != null) {
//				filters = builder.and(filters, builder.equal(from.get("authour.surname"), authour.getSurname()));
//			}
//			if(authour.getBirthday() != null) {
//				filters = builder.and(filters, builder.equal(from.get("authour.birthday"), authour.getBirthday()));
//			}
//			if(authour.getAge() != null) {
//				filters = builder.and(filters, builder.equal(from.get("authour.age"), authour.getAge()));
//			}
//		}
		
		if(!selectAllStatus) {
			filters = builder.and(filters, builder.equal(from.get("status"), 1));
		}
		
		query.where(filters);
		
		
		return em.createQuery(query).getResultList();
	}
}
