package library.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
class TestLibrary {
	
	Library library;
	@Mock Patron patron;
	
	IBookHelper bookHelper;
	IPatronHelper patronHelper;
	ILoanHelper loanHelper;
	
	String lastName = "Smith";
	String firstName = "Bob";
	String email = "bob.smith@gmail.com";
	long phoneNo = 61412345678L;
	int id = 1;
	
	int loanLimit = 2;
	double maxFinesOwed = 1;


	@BeforeEach
	void setUp() throws Exception {
		patron = new Patron(lastName, firstName, email, phoneNo, id);
		library = new Library(bookHelper, patronHelper, loanHelper);
	}

	@AfterEach
	void tearDown() throws Exception {
		library = null;
	}

	@Test
	void testPatronCanBorrow() {
		//arrange all necessary preconditions and inputs
		verify(patron);
				
		//act on the object or method under test

		//assert that the expected results have occurred
		assertFalse(patron.hasOverDueLoans());
		assertEquals(loanLimit, patron.getNumberOfCurrentLoans());
		assertFalse(patron.getFinesPayable() >= maxFinesOwed);
	}
	
	

}
