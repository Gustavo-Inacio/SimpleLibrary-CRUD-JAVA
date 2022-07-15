package library;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import DAO.AuthourDAO;
import DAO.BookDAO;
import model.Authour;
import model.Book;
import util.JPA.JPAUtil;

public class TestDaoStructures {
	
	@Test
	public void shouldDoOk() {
		Authour authour = new Authour();
		
		authour.setName("teste");
		Assert.assertEquals(authour.getName(),"teste");
	}
	
	
	public void shouldInsertAndListBookFromDatabase() {
		Book book = new Book();
		Authour authour = new Authour();
		
		Instant i = Instant.parse("1992-02-03T11:25:30.00Z");
		
		authour.setAge(30);
		authour.setBirthday(i);
		authour.setName("person name");
		authour.setSurname("person surname");
		
		book.setName("book name");
		book.setAuthour(authour);
		
		EntityManager em = JPAUtil.getEntityManeger();
		BookDAO bookDao = new BookDAO(em);
		em.getTransaction().begin();
		
		bookDao.insert(book);
		
		em.getTransaction().commit();
		em.close();
		
	}
	
	@Test
	public void shouldInsertAuthour() {
		Authour authour = new Authour();
		
		Date date = new Date(1992, 2, 3);
		Instant i = Instant.parse("1992-02-03T11:25:30.00Z");
		
		authour.setAge(30);
		authour.setBirthday(i);
		authour.setName("person name");
		authour.setSurname("person surname");
		
		EntityManager em = JPAUtil.getEntityManeger();
		AuthourDAO authourDao = new AuthourDAO(em);
		em.getTransaction().begin();
		
		authourDao.insert(authour);
		
		em.getTransaction().commit();
		em.close();
	}
	
	
}
