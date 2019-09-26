package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.entities.IBook.BookState;

class TestBook {

	Book book;
	BookState state;
	
	String author = "Tolken";
	String title = "Lord of the Rings";
	String callNo = "what is this";
	int id = 1;
	
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
    void testIsAvailableWhenAvailable() {
        //arrange
        //act
        //assert
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testIsAvailableWhenOnLoan() {
        //arrange
        book.borrowFromLibrary();
        //act
        //assert
        assertFalse(book.isAvailable());
    }
    
	@Test
	void testBorrowFromLibraryWhenAVAILABLE() {
		//arrange
		//act		
		book.borrowFromLibrary();
		//assert
		assertTrue(book.isOnLoan());
	}

	@Test
	void testBorrowFromLibraryWhenONLOAN() {
		//arrange
		book.borrowFromLibrary();
		assertTrue(book.isOnLoan());
		//act
		Executable e = () -> book.borrowFromLibrary();
		Throwable t = assertThrows(RuntimeException.class,e);
		//assert
		assertEquals("Book: cannot borrow while book is in state: ON_LOAN", t.getMessage());
	}
	
	@Test
	void testBorrowFromLibraryWhenDAMAGED() {
		//arrange
		book.borrowFromLibrary();
		book.returnToLibrary(true);
		assertTrue(book.isDamaged());
		//act
		Executable e = () -> book.borrowFromLibrary();
		Throwable t = assertThrows(RuntimeException.class,e);
		//assert
		assertEquals("Book: cannot borrow while book is in state: DAMAGED", t.getMessage());
	}
}
