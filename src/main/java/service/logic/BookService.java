package service.logic;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAO;
import model.Authour;
import model.Book;
import util.JPA.JPAUtil;

public class BookService {
	private BookDAO bookDao;
	
	public BookService(BookDAO bookDao) {
		this.bookDao = bookDao;
	}
	
	public BookService() {
		EntityManager em = JPAUtil.getEntityManeger();
		this.bookDao = new BookDAO(em);
	}
	
	public List<Book> getByAuthour(Authour authour){
		this.bookDao.selectByAuthour(authour);
	}
	
	public List<Book> getByPartOfInfo(Book book){
		
	}
	
	public List<Book> getAllStatus1(){
		
	}
	
	public void remove(Authour authour){
		
	}
	
	public void update(Authour authour){
		
	}
	
	public List<Book> selectAll(Authour authour){
		
	}
}
