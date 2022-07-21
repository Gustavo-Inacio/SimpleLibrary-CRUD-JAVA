package library;

import java.math.BigDecimal;
import java.rmi.server.RMIClassLoader;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

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
		
		authour.setAge(30);
		authour.setBirthday(i);
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
	
	@org.junit.jupiter.api.Test
	public void shouldGetByAuthour(){
		EntityManager em = JPAUtil.getEntityManeger();
		
		List<Book> bookList = new ArrayList<>();
		Instant instant = Instant.now();
		Authour authourToSeachFor = new Authour("NameGene" + Instant.now().toString(), "sunameofthe", 20, instant.minus(20 * 366, ChronoUnit.DAYS));
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour randomAuthour = this.generateAuthour();
		
		authourDAO.insert(authourToSeachFor);
		authourDAO.insert(randomAuthour);
		
		Book bookToBeReturned1 = new Book("book to be retured", authourToSeachFor);
		Book bookToBeReturned2 = new Book("book to be retured", authourToSeachFor);
		Book book1 = new Book("book name1", randomAuthour);
		Book book2 = new Book("book name2", randomAuthour);
		
		bookList.add(book1);
		bookList.add(bookToBeReturned1);
		bookList.add(bookToBeReturned2);
		bookList.add(book2);
		
		
		BookDAO bookDao = new BookDAO(em);
		bookDao.insert(bookList);
		List<Book> bookListSelected = bookDao.selectByAuthour(authourToSeachFor);
		
		Assert.assertEquals(2, bookListSelected.size());
		boolean asserted1 = false;
		boolean asserted2 = false;
		
		for (Book book : bookListSelected) {
			if(book.equals(bookToBeReturned2))
				asserted2 = true;
			
			if(book.equals(bookToBeReturned1))
				asserted2 = true;
		}
		
		Assert.assertTrue(asserted1);
		Assert.assertTrue(asserted2);
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
//	public List<Book> shouldGetByPartOfInfo(Book book){
//		
//	}
//	
//	public List<Book> shouldGettAllStatus1(){
//		
//	}
//	
//	public void ShouldRemoveApplingStatusZero(Authour authour){
//		
//	}
//	
//	public void shouldUpdate(Authour authour){
//		
//	}
//	
//	public List<Book> shouldSelectAllEvenThoseWithStatusZero(Authour authour){
//		
//	}
}
