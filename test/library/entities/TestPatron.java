package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import library.entities.ILoan.LoanState;
import library.entities.IPatron.PatronState;
import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestPatron {

	@Mock ILoan mockLoan;
	//@Spy ArrayList<Loan> loans;
	@Spy Map<Integer, ILoan> loans = new HashMap<>();

	String lastName = "Smith";
	String firstName = "John";
	String email = "john.smith@hotmail.com";
	long phoneNo = 04123456L;
	int id = 1;
	PatronState state;
	
	Date currentDate;
	SimpleDateFormat format;
	
	LoanState loanState;
	int loanId;
	
	@InjectMocks
	Patron patron = new Patron(lastName, firstName, email, phoneNo, id, 0.0, loans, state);

	@BeforeEach
	void setUp() throws Exception {
		loanId = mockLoan.getId();
		loanState = LoanState.CURRENT;
	}

	@AfterEach
	void tearDown() throws Exception {
		patron = null;
	}

//	@Test
//	void testHasOverDueLoans() throws Exception {
//		//arrange
//		
//		format = new SimpleDateFormat("dd-MM-yyyy");
//		currentDate = format.parse("01-01-2001");
//		
//		//act
//		boolean anyOverDueLoans = loans.equals(mockLoan.isOverDue());
//
//		//assert
//		assertTrue(anyOverDueLoans);
//	}
	
	// defect in Patron because you can't use methods to find out what state patron is in or they can borrow

	@Test
	void testTakeOutLoansWhenPatronCANBORROW() {
		//arrange
		state = PatronState.CAN_BORROW;		
		assertTrue(state == PatronState.CAN_BORROW); //are these even needed?
		assertTrue(mockLoan instanceof ILoan);
		assertFalse(loans.containsKey(loanId));
		assertTrue(loanState == LoanState.CURRENT); //are these even needed?
		
		//act
		patron.takeOutLoan(mockLoan);
		
		//assert
		assertEquals(1, loans.size());
	}
	
	@Test
	void testTakeOutLoansWhenPatronRESTRICTED() {
		//arrange
		patron = new Patron(lastName, firstName, email, phoneNo, id, 0.0, loans, PatronState.RESTRICTED);
		assertTrue(mockLoan instanceof ILoan);
		assertFalse(loans.containsKey(loanId));
		
		//act
		Executable e = () -> patron.takeOutLoan(mockLoan);
		Throwable t = assertThrows(RuntimeException.class,e);
		
		//assert
		assertEquals("Patron cannot borrow in RESTRICTED state", t.getMessage()); // this is testing the message
	}
	

	@Test
	void testTakeOutLoansWhenLoanOVERDUE() {
		//arrange
		loanState = LoanState.OVER_DUE;
		assertTrue(mockLoan instanceof ILoan);
		assertFalse(loans.containsKey(loanId));
//
//		//act
//		Executable e = () -> patron.takeOutLoan(mockLoan);
//		Throwable t = assertThrows(RuntimeException.class,e);
//		
//		//assert
//		assertEquals("Patron cannot borrow when loan is in OVERDUE state", t.getMessage()); // this is testing the message		
	}
	
	@Test
	void testTakeOutLoansWhenLoanNotValid() {
		//arrange
		mockLoan = null;
		assertFalse(mockLoan instanceof ILoan);
		assertFalse(loans.containsKey(loanId));
//
//		//act
//		Executable e = () -> patron.takeOutLoan(mockLoan);
//		Throwable t = assertThrows(RuntimeException.class,e);
//		
//		//assert
//		assertEquals("Patron cannot borrow when loan is not valid", t.getMessage()); // this is testing the message
	}
	
	@Test
	void testTakeOutLoansWhenAlreadyExists() {
		//arrange
		assertTrue(mockLoan instanceof ILoan);
		assertTrue(loans.containsKey(loanId));

		//act
		Executable e = () -> patron.takeOutLoan(mockLoan);
		Throwable t = assertThrows(RuntimeException.class,e);
		
		//assert
		assertEquals("Attempted to add duplicate loan to patron", t.getMessage()); // this is testing the message
	}
}
