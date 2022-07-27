package model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Book {
	
	@Transient
	private static final String tablePrefix = "bk_";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name=Book.tablePrefix + "id")
	private Integer id;
	@Column(name= Book.tablePrefix + "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
	@JoinColumn(foreignKey = @ForeignKey(name= Book.tablePrefix + "authour_fk"), name=Book.tablePrefix + "authour_fk", nullable=false)
	private Authour authour;
	
	@Column(name= Book.tablePrefix + "update")
	private Timestamp update;
	@Column(name= Book.tablePrefix + "create",  insertable = false, updatable = false)
	private Timestamp create;
	@Column(name= Book.tablePrefix + "status")
	private Integer status = 1;
	
	public Book() {
		
	}
	
	public Book(String name, Authour authour) {
		this.name = name;
		this.authour = authour;
	}
	public Integer getId() {
		return id;
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
