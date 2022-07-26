package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Authour;

public class AuthourDAO {
	private EntityManager em;
	
	public AuthourDAO(EntityManager em) {
		this.em = em;
	}
	
	public void insert(Authour authour){
		this.em.persist(authour);
	}
	
	public void insert(List<Authour> autours) {
		autours.forEach(item -> {
			this.insert(item);
		});
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

	public List<Authour> getPartOfInfo(Authour authour) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Authour> query = builder.createQuery(Authour.class);
		Root<Authour> from = query.from(Authour.class);
		
		Predicate filters = builder.and();
		if(authour.getId() != null) {
			filters = builder.and(filters, builder.equal(from.get("id"), authour.getId()));
		}else {
			if(authour.getName() != null) {
				filters = builder.and(filters, builder.like(from.get("name"), authour.getName() + "%"));
			}
			if(authour.getSurname() != null) {
				filters = builder.and(filters, builder.like(from.get("surname"), authour.getSurname() + "%"));
			}
			if(authour.getBirthday() != null) {
				filters = builder.and(filters, builder.equal(from.get("birthday"), authour.getBirthday()));
			}
		}
		
		query.where(filters);
		
		return em.createQuery(query).getResultList();
	}

	public void update(Authour authour) {
		Authour authourSelected = this.getAuthour(authour.getId());
		authour.setAllPermitedFields(authourSelected);
	}

	public List<Authour> selectAllStatus1() {
		String query = "SELECT au Authour as au WHERE au.status=1";
		return this.em.createQuery(query, Authour.class).getResultList();
	}
}
