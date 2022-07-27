package library;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.AuthourDAO;
import DAO.BookDAO;
import DAO.BookSellingDAO;
import dto.enter.bookSelling.BookSellingUpdateAsWrong;
import dto.enter.bookSelling.Selling;
import model.Authour;
import model.Book;
import model.BookSelling;
import util.Exceptions.NoResultFoundException;
import util.JPA.JPAUtil;

public class TestSellingBookDao {
	EntityManager em = null;
	private BookDAO bookDao = null;
	private AuthourDAO authourDao = null;
	private BookSellingDAO bookSellingDao = null;
	

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
	
	private void generateBookSellingsList(List<Authour> authours, List<Book> books, List<BookSelling> bookSelling){
		for(int i = 0; i < 5; i++) {	
			Authour authour = this.generateAuthour();
			authour.setName("AuthourName " + i);
			authours.add(authour);
			
			for(int k = 0; k < 3; k++) {
				Book book = this.generateBook(authour);
				book.setName("BookName " + i + " | k : " +k);
				books.add(book);
				
				for(int j = 0; j < 5; j++) {
					BookSelling bs = this.generateBookSelling(book);
					bs.setPrice(new BigDecimal(7000));
					bs.setClientName("BookSelling " + j);
					
					bookSelling.add(bs);
				}
			}
		
		}
	}
	
	
	@BeforeEach
	public void BeforeEach() {
		this.em = JPAUtil.getEntityManeger();
		this.bookDao = new BookDAO(em);
		this.authourDao = new AuthourDAO(em);
		this.bookSellingDao = new BookSellingDAO(em);
	}
	
	@AfterEach
	public void AfterEach() {
		this.em.getTransaction().rollback();
		this.em.close();
	}

	@Test
	public void shouldRegisterSelling() {
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		BookSelling bookSelling = this.generateBookSelling(book);
		
		this.em.getTransaction().begin();
		this.authourDao.insert(authour);
		this.bookDao.insert(book);
		this.bookSellingDao.insert(bookSelling);
		
		BookSelling bookSellingReturned = this.bookSellingDao.getBookSelling(bookSelling.getId());
		
		Assert.assertEquals(bookSelling.getClientName(), bookSellingReturned.getClientName());
		Assert.assertEquals(bookSelling.getBook().getName(), bookSellingReturned.getBook().getName());
		Assert.assertEquals(bookSelling.getBook().getAuthour().getName(), bookSellingReturned.getBook().getAuthour().getName());
	}
	
	@Test
	public void shouldUpdateSellingAsWrong() throws NoResultFoundException {
		Authour authour = this.generateAuthour();
		Book book = this.generateBook(authour);
		BookSelling bookSelling = this.generateBookSelling(book);
		
		this.em.getTransaction().begin();
		this.authourDao.insert(authour);
		this.bookDao.insert(book);
		
		this.bookSellingDao.insert(bookSelling);
		
		Integer id = bookSelling.getId();
		
		BookSellingUpdateAsWrong bsuw = new BookSellingUpdateAsWrong();
		System.out.println(id);
		bsuw.setBookSellingId(id);
		bsuw.setBookId(bookSelling.getBook().getId());
		bsuw.setClientName("new name");
		bsuw.setNewPrice(new BigDecimal("400"));
		
		Authour newAuthourForUpdate = this.generateAuthour();
		newAuthourForUpdate.setName("New authour name");
		Book newBookForUpdate = this.generateBook(newAuthourForUpdate);
		
		this.bookDao.insert(newBookForUpdate);
		
		bsuw.setBookId(newBookForUpdate.getId());
		
		this.bookSellingDao.update(bsuw.getBookSellingId(), bsuw);
		
		BookSelling bookSellingReturned = this.bookSellingDao.getBookSelling(bookSelling.getId());
		
		Assert.assertEquals(bookSellingReturned.getClientName(), bsuw.getClientName());
		Assert.assertEquals(bookSellingReturned.getBook().getAuthour().getName(), bookSellingReturned.getBook().getAuthour().getName());
	}
	
	@Test
	public void shouldGetSellingByBook() {
		List<Authour> authours = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<BookSelling> bookSellings = new ArrayList<>();
		
		this.generateBookSellingsList(authours, books, bookSellings);

		this.em.getTransaction().begin();
		this.authourDao.insert(authours);
		this.bookDao.insert(books);
		this.bookSellingDao.insert(bookSellings);
		
		BookSelling bs = new BookSelling();
		Book bookToSearch = new Book();
		bookToSearch.setName("BookNa");
		bs.setBook(bookToSearch);
		
		List<Selling> sellingsWithSelectedBooks = this.bookSellingDao.selectByPartOfInfo(bs);
		Assert.assertTrue(sellingsWithSelectedBooks.size() > 0);
		sellingsWithSelectedBooks.forEach(item -> {
			System.out.println(item.getBook().getName());
			Assert.assertTrue(item.getBook().getName().contains(bs.getBook().getName()));
		});
	}
	
	@Test
	public void shouldGetSellingByAuthour() {
		List<Authour> authours = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<BookSelling> bookSellings = new ArrayList<>();
		
		this.generateBookSellingsList(authours, books, bookSellings);

		this.em.getTransaction().begin();
		this.authourDao.insert(authours);
		this.bookDao.insert(books);
		this.bookSellingDao.insert(bookSellings);
		
		BookSelling bs = new BookSelling();
		Authour authourToSearch = new Authour();
		authourToSearch.setName("AuthourName 3");
		Book book = new Book();
		book.setAuthour(authourToSearch);;
		bs.setBook(book);
		
		List<Selling> sellingsWithSelectedBooks = this.bookSellingDao.selectByPartOfInfo(bs);
		
		Assert.assertTrue(sellingsWithSelectedBooks.size() > 0);
		sellingsWithSelectedBooks.forEach(item -> {
			Assert.assertTrue(item.getAuthour().getName().contains(bs.getBook().getAuthour().getName()));
		});
	}
	
	@Test
	public void shouldNotGetSellingByAuthourBecauseAuthourNameIsNotFound() {
		List<Authour> authours = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<BookSelling> bookSellings = new ArrayList<>();
		
		this.generateBookSellingsList(authours, books, bookSellings);

		this.em.getTransaction().begin();
		this.authourDao.insert(authours);
		this.bookDao.insert(books);
		this.bookSellingDao.insert(bookSellings);
		
		BookSelling bs = new BookSelling();
		Authour authourToSearch = new Authour();
		authourToSearch.setName("Authour 3010101");
		Book book = new Book();
		book.setAuthour(authourToSearch);;
		bs.setBook(book);
		
		List<Selling> sellingsWithSelectedBooks = this.bookSellingDao.selectByPartOfInfo(bs);
		
		Assert.assertFalse(sellingsWithSelectedBooks.size() > 0);
	}
	
	public void shouldGetSellingByPeriod() {
		List<Authour> authours = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<BookSelling> bookSellings = new ArrayList<>();
		
		this.generateBookSellingsList(authours, books, bookSellings);

		this.em.getTransaction().begin();
		this.authourDao.insert(authours);
		this.bookDao.insert(books);
		this.bookSellingDao.insert(bookSellings);
		
		LocalDate startPeriodLocal = LocalDate.of(2022, 3, 20);
		Date startPeriod = Date.valueOf(startPeriodLocal);
		
		LocalDate endPeriodLocal = LocalDate.of(2022, 8, 16);
		Date endOfPeriod = Date.valueOf(endPeriodLocal);
		
		List<BookSelling> sellingsWithinThisPeriod = this.bookSellingDao.selectByPeriod(startPeriod, endOfPeriod);
		
		Assert.assertTrue(sellingsWithinThisPeriod.size() > 0);
		sellingsWithinThisPeriod.forEach(item -> {
			
			if(item.getCreate() == null) {
				System.out.println("null =============================================== ");
			} else {
				Assert.assertTrue(item.getBook().getCreate().compareTo(startPeriod) >= 0 && item.getCreate().compareTo(endOfPeriod) <= 0);
				System.err.println("pass");
			}
		});
	}

}
