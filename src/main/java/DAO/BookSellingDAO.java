package DAO;

import static org.mockito.ArgumentMatchers.startsWith;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dto.enter.bookSelling.BookSellingUpdateAsWrong;
import dto.enter.bookSelling.Selling;
import model.Authour;
import model.Book;
import model.BookSelling;
import util.Exceptions.NoResultFoundException;
import util.JPQL.JPQLBuilder;

public class BookSellingDAO {
	private EntityManager em;
	
	public BookSellingDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(BookSelling bookSelling){
		this.em.persist(bookSelling);
	}
	
	public void insert(List<BookSelling> bookSellings) {
		bookSellings.forEach(item -> {
			this.insert(item);
		});
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

	public void update(Integer id, BookSellingUpdateAsWrong sellingUpdated) throws NoResultFoundException {
		BookSelling bs;
		try {
			bs = this.getBookSelling(id);
		} catch (NoResultException e) {
			throw new NoResultFoundException("The system has not found a selling with the id: " + id);
		}
		
		if(sellingUpdated.getClientName() != null) {
			bs.setClientName(sellingUpdated.getClientName());
		}
		
		if(sellingUpdated.getNewPrice() != null) {
			bs.setPrice(sellingUpdated.getNewPrice());
		}
		
		if(sellingUpdated.getBookId() != null) {
			try {
				bs = this.getBookSelling(id);
				BookDAO bookDao = new BookDAO(this.em);
				
				Book bookToUpdate = bookDao.getBook(sellingUpdated.getBookId());
				bs.setBook(bookToUpdate);
			} catch (NoResultException e) {
				throw new NoResultFoundException("The system has not found a book with the id: " + id);
			}
		}
		
	}
	
	public List<Selling> selectByPartOfInfo(BookSelling bs) {
		String JPQLSelect = "SELECT new dto.enter.bookSelling.Selling(bs, bs.book, bs.book.authour) FROM BookSelling bs";
		JPQLBuilder<Selling> builder = new JPQLBuilder<>(JPQLSelect);
		
		if(bs.getBook() != null) {
			Book thisBook = bs.getBook();
			if(bs.getBook().getId() != null) {
				builder.whereBuilder.and("bs.book.id=:book_id");
				builder.whereBuilder.addParam("book_id", thisBook.getId());
			} else {
				if(thisBook.getName() != null) {
					builder.whereBuilder.and("bs.book.name LIKE :book_name");
					builder.whereBuilder.addParam("book_name", thisBook.getName() + "%");
				}
				
				if(thisBook.getCreate() != null) {
					builder.whereBuilder.and("bs.book.create=:book_create");
					builder.whereBuilder.addParam("book_create", thisBook.getCreate());
				}
				
				if(thisBook.getAuthour() != null) {
					Authour thisAuthour = thisBook.getAuthour();
					if(thisAuthour.getId() != null) {
						builder.whereBuilder.and("bs.book.authour.id=:authour_id");
						builder.whereBuilder.addParam("authour_id", thisAuthour.getId());
					} else {
						if(thisAuthour.getName() != null) {
							builder.whereBuilder.and("bs.book.authour.name LIKE :authour_name");
							builder.whereBuilder.addParam("authour_name", thisAuthour.getName() + "%");
						}
						
						if(thisAuthour.getAge() != null) {
							builder.whereBuilder.and("bs.book.authour.age=:authour_age");
							builder.whereBuilder.addParam("authour_age", thisAuthour.getAge());
						}
					}
				}
			}
		}
		
		List<Selling> list = builder.generateQuery(em, Selling.class).getResultList();
	
		return list;
	}

	public List<BookSelling> selectByPeriod(Date startPeriod, Date endOfPeriod) {
		TypedQuery<BookSelling> tpQuery = this.em.createQuery(
			"SELECT bs FROM BookSelling as bs WHERE bs.status=1 AND bs.create >= :start AND bs.create <= :final",
			BookSelling.class
		);
		
		System.out.println(startPeriod);
		
		return tpQuery
				.setParameter("start", startPeriod)
				.setParameter("final", endOfPeriod)
				.getResultList();
	}

	
}
