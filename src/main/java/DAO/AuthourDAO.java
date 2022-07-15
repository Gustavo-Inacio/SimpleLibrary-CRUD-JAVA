package DAO;

import java.util.List;

import javax.persistence.EntityManager;

import model.Authour;
import model.Book;

public class AuthourDAO {
	private EntityManager em;
	
	public AuthourDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(Authour authour){
		this.em.persist(authour);
	}
	
	public List<Authour> selectAll(){
		String query = "SELECT bk Authour book as bk";
		return this.em.createQuery(query, Authour.class).getResultList();
	}
}
