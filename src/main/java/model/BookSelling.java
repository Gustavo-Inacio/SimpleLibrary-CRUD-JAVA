package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class BookSelling {
	
	@Transient
	private static final String tablePrefix = "bs_";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name=BookSelling.tablePrefix + "id")
	private Integer id;
	@Column(name= BookSelling.tablePrefix + "client_name")
	private String clientName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name=BookSelling.tablePrefix + "book_fk", nullable=false)
	private Book book;
	
	@Column(name = BookSelling.tablePrefix + "price")
	private BigDecimal price;
	
	@Column(name= BookSelling.tablePrefix + "update")
	private Timestamp update;
	@Column(name= BookSelling.tablePrefix + "create", insertable = false, updatable = false)
	private Timestamp create;
	@Column(name= BookSelling.tablePrefix + "status")
	private Integer status = 1;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
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
