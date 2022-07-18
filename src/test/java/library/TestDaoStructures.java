package library;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import DAO.AuthourDAO;
import DAO.BookDAO;
import DAO.BookSellingDAO;
import model.Authour;
import model.Book;
import model.BookSelling;
import util.JPA.JPAUtil;

public class TestDaoStructures {
	
	@Test
	public void shouldDoOk() {
		Authour authour = new Authour();
		
		authour.setName("teste");
		Assert.assertEquals(authour.getName(),"teste");
	}
	
	private Authour generateAuthour() {
		Authour authour = new Authour();
		
		Instant i = Instant.parse("1992-02-03T11:25:30.00Z");
		
		authour.setAge(30);
		authour.setBirthday(i);
		authour.setName("person name");
		authour.setSurname("person surname");
		
		return authour;
	}
	
	private Book generateBook(Authour authour) {
		Book book = new Book();
		book.setName("book name");
		book.setAuthour(authour);
		
		return book;
	}
	
	private BookSelling generateBookSelling(Book book) {
		BookSelling bookSelling = new BookSelling();
		bookSelling.setBook(book);
		bookSelling.setClientName("clinet name");
		bookSelling.setPrice(new BigDecimal("50.30"));
		
		return bookSelling;
	}
	
	@Test
	public void shouldReturnInsertedBook() {
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDao = new BookDAO(em);
		em.getTransaction().begin();
		
		AuthourDAO auhourDao = new AuthourDAO(em);
		auhourDao.insert(authour);
		bookDao.insert(book);
		
		int bkId = book.getId();
		Book bookGetter = bookDao.getBook(bkId);
		
		Assert.assertEquals(bkId, bookGetter.getId());
		Assert.assertEquals(book.getName(), bookGetter.getName());
		Assert.assertEquals(book.getAuthour(), bookGetter.getAuthour());
		
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldInsertAuthour() {
		Authour authour = this.generateAuthour();
		
		EntityManager em = JPAUtil.getEntityManeger();
		AuthourDAO authourDao = new AuthourDAO(em);
		em.getTransaction().begin();
		
		authourDao.insert(authour);
		
		int auId = authour.getId();
		Authour authourGetter = authourDao.getAuthour(auId);
		
		Assert.assertEquals(auId, authourGetter.getId());
		Assert.assertEquals(authour.getName(), authourGetter.getName());
		Assert.assertEquals(authour.getSurname(), authourGetter.getSurname());
		Assert.assertEquals(authour.getAge(), authourGetter.getAge());
		Assert.assertEquals(authour.getBirthday(), authourGetter.getBirthday());
		
		System.out.println(authour.getId());
		
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void shouldInsertBookSelling() {
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		BookSelling bookSelling = this.generateBookSelling(book);
		
		EntityManager em = JPAUtil.getEntityManeger();
		AuthourDAO authourDao = new AuthourDAO(em);
		BookDAO bookDao = new BookDAO(em);
		BookSellingDAO bookSellingDao = new BookSellingDAO(em);
		
		em.getTransaction().begin();
		
		authourDao.insert(authour);
		bookDao.insert(book);
		bookSellingDao.insert(bookSelling);
		
		int bookSellingId = bookSelling.getId();
		BookSelling bookSellingGetter = bookSellingDao.getBookSelling(bookSellingId);
		
		Assert.assertEquals(bookSelling.getClientName(), bookSellingGetter.getClientName());
		Assert.assertEquals(bookSelling.getBook(), bookSellingGetter.getBook());
		Assert.assertEquals(bookSelling.getPrice(), bookSellingGetter.getPrice());
		Assert.assertEquals(bookSelling.getCreate(), bookSellingGetter.getCreate());
		Assert.assertEquals(bookSelling.getBook().getAuthour().getName(), bookSellingGetter.getBook().getAuthour().getName());
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	
}
