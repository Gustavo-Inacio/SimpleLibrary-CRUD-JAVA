package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Authour;

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

	public Authour getAuthour(int auId) {
		TypedQuery<Authour> query = em.createQuery(
			"SELECT au FROM Authour as au WHERE au.id=:id", 
			Authour.class	
		);
		return query.setParameter("id", auId).getSingleResult();
	}
	
	public void remove(Integer id) {
		Authour authour = this.getAuthour(id);
		authour.setStatus(0);
		System.out.println(id);
		
		
	}
}
