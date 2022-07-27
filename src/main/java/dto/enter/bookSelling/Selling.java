package dto.enter.bookSelling;

import model.Authour;
import model.Book;
import model.BookSelling;

public class Selling {

	private Book book;
	private BookSelling bookSelling;
	private Authour authour;
	
	public Selling(BookSelling bs) {
		this.bookSelling = bs;
	}
	
	public Selling(BookSelling bookSelling, Book book) {
		this.book = book;
		this.bookSelling = bookSelling;
	}
	
	public Selling(Book book, Authour authour) {
		this.book = book;
		this.authour = authour;
	}
	
	public Selling(BookSelling bookSelling, Book book, Authour authour ) {
		this.book = book;
		this.bookSelling = bookSelling;
		this.authour = authour;
	}
	
	public Selling(Book book, Authour authour, BookSelling bookSelling) {
		this.book = book;
		this.bookSelling = bookSelling;
		this.authour = authour;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public BookSelling getBookSelling() {
		return bookSelling;
	}
	public void setBookSelling(BookSelling bookSelling) {
		this.bookSelling = bookSelling;
	}
	public Authour getAuthour() {
		return authour;
	}
	public void setAuthour(Authour authour) {
		this.authour = authour;
	}

}
