package library;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import DAO.AuthourDAO;
import DAO.BookDAO;
import model.Authour;
import model.Book;
import model.BookSelling;
import util.JPA.JPAUtil;

public class BookDaoTest {
	private Authour generateAuthour() {
		Authour authour = new Authour();
		
		Instant i = Instant.parse("1992-02-03T11:25:30.00Z");
		Instant instant = Instant.now();
		
		
		authour.setAge(30);
		//authour.setBirthday(i);
		authour.setBirthday(instant.minus(20 * 366, ChronoUnit.DAYS));
		authour.setName("auto generated random name " + new Random().nextInt(100));
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
	public void shouldGetByAuthour(){
		Instant instant = Instant.now();
		Authour authourToSeachFor = new Authour("NameGene" + Instant.now().toString(), "sunameofthe", 20, instant.minus(20 * 366, ChronoUnit.DAYS));
		
		Book book1 = new Book();
		book1.setAuthour(authourToSeachFor);
		book1.setName("Book name to search");
		
		Book book2 = new Book();
		book2.setAuthour(authourToSeachFor);
		book2.setName("name 2");
		
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDAO = new BookDAO(em);
		
		em.getTransaction().begin();
		bookDAO.insert(book);
		bookDAO.insert(book2);
		bookDAO.insert(book1);
		
		Authour authourUsedToSearch = new Authour();
		authourUsedToSearch.setName(authourToSeachFor.getName());
		authourUsedToSearch.setSurname(authourToSeachFor.getSurname());
		authourUsedToSearch.setAge(authourToSeachFor.getAge());
		
		List<Book> selectedByAuthour = bookDAO.selectByAuthour(authourUsedToSearch);
		Assert.assertTrue(selectedByAuthour.size() >= 2);
		
		selectedByAuthour.stream().forEach(bookGetter -> {
			Assert.assertEquals(book1.getAuthour().getName(), bookGetter.getAuthour().getName());
		});
		
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void shouldReturnInsertedBook() {
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDao = new BookDAO(em);
		AuthourDAO auhourDao = new AuthourDAO(em);
		em.getTransaction().begin();
		
		auhourDao.insert(authour);
		bookDao.insert(book);
		
		Integer bkId = book.getId();
		Book bookGetter = bookDao.getBook(bkId);
		
		Assert.assertEquals(bkId, bookGetter.getId());
		Assert.assertEquals(book.getName(), bookGetter.getName());
		Assert.assertEquals(book.getAuthour(), bookGetter.getAuthour());
		
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldGetByPartOfInfo(){
		Authour authour = this.generateAuthour();
		Book insertBook = this.generateBook(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDao = new BookDAO(em);
		
		Book bookWithPartOfInfo = new Book();
		em.getTransaction().begin();
		bookDao.insert(insertBook);
		
		bookWithPartOfInfo.setName(insertBook.getName());
		List<Book> bookList = bookDao.selectByPartOfInfo(bookWithPartOfInfo);
		Assert.assertTrue(bookList.size() > 0);
		
		assertTrue(bookList.contains(insertBook));
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void ShouldRemoveApplingStatusZero(){
		Authour authour = this.generateAuthour();
		Book insertBook = this.generateBook(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDao = new BookDAO(em);
		em.getTransaction().begin();
		
		bookDao.insert(insertBook);
		Integer id = insertBook.getId();
		bookDao.remove(id);
		
		Book returnedBook = bookDao.getBook(id);
		Assert.assertNull(returnedBook);
		
		em.getTransaction().rollback();
		em.close();
	}
//	
//	public void shouldUpdate(Authour authour){
//		
//	}
//	
//	public List<Book> shouldSelectAllEvenThoseWithStatusZero(Authour authour){
//		
//	}
}
