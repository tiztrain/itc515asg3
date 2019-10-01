package library.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import library.entities.helpers.BookHelper;
import library.entities.helpers.IBookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.IPatronHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestLibrary {
	
	@Mock IPatron mockPatron;
	@Mock ILoan mockLoan;
	@Mock IBook mockBook;
	
	@Spy Map<Integer, ILoan> loans = new HashMap<>();
	@Spy Map<Integer, ILoan> currentLoans = new HashMap<>();
	@Spy Map<Integer, IPatron> patrons = new HashMap<>();
	
	@Mock BookHelper bookHelper;
	@Mock PatronHelper patronHelper;
	@Mock LoanHelper loanHelper;
	
	int loanId;
	Date dueDate;
	SimpleDateFormat format;

	@InjectMocks
	Library library = new Library(bookHelper, patronHelper, loanHelper,
			null, patrons, loans, currentLoans, null, 0, 0, 0);

	@BeforeEach
	void setUp() throws Exception {
		format = new SimpleDateFormat("dd-MM-yyyy");
		dueDate = format.parse("11-12-2001");
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
	void testPatronExceedNumberCurrentLoans() {
		//arrange all necessary preconditions and inputs
		assertTrue(mockPatron instanceof IPatron);
		when(mockPatron.getNumberOfCurrentLoans()).thenReturn( ILibrary.LOAN_LIMIT + 1);
		when(mockPatron.getFinesPayable()).thenReturn(0.0);
		when(mockPatron.hasOverDueLoans()).thenReturn(false);
		
		//act on the object or method under test
		boolean actual = library.patronCanBorrow(mockPatron);
		// line 137 in Library needs to change to > instead of ==
		
		//assert that the expected results have occurred
		assertFalse(actual);
	}	
	
	@Test
	void testPatronExceedMaxFines() {
		//arrange all necessary preconditions and inputs
		when(mockPatron.getNumberOfCurrentLoans()).thenReturn(0);
		when(mockPatron.getFinesPayable()).thenReturn(ILibrary.MAX_FINES_OWED + 1 );
		when(mockPatron.hasOverDueLoans()).thenReturn(false);
		
		//act on the object or method under test
		boolean actual = library.patronCanBorrow(mockPatron);

		//assert that the expected results have occurred
		assertFalse(actual);
	}
	
	@Test
	void testPatronHasOverDueLoans() {		
		//arrange all necessary preconditions and inputs
		when(mockPatron.getNumberOfCurrentLoans()).thenReturn(0);
		when(mockPatron.getFinesPayable()).thenReturn(0.0);
		when(mockPatron.hasOverDueLoans()).thenReturn(true);
		
		//act on the object or method under test
		boolean actual = library.patronCanBorrow(mockPatron);

		//assert that the expected results have occurred
		assertFalse(actual);
	}
	
	
	@Test
	void testCommitLoanWhenPENDING() {
		//arrange all necessary preconditions and inputs
		LoanState state = LoanState.PENDING;
		
		//Loan mockLoan = new Loan(mockBook, mockPatron, 0, null, state); //is this right?
		ILoan mockLoan = loanHelper.makeLoan(mockBook, mockPatron);
		assertTrue(mockLoan instanceof ILoan);
		assertEquals(state, LoanState.PENDING);
		
		
		assertEquals(0, loans.size());
		//when(mockLoan.commit(loanId, dueDate)).thenReturn(mockLoan);
		when(loanHelper.makeLoan(any(Book.class), any(Patron.class))).thenReturn(mockLoan);
		assertEquals(0, currentLoans.size());
		assertEquals(0, patrons.size());

		//act on the object or method under test		
		
		//Loan actual = mockLoan.commit(loanId, dueDate);
		
		library.commitLoan(mockLoan);
		

		//assert that the expected results have occurred
		assertEquals(1, loans.size());
		assertEquals(1, currentLoans.size());
		
		assertTrue(mockBook.isOnLoan());
		assertEquals(state, LoanState.CURRENT);
	}
	
	@Test
	void testCommitLoanWhenBookAVAILABLE() {
		//arrange all necessary preconditions and inputs
		LoanState state = LoanState.PENDING;
		when(mockBook.isAvailable()).thenReturn(true);
		//Loan mockLoan = new Loan(mockBook, mockPatron, 0, null, state); //is this right?
		
		Book book = new Book("a","b","c",3);
		Patron pat = new Patron("a","b","c",1,2);
		ILoan mockLoan = library.issueLoan(book, pat);
		assertTrue(loanHelper instanceof LoanHelper);
		assertTrue(mockLoan instanceof ILoan);

		assertEquals(state, LoanState.PENDING);
		
		
		assertEquals(0, loans.size());
		//when(mockLoan.commit(loanId, dueDate)).thenReturn(mockLoan);
		when(loanHelper.makeLoan(any(Book.class), any(Patron.class))).thenReturn(mockLoan);
		assertEquals(0, currentLoans.size());
		assertEquals(0, patrons.size());

		//act on the object or method under test		
		
		//Loan actual = mockLoan.commit(loanId, dueDate);
		
		library.commitLoan(mockLoan);

		//assert that the expected results have occurred
		assertEquals(1, loans.size()); //ERROR expect 1 but got 0
		assertEquals(1, currentLoans.size());
		
		assertTrue(mockBook.isOnLoan());
		assertEquals(state, LoanState.CURRENT);
	}
	
	@Test
	void testCommitLoanWhenBookNotAVAILABLE() {
		//arrange all necessary preconditions and inputs
		LoanState state = LoanState.PENDING;
		when(mockBook.isAvailable()).thenReturn(false);
		Loan mockLoan = new Loan(mockBook, mockPatron, 0, null, state); //is this right?
		assertTrue(mockLoan instanceof ILoan);
		assertEquals(state, LoanState.PENDING);
		
		
		assertEquals(0, loans.size());
		//when(mockLoan.commit(loanId, dueDate)).thenReturn(mockLoan);
		when(loanHelper.makeLoan(any(Book.class), any(Patron.class))).thenReturn(mockLoan);
		assertEquals(0, currentLoans.size());
		assertEquals(0, patrons.size());

		//act on the object or method under test
		
		
		//Loan actual = mockLoan.commit(loanId, dueDate);
		
		library.commitLoan(mockLoan);

		//assert that the expected results have occurred
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e); //ERROR NOTHING WAS THROWN
	}
	
	
	@Test
	void testCommitLoanWhenCURRENT() {
		//mockLoan = new ILoan(mockBook, mockLoan, 0, 0, LoanState.CURRENT);
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.CURRENT;

		//act on the object or method under test
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e); //ERROR NOTHING WAS THROWN

		//assert that the expected results have occurred
		assertEquals("Loan: Cannot commit a non PENDING loan", t.getMessage());
	}
	
	
	@Test
	void testCommitLoanWhenOVERDUE() {
		//mockLoan = new ILoan(mockBook, mockLoan, 0, 0, LoanState.CURRENT);
		assertTrue(mockLoan instanceof ILoan);
		LoanState state = LoanState.OVER_DUE;

		//act on the object or method under test
		Executable e = () -> mockLoan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e); //ERROR NOTHING WAS THROWN

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
		Throwable t = assertThrows(RuntimeException.class,e); //ERROR NOTHING WAS THROWN

		//assert that the expected results have occurred
		assertEquals("Loan: Cannot commit a non PENDING loan", t.getMessage());
	}
}
