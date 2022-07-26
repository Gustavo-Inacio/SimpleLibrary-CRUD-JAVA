package util.JPQL;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JPQLWhereBuilder {
	private String where;
	private Map<String, Object> values = new HashMap<String, Object>();
	
	public JPQLWhereBuilder() {
		this.where = " WHERE 1=1 ";
	}

	public JPQLWhereBuilder(String where) {
		this.where = where;
	}
	
	public void addParam(String paramName, Object value) {
		values.put(paramName, value);
	}
	
	public void and(String wherePart) {
		this.where += " AND "+ wherePart;
	}
	
	public Map<String, Object> getValues() {
		return values;
	}

	public String getWhere() {
		return where;
	}

	public String generateWhere() {
		String replacedWhere = "";
		for(Entry<String , Object> entry : values.entrySet()) {
			this.where = this.where.replaceAll(entry.getKey(), entry.getValue().toString());
		}
		
		return this.where;
	}

}
