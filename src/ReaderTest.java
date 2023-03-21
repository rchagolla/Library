import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Name: Rolando Chagolla-Bonilla
 * Date: February - 6 - 2023
 * Description: A unit test for the class Reader.
 */

class ReaderTest {

  private final int testCardNumber = 1738;
  private final String testName = "Rolando Chagolla";
  private final String testPhone = "(831)435-2019";
  private List<Book> testBooks = new ArrayList<Book>();

  private Book testBook = null;
  private Reader testReader = null;

  @BeforeEach
  void setUp() {
    System.out.println("---SETUP RUNS BEFORE EVERY TEST---");
    testBook = new Book("1337", "Headfirst Java", "education",
        1337, "Grady Booch", LocalDate.now());
    testReader = new Reader(testCardNumber, testName, testPhone);
  }

  @AfterEach
  void tearDown() {
    System.out.println("---TEARDOWN RUNS AFTER EVERY TEST---");
    testBooks = null;
    testReader = null;
  }

  @Test
  void testConstructor() {
    System.out.println("*** Testing constructor ***");
    // instantiates a Reader object set to null then tests if null.
    Reader testReader2 = null;
    assertNull(testReader2);

    // sets Reader object equal to testReader address then makes sure contents are the same;
    testReader2 = testReader;
    assertNotNull(testReader2);
    assertEquals(testReader2, testReader2);
  }

  @Test
  void hasBook() {
    System.out.println("*** Testing hasBook ***");
    // checks if book is in list should return false
    assertFalse(testReader.hasBook(testBook));

    // added book to list, hasBook should return true
    testReader.addBook(testBook);
    assertTrue(testReader.hasBook(testBook));
  }

  @Test
  void testToString() {
    Book testBook2 = new Book("1234", "Fake title", "education", 345, "me", LocalDate.now());
    String testString = "Rolando Chagolla (#1738) has checked out {}";
    String testString2 = "Rolando Chagolla (#1738) has checked out {Headfirst Java by Grady Booch ISBN: 1337}";
    String testString3 = "Rolando Chagolla (#1738) has checked out {Headfirst Java by Grady Booch ISBN: 1337, Fake title by me ISBN: 1234}";
    System.out.println("*** Testing toString ***");
    assertEquals(testString, testReader.toString());

    testReader.addBook(testBook);
    assertEquals(testString2, testReader.toString());

    testReader.addBook(testBook2);
    assertEquals(testString3, testReader.toString());

    testReader.removeBook(testBook);
    testReader.removeBook(testBook2);
    assertEquals(testString, testReader.toString());
  }

  @Test
  void addBook() {
    System.out.println("*** Testing addBook ***");
    // adds book to list and checks code status
    assertEquals(testReader.addBook(testBook), Code.SUCCESS);
    // adds same book again and checks code is different
    assertNotEquals(testReader.addBook(testBook), Code.SUCCESS);
    // adds same book again and verifies code status
    assertEquals(testReader.addBook(testBook), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
  }

  @Test
  void removeBook() {
    System.out.println("*** Testing removeBook ***");
    // removes non-existing book and checks code status
    assertEquals(testReader.removeBook(testBook), Code.READER_DOESNT_HAVE_BOOK_ERROR);
    // adds a book and checks code is SUCCESS
    testReader.addBook(testBook);
    assertEquals(testReader.removeBook(testBook), Code.SUCCESS);
  }

  @Test
  void getCardNumber() {
    System.out.println("*** Testing getCardNumber ***");
    assertNotNull(testReader.getCardNumber());
    assertEquals(testCardNumber, testReader.getCardNumber());
  }

  @Test
  void setCardNumber() {
    System.out.println("*** Testing setCardNumber ***");
    // instantiates a Reader object with different attributes from testReader
    Reader testReader2 = new Reader(1423, "Jimmy", "(408)241-2453");

    //tests that cardNumber is not the same as testReader's cardNumber
    assertNotEquals(testReader.getCardNumber(), testReader2.getCardNumber());

    //changes cardNumber of testReader2 then checks if change was made.
    testReader2.setCardNumber(testCardNumber);
    assertEquals(testReader.getCardNumber(), testReader2.getCardNumber());
  }

  @Test
  void getName() {
    System.out.println("*** Testing getName ***");
    assertNotNull(testReader.getName());
    assertEquals(testName, testReader.getName());
  }

  @Test
  void setName() {
    System.out.println("*** Testing setName ***");
    // instantiates a Reader object with different attributes from testReader
    Reader testReader2 = new Reader(1423, "Jimmy", "(408)241-2453");

    //tests that name is not the same as testReader's name
    assertNotEquals(testReader.getName(), testReader2.getName());

    //changes name of testReader2 then checks if change was made.
    testReader2.setName(testName);
    assertEquals(testReader.getName(), testReader2.getName());
  }

  @Test
  void getPhone() {
    System.out.println("*** Testing getPhone ***");
    assertNotNull(testReader.getPhone());
    assertEquals(testPhone, testReader.getPhone());
  }

  @Test
  void setPhone() {
    System.out.println("*** Testing setPhone ***");
    // instantiates a Reader object with different attributes from testReader
    Reader testReader2 = new Reader(1423, "Jimmy", "(408)241-2453");

    //tests that phone is not the same as testReader's phone
    assertNotEquals(testReader.getPhone(), testReader2.getPhone());

    //changes phone of testReader2 then checks if change was made.
    testReader2.setPhone(testPhone);
    assertEquals(testReader.getPhone(), testReader2.getPhone());
  }

  @Test
  void getBooks() {
    System.out.println("*** Testing getBooks ***");
    assertNotNull(testReader.getBooks());
    assertEquals(testBooks, testReader.getBooks());
  }

  @Test
  void setBooks() {
    System.out.println("*** Testing setBooks ***");
    // instantiates a Reader object with different attributes from testReader
    Reader testReader2 = new Reader(1423, "Jimmy", "(408)241-2453");
    testReader2.addBook(testBook);

    //tests that Books is not the same as testReader's books
    assertNotEquals(testReader.getBooks(), testReader2.getBooks());

    //changes Books of testReader then checks if change was made.
    testReader.setBooks(testReader2.getBooks());
    assertEquals(testReader.getBooks(), testReader2.getBooks());
  }

  @Test
  void getBookCount() {
    System.out.println("*** Testing getBookCount ***");
    assertEquals(0, testReader.getBookCount());

    testReader.addBook(testBook);
    assertEquals(1, testReader.getBookCount());

    testReader.removeBook(testBook);
    assertEquals(0, testReader.getBookCount());
  }

  @Test
  void testEquals() {
    System.out.println("*** Testing Equals ***");
    // instantiates a Reader object with different attributes from testReader
    Reader testReader2 = new Reader(1423, "Jimmy", "(408)241-2453");
    testReader2.addBook(testBook);

    // instantiates a Reader object with the same attributes as testReader2
    Reader testReader3 = new Reader(1423, "Jimmy", "(408)241-2453");
    testReader2.addBook(testBook);

    //checks testBook and testBook2 are not equal
    assertFalse(testReader.equals(testReader2));

    // checks if values of testBook2 and testBook3 are equal
    assertTrue(testReader2.equals(testReader3));
  }
}