package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;

@ExtendWith(MockitoExtension.class)
public class TestPatron {

	Patron patron;

	String lastName = "Smith";
	String firstName = "John";
	String email = "john.smith@hotmail.com";
	long phoneNo = 04123456L;
	int id = 1;

	@BeforeEach
	void setUp() throws Exception {
		patron = new Patron(lastName, firstName, email, phoneNo, id);
	}

	@AfterEach
	void tearDown() throws Exception {
		patron = null;
	}

	@Test
	void testHasOverDueLoans() {
		//arrange
		patron.hasOverDueLoans();
		//act
		patron.restrictBorrowing();
		//assert

	}

}
