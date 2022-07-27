package util.JPQL;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JPQLBuilder<T> {
	private String query;
	private String select;
	private String where;
	private Class<T> selectNew;
	public JPQLWhereBuilder whereBuilder = new JPQLWhereBuilder();
	private Map<String, Object> values = new HashMap<String, Object>();
	
	public JPQLBuilder() {
		
	}
	
	public JPQLBuilder(String select) {
		this.select = select;
	}
	
	public JPQLBuilder(String select, String where) {
		this.select = select;
		this.where = where;
	}
	
	public void select(String selectSt) {
		this.select = selectSt;
	}
	
	public void setSelectNewClass(Class<T> selectNew) {
		this.selectNew = selectNew;
	}
	
	public void where(String whereSt) {
		this.where = whereSt;
	}
	
	public Query generateQuery(EntityManager em, Class<T> type ) {
		Map<String, Object> params = this.whereBuilder.getValues();
		this.where = this.whereBuilder.getWhere();
		this.query = this.select + this.where;
		
		System.out.println(this.query);
		
		Query tdQuery = em.createQuery(
			this.query, type
		);
		
		for(Entry<String , Object> entry : params.entrySet()) {
			tdQuery.setParameter(entry.getKey(), entry.getValue());
		}
		
		return tdQuery;
	}
	

	

}
