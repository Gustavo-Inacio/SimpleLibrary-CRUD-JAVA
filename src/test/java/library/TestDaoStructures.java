package library;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
		
		Assert.assertEquals(authour.getName(), authourGetter.getName());
		Assert.assertEquals(authour.getSurname(), authourGetter.getSurname());
		Assert.assertEquals(authour.getAge(), authourGetter.getAge());
		Assert.assertEquals(authour.getBirthday(), authourGetter.getBirthday());
		
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void shouldRemoveAuthour() {
		Authour authour = this.generateAuthour();
		EntityManager em = JPAUtil.getEntityManeger();
		AuthourDAO authourDao = new AuthourDAO(em);
		
		em.getTransaction().begin();
		authourDao.insert(authour);
		authourDao.remove(authour.getId());
		System.out.println(authour);
		int status = authourDao.getAuthour(authour.getId()).getStatus();
		
		em.getTransaction().rollback();
		em.close();
		
		Assert.assertEquals(0, status);
	}
	
	@Test(expected= PersistenceException.class)
	public void shouldNotInsertAuthourBecauseDataIsNull() {
		Authour authour = this.generateAuthour();
		authour.setName(null);
		authour.setBirthday(null);
		
		EntityManager em = JPAUtil.getEntityManeger();
		AuthourDAO authourDao = new AuthourDAO(em);
		em.getTransaction().begin();
		
		authourDao.insert(authour);
		
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
	
	@Test
	public void shouldReturnAuthourDataByPartOfName() {
		EntityManager em = JPAUtil.getEntityManeger();
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour authour = this.generateAuthour();
		authour.setName("Mickel");
		
		String partOfName = "Mi";
		
		em.getTransaction().begin();
		
		authourDAO.insert(authour);
		
		List<Authour> selectedAutour = authourDAO.getPartOfInfo(authour);
		
		List<Authour> authourVerified = selectedAutour.stream()
				.filter(elem -> elem.getName().startsWith(partOfName))
				.collect(Collectors.toList());
		
		Assert.assertEquals(selectedAutour, authourVerified);
		Assert.assertFalse(authourVerified.isEmpty());
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldReturnTwoAuthoursDataByPartOfName() {
		EntityManager em = JPAUtil.getEntityManeger();
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour authour = this.generateAuthour();
		Authour authour2 = this.generateAuthour();
		authour2.setName("Mijact");
		authour.setName("Mickel");
		
		String partOfName = "Mi";
		
		em.getTransaction().begin();
		
		authourDAO.insert(authour);
		authourDAO.insert(authour2);
		
		Authour authourForSearch = new Authour();
		authourForSearch.setName(partOfName);
		
		List<Authour> selectedAutour = authourDAO.getPartOfInfo(authourForSearch);
		
		List<Authour> authourVerified = selectedAutour.stream()
				.filter(elem -> elem.getName().startsWith(partOfName))
				.collect(Collectors.toList());
		
		Assert.assertEquals(selectedAutour, authourVerified);
		Assert.assertTrue(selectedAutour.size() >= 2);
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldReturnOneAuthoursDataByPartOfName() {
		EntityManager em = JPAUtil.getEntityManeger();
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour authour = this.generateAuthour();
		Authour authour2 = this.generateAuthour();
		authour2.setName("Francis");
		authour.setName("Mickel");
		
		String partOfName = "Mi";
		
		em.getTransaction().begin();
		
		authourDAO.insert(authour);
		authourDAO.insert(authour2);
		
		Authour authourForSearch = new Authour();
		authourForSearch.setName(partOfName);
		
		List<Authour> selectedAutour = authourDAO.getPartOfInfo(authourForSearch);
		
		List<Authour> authourVerified = selectedAutour.stream()
				.filter(elem -> elem.getName().startsWith(partOfName))
				.collect(Collectors.toList());
		
		Assert.assertEquals(selectedAutour, authourVerified);
		Assert.assertTrue(selectedAutour.size() >= 1);
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldReturnOneAuthoursDataByNameSurnameAndBirthday() {
		EntityManager em = JPAUtil.getEntityManeger();
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour authour = this.generateAuthour();
		Authour authour2 = this.generateAuthour();
		authour2.setName("Francis");
		authour2.setSurname("Bacon");
		
		authour.setName("Mickel");
		
		Authour authourForSearch = new Authour();
		authourForSearch.setName("Mickel");
		authourForSearch.setBirthday(authour.getBirthday());
		authourForSearch.setSurname(authour.getSurname());
		
		em.getTransaction().begin();
		
		authourDAO.insert(authour);
		authourDAO.insert(authour2);
		
		
		List<Authour> selectedAutour = authourDAO.getPartOfInfo(authourForSearch);
		
		List<Authour> authourVerified = selectedAutour.stream()
				.filter(elem -> elem.getName().startsWith("Mi"))
				.collect(Collectors.toList());
		
		Assert.assertEquals(selectedAutour, authourVerified);
		Assert.assertTrue(selectedAutour.size() >= 1);
		
		em.getTransaction().rollback();
		em.close();
		
	}
	
	@Test
	public void shouldUpdateNameAndSurname() {
		EntityManager em = JPAUtil.getEntityManeger();
		
		AuthourDAO authourDAO = new AuthourDAO(em);
		Authour authour = this.generateAuthour();
		authour.setName("Name before update");
		authour.setSurname("surname before update");
		
		Authour authour2 = this.generateAuthour();
		authour2.setName("Name after update");
		authour2.setSurname("surname after update");
		
		em.getTransaction().begin();
		
		authourDAO.insert(authour);
		authour.setAllPermitedFields(authour2);
		
		Authour authourGetter = authourDAO.getAuthour(authour.getId());
		Assert.assertEquals(authourGetter.getName(), "Name after update");
		Assert.assertEquals(authourGetter.getSurname(), "surname after update");
		
		em.getTransaction().rollback();
		em.close();
	}
	
	
}
