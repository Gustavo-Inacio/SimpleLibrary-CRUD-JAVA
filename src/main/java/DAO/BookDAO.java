package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Authour;
import model.Book;

public class BookDAO {
	private EntityManager em;
	private AuthourDAO authourDao ;
	
	public BookDAO(EntityManager em) {
		this.em = em;
		this.authourDao = new AuthourDAO(em);
	}
	
	public Book select(Integer id) {
		TypedQuery<Book> query = em.createQuery(
			"SELECT bk FROM Book as bk WHERE bk.id=:id AND bk.status=1",
			Book.class
		);
		
		return query.setParameter("id", id).getSingleResult();
	}
	
	public void insert(Book book){
		this.em.persist(book);
	}
	
	public void insert(List<Book> bookList){
		bookList.forEach(book -> {
			this.insert(book);
		});
	}
	
	public Book getBook(int id) {
		return this.getBook(id, false);
	}
	
	public Book getBook(int id, Boolean allAtatus) {
		String query = "SELECT bk FROM Book as bk WHERE bk.id=:id ";
		if(!allAtatus) query += " AND bk.status=1";
		
		TypedQuery<Book> tpQuery = this.em.createQuery(
			query,
			Book.class
		);
		
		return tpQuery.setParameter("id", id).getSingleResult();
	}
	
	public List<Book> selectAll(){
		return this.selectAll(false);
	}
	public List<Book> selectAll(Boolean getAllStatus){
		String query = "SELECT bk FROM Book as bk WHERE 1=1 ";
		if(!getAllStatus) query += " AND bk.status=1";
		return this.em.createQuery(query, Book.class).getResultList();
	}
	
	
	public List<Book> selectByAuthour(Authour authour){
		return this.selectByAuthour(authour, false);
	}
	
	public List<Book> selectByAuthour(Authour authour, boolean selectAllStatus) {
		String query = "SELECT bk FROM Book as bk inner join Authour as au ON bk.authour=au.id WHERE 1=1";
		
		if(authour.getId() != null) {
			query += " AND au.id=:id";
		} else {
			if(authour.getName() != null) {
				query += " AND au.name= :name";
			}
			if(authour.getAge() != null) {
				query += " AND au.age= :age";
			}
			if(authour.getSurname() != null) {
				query += " AND au.surname= :surname";
			}
		}
		if(selectAllStatus) {
			query += " AND au.status= 1 AND bk.status=1";
		}
		TypedQuery<Book> typedQuery = this.em.createQuery(
			query,
			Book.class
		);
		if(authour.getId() != null) {
			typedQuery.setParameter("id", authour.getId());
		} else {
			if(authour.getName() != null) {
				typedQuery.setParameter("name", authour.getName());
			}
			if(authour.getAge() != null) {
				typedQuery.setParameter("age", authour.getAge());
			}
			if(authour.getSurname() != null) {
				typedQuery.setParameter("surname", authour.getSurname());
			}
		}
		
		return typedQuery.getResultList();
	}
	
	public List<Book> selectByPartOfInfo(Book book){
		String query = "SELECT bk FROM Book bk inner join "
				+ "Authour au ON bk.authour = au.id WHERE 1=1 ";
		
		if(book.getId() != null) {
			query += " AND bk.id=:book_id";
		} else {
			if(book.getName() != null) {
				query += " AND bk.name=:book_name";
			}
			if(book.getAuthour() != null) {
				Authour authour = book.getAuthour();
				if(authour.getId() != null) {
					query += " AND au.id=:authour_id";
				} else {
					if(authour.getName() != null) {
						query += " AND au.name= :name";
					}
					if(authour.getAge() != null) {
						query += " AND au.age= :age";
					}
					if(authour.getSurname() != null) {
						query += " AND au.surname= :surname";
					}
				}
			}
		}
		
		TypedQuery<Book> typedQuery = this.em.createQuery(
			query,
			Book.class
		);
		
		if(book.getId() != null) {
			typedQuery.setParameter("book_id", book.getId());
		} else {
			if(book.getName() != null) {
				typedQuery.setParameter("book_name", book.getName());
			}
			if(book.getAuthour() != null) {
				Authour authour = book.getAuthour();
				if(authour.getId() != null) {
					typedQuery.setParameter("authour_id", authour.getId());
				} else {
					
					if(authour.getName() != null) {
						typedQuery.setParameter("name", authour.getName());
					}
					if(authour.getAge() != null) {
						typedQuery.setParameter("age", authour.getAge());
					}
					if(authour.getSurname() != null) {
						typedQuery.setParameter("surname", authour.getSurname());
					}
				}
			}
		}
		
		return typedQuery.getResultList();
	}

	public void remove(Integer id) {
		Query query = this.em.createQuery(
			"UPDATE Book bk set bk.status=0 Where bk.id=:id"
		);
		
		query.setParameter("id", id).executeUpdate();
		
	}
	
	

}
