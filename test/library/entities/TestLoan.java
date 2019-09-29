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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import library.entities.ILoan.LoanState;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestLoan {
	
	@Mock IBook mockBook;
	@Mock IPatron mockPatron;
	
	@Spy Map<Integer, ILoan> loans;
	
	int loanId;
	Date dueDate;
	

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
		LoanState state = LoanState.PENDING;
		
		//act
		assertEquals(0, loans.size()); //Error expected 1 but got 0
		loan.commit(loanId, dueDate);
		state = LoanState.CURRENT;
		
		//assert
		assertEquals(LoanState.CURRENT, state);
		assertEquals(1, loans.size());
		assertTrue(mockBook.isOnLoan());
	}
	
//	@Test
//	void testCommitWhenCURRENT() {
//		//arrange
//		LoanState state = LoanState.PENDING;
//		
//		//act
//		assertEquals(0, loans.size()); //Error expected 1 but got 0
//		loan.commit(loanId, dueDate);
//		state = LoanState.CURRENT;
//		
//		//assert
//		assertEquals(LoanState.CURRENT, state);
//		assertEquals(1, loans.size());
//		assertTrue(mockBook.isOnLoan());
//	}
//	
//	@Test
//	void testCommitWhenOVERDUE() {
//		//arrange
//		LoanState state = LoanState.PENDING;
//		
//		//act
//		assertEquals(0, loans.size()); //Error expected 1 but got 0
//		loan.commit(loanId, dueDate);
//		state = LoanState.CURRENT;
//		
//		//assert
//		assertEquals(LoanState.CURRENT, state);
//		assertEquals(1, loans.size());
//		assertTrue(mockBook.isOnLoan());
//	}
//	
//	@Test
//	void testCommitWhenDISCHARGED() {
//		//arrange
//		LoanState state = LoanState.PENDING;
//		
//		//act
//		assertEquals(0, loans.size()); //Error expected 1 but got 0
//		loan.commit(loanId, dueDate);
//		state = LoanState.CURRENT;
//		
//		//assert
//		assertEquals(LoanState.CURRENT, state);
//		assertEquals(1, loans.size());
//		assertTrue(mockBook.isOnLoan());
//	}



}
