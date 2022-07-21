package model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Authour {
	
	@Transient
	private static final String tablePrefix = "au_";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name=Authour.tablePrefix + "id")
	private Integer id;
	@Column(name= Authour.tablePrefix + "name", nullable = false) 
	private String name;
	@Column(name= Authour.tablePrefix + "surname")
	private String surname;
	@Column(name= Authour.tablePrefix + "age")
	private int age;
	@Column(name= Authour.tablePrefix + "birthday", nullable = false)
	private Instant birthday;
	@Column(name= Authour.tablePrefix + "update")
	private Timestamp update;
	@Column(name= Authour.tablePrefix + "create", insertable = false, updatable = false)
	private Timestamp create; 
	@Column(name= Authour.tablePrefix + "status", nullable = false)
	private int status = 1;
	
	@OneToMany(mappedBy = "authour")
	private Set<Book> bookList;
	
	public Authour() {
		// TODO Auto-generated constructor stub
	}
	
	public Authour(String name, String surname, int age, Instant birthday) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.birthday = birthday;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Instant getBirthday() {
		return birthday;
	}
	public void setBirthday(Instant birthday) {
		this.birthday = birthday;
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
	
	public void setAllPermitedFields(Authour authour) {
		this.setAge(authour.getAge());
		this.setName(authour.getName());
		this.setSurname(authour.getSurname());
		this.setBirthday(authour.getBirthday());
	}
}
