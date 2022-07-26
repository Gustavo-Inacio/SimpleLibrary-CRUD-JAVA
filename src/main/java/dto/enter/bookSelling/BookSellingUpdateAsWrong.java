package dto.enter.bookSelling;

import java.math.BigDecimal;

public class BookSellingUpdateAsWrong {
	private Integer bookSellingId;
	private BigDecimal newPrice;
	private Integer bookId;
	private String clientName;
	
	
	
	public BookSellingUpdateAsWrong(Integer bookSellingId, BigDecimal newPrice, Integer bookId, String clientName) {
		this.bookSellingId = bookSellingId;
		this.newPrice = newPrice;
		this.bookId = bookId;
		this.clientName = clientName;
	}
	
	
	public BookSellingUpdateAsWrong() {
		// TODO Auto-generated constructor stub
	}


	public Integer getBookSellingId() {
		return bookSellingId;
	}
	public void setBookSellingId(Integer bookSellingId) {
		this.bookSellingId = bookSellingId;
	}
	public BigDecimal getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
