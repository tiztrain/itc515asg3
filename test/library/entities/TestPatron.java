package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;

@ExtendWith(MockitoExtension.class)
public class TestPatron {

	@Mock ILoan mockLoan;
	@Spy ArrayList<Loan> loans;

	String lastName = "Smith";
	String firstName = "John";
	String email = "john.smith@hotmail.com";
	long phoneNo = 04123456L;
	int id = 1;
	
	Date currentDate;
	SimpleDateFormat format;
	
	@InjectMocks
	Patron patron = new Patron(lastName, firstName, email, phoneNo, id);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		patron = null;
	}

	@Test
	void testHasOverDueLoans() throws Exception {
		//arrange
		
		format = new SimpleDateFormat("dd-MM-yyyy");
		currentDate = format.parse("01-01-2001");
		
		//act
		boolean anyOverDueLoans = loans.equals(mockLoan.isOverDue());

		//assert
		assertTrue(anyOverDueLoans);
	}

}
