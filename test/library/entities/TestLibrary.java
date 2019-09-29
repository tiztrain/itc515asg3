package library.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import static org.mockito.Mockito.*;

import library.entities.ILoan.LoanState;
import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestLibrary {
	
	@Mock IPatron mockPatron;
	@Mock ILoan mockLoan;
	@Mock IBook mockBook;
	
	@Spy Map<Integer, ILoan> loans;
	@Spy Map<Integer, ILoan> currentLoans;
	@Spy Map<Integer, IPatron> patrons;
	
	IBookHelper bookHelper;
	IPatronHelper patronHelper;
	ILoanHelper loanHelper;
	
	
//	String lastName;
//	String firstName;
//	String email;
//	long phoneNo;
//	int id;
//	
//	String author;
//	String title;
//	String callNo;
//	int bookId;
//	
//	int loanId;
//	Date dueDate;
//	SimpleDateFormat format;
//	
//	int loanLimit = 2;
//	double maxFinesOwed = 1;

	@InjectMocks
	Library library = new Library(bookHelper, patronHelper, loanHelper,
			null, patrons, loans, currentLoans, null, 0, 0, 0);

	@BeforeEach
	void setUp() throws Exception {
//		lastName = "Smith";
//		firstName = "Bob";
//		email = "bob.smith@gmail.com";
//		phoneNo = 61412345678L;
//		id = 1;
//		
//		author = "Tolken";
//		title = "Lord of the Rings";
//		callNo = "what is this";
//		bookId = 1;
//		
//		loanId = 1;
//		format = new SimpleDateFormat("dd-MM-yyyy");
//		dueDate = format.parse("01-01-2001");
//		
//		patron = new Patron(lastName, firstName, email, phoneNo, id);
//		//library = new Library(bookHelper, patronHelper, loanHelper);
//		book = new Book(author, title, callNo, bookId);
//		loan = new Loan(book, patron);
	}

	@AfterEach
	void tearDown() throws Exception {
		library = null;
	}
	

	@Test
	void testPatronCanBorrow() {
		//arrange all necessary preconditions and inputs
		assertTrue(mockPatron instanceof IPatron);
		when(mockPatron.getNumberOfCurrentLoans()).thenReturn(0);
		when(mockPatron.getFinesPayable()).thenReturn(0.0);
		when(mockPatron.hasOverDueLoans()).thenReturn(false);
		
		//act on the object or method under test
		boolean actual = library.patronCanBorrow(mockPatron);

		//assert that the expected results have occurred
		assertTrue(actual);
	}
	
	
	@Test
	void testCommitLoanWhenPENDING() {
		//arrange all necessary preconditions and inputs
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.PENDING;
		assertEquals(state, LoanState.PENDING);

		//act on the object or method under test
		assertEquals(0, loans.size());
		assertEquals(0, currentLoans.size());
		assertEquals(0, patrons.size());
		
		library.commitLoan(mockLoan); //ERROR HERE

		//assert that the expected results have occurred
		assertEquals(1, loans.size());
		assertEquals(1, currentLoans.size());
		assertEquals(1, patrons.size());
		assertTrue(mockBook.isOnLoan());
		assertEquals(state, LoanState.CURRENT);
	}
	
	
	@Test
	void testcommitLoanWhenCURRENT() {
		//mockLoan = new ILoan(mockBook, mockLoan, 0, 0, LoanState.CURRENT);
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.CURRENT;

		//act on the object or method under test
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);

		//assert that the expected results have occurred
		assertEquals("Loan: Cannot commit a non PENDING loan", t.getMessage());
	}
	
	
	@Test
	void testcommitLoanWhenOVERDUE() {
		//mockLoan = new ILoan(mockBook, mockLoan, 0, 0, LoanState.CURRENT);
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.OVER_DUE;

		//act on the object or method under test
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);

		//assert that the expected results have occurred
		assertEquals("Loan: Cannot commit a non PENDING loan", t.getMessage());
	}

	
	@Test
	void testcommitLoanWhenDISCHARGED() {
		//mockLoan = new ILoan(mockBook, mockLoan, 0, 0, LoanState.CURRENT);
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.DISCHARGED;

		//act on the object or method under test
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);

		//assert that the expected results have occurred
		assertEquals("Loan: Cannot commit a non PENDING loan", t.getMessage());
	}
}
