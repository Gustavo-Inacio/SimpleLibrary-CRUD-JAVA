package model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;

@Entity
public class Book {
	
	@Transient
	private static final String tablePrefix = "bk_";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name=Book.tablePrefix + "id")
	private int id;
	@Column(name= Book.tablePrefix + "name")
	private String name;
	
	@Column(name = Book.tablePrefix + "authour_fk") @ManyToOne(fetch = FetchType.LAZY)
	private Authour authour;
	
	@Column(name= Book.tablePrefix + "update")
	private Timestamp update;
	@Column(name= Book.tablePrefix + "create")
	private Timestamp create;
	@Column(name= Book.tablePrefix + "status")
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Authour getAuthour() {
		return authour;
	}
	public void setAuthour(Authour authour) {
		this.authour = authour;
	}
	public Timestamp getUpdate() {
		return update;
	}
	
	public Timestamp getCreate() {
		return create;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
