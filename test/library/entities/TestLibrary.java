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

import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;
import library.entities.helpers.PatronHelper;

class TestLibrary {

	static final int LOAN_LIMIT = 2;
	static final int LOAN_PERIOD = 2;
	static final double FINE_PER_DAY = 1.0;
	static final double MAX_FINES_OWED = 1.0;
	
	Library library;
	Patron patron;
	Loan loan;
	IBookHelper bookHelper;
	IPatronHelper patronHelper;
	ILoanHelper loanHelper;
	
	String lastName;
	String firstName;
	String email;
	long phoneNo;
	int id = 1;
	//IPatron patron;


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
		lastName = "Smith";
		firstName = "Bob";
		email = "bob.smith@gmail.com";
		phoneNo = 61412345678L;
		id = 1;
		patronHelper.makePatron(lastName, firstName, email, phoneNo, id);
		
		//act on the object or method under test

		library.patronCanBorrow(patron);
		//assert that the expected results have occurred
		assertTrue(isValidPatron);
		assertFalse("failure: Patron has overdue loans", library.patronCanBorrow(patron));
	}
	
	

}
