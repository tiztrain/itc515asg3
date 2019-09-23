package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.entities.IBook.BookState;

class TestBook {

	Book book;
	BookState state;
	
	String author;
	String title;
	String callNo;
	int id;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		book = new Book(author, title, callNo, id);
	}

	@AfterEach
	void tearDown() throws Exception {
		book = null;
	}

	@Test
	void testIsAvailable() {
		//arrange
		//act
		//assert
		assertTrue("The book is available", book.isAvailable());
	}
	
	@Test
	void testBorrowFromLibrary() {
		//arrange
		state = BookState.AVAILABLE;
		//act
		//Assert.ThrowsException<System.ArgumentException(()-> )>
		//assert
	}

}
