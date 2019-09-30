package library.entities;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestLoan {
	
	@Mock IBook mockBook;
	@Mock IPatron mockPatron;
	
	@Spy Map<Integer, ILoan> loans;
	
	int loanId;
	Date dueDate;
	LoanState state;
	

	@InjectMocks
	Loan loan = new Loan(mockBook, mockPatron, 0, null, LoanState.PENDING);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCommitWhenPENDING() {
		//arrange
		state = LoanState.PENDING;
		
		//act
		assertEquals(0, loans.size()); 
		loan.commit(loanId, dueDate);
		LoanState actual = LoanState.CURRENT;
		verify(mockPatron).takeOutLoan(loan);
		verify(mockBook).borrowFromLibrary();
		
		//assert
		assertEquals(state, actual);
		assertEquals(1, loans.size()); //Error expected 1 but got 0
		assertTrue(mockBook.isOnLoan());
	}
	
	@Test
	void testCommitWhenCURRENT() {
		//arrange
		loan = new Loan(mockBook, mockPatron, loanId, dueDate, LoanState.CURRENT);
		//LoanState state = LoanState.CURRENT;
		
		//act
		Executable e = () -> loan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);
		
		//assert
		assertEquals("Cannot commit a non PENDING loan", t.getMessage());
	}
	
	@Test
	void testCommitWhenOVERDUE() {
		//arrange
		loan = new Loan(mockBook, mockPatron, loanId, dueDate, LoanState.OVER_DUE);
		//LoanState state = LoanState.CURRENT;
		
		//act
		Executable e = () -> loan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);
		
		//assert
		assertEquals("Cannot commit a non PENDING loan", t.getMessage());
	}
	
	@Test
	void testCommitWhenDISCHARGED() {
		//arrange
		loan = new Loan(mockBook, mockPatron, loanId, dueDate, LoanState.DISCHARGED);
		//LoanState state = LoanState.CURRENT;
		
		//act
		Executable e = () -> loan.commit(0, null);
		Throwable t = assertThrows(RuntimeException.class,e);
		
		//assert
		assertEquals("Cannot commit a non PENDING loan", t.getMessage());
	}



}
