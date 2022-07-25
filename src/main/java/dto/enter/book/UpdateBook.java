package dto.enter.book;

public class UpdateBook {
	private Integer id;
	private String name;
	private Integer authourId;
	
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAuthourId() {
		return authourId;
	}
	public void setAuthourId(Integer authourId) {
		this.authourId = authourId;
	}
	public Integer getId() {
		return id;
	}
	
}
