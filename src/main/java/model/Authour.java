package model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Authour {
	
	@Transient
	private static final String tablePrefix = "au_";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name=Authour.tablePrefix + "id")
	private int id;
	@Column(name= Authour.tablePrefix + "name")
	private String name;
	@Column(name= Authour.tablePrefix + "surname")
	private String surname;
	@Column(name= Authour.tablePrefix + "age")
	private int age;
	@Column(name= Authour.tablePrefix + "birthday")
	private Timestamp birthday;
	@Column(name= Authour.tablePrefix + "update")
	private Timestamp update;
	@Column(name= Authour.tablePrefix + "create")
	private Timestamp create;
	@Column(name= Authour.tablePrefix + "status")
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
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
	
	
	
//	au_id int not null auto_increment,
//    au_name varchar(50) not null,
//    au_surname varchar(100),
//    au_age int,
//    au_birthday timestamp not null,
//    au_update timestamp on update now(),
//    au_create timestamp not null default now(),
//    au_status int not null default 1,
    
//    primary key(au_id)
}
