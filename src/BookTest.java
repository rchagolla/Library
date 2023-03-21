import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Name: Rolando Chagolla-Bonilla
 * Date: February - 5 - 2023
 * Description: A unit test for the class Book.
 */

class BookTest {

  //  1337,Headfirst Java,education,1337,Grady Booch,0000
  private String testIsbn = "1337";
  private String testTitle = "Headfirst Java";
  private String testSubject = "education";
  private int testPageCount = 1337;
  private String testAuthor = "Grady Booch";
  private LocalDate testDueDate = LocalDate.now();

  private Book testBook = null;

  @BeforeAll
  static void mainSetup() {
    System.out.println("-=-=-MAIN SETUP RUNS ONCE BEFORE ALL TESTS-=-=-");
  }

  @AfterAll
  static void mainTearDown() {
    System.out.println("-=-=-MAIN TEARDOWN RUNS ONCE AFTER ALL TEST-=-=-");
  }

  //  Guarantees that the object I am working with is the same one for every test
  @BeforeEach
  void setUp() {
    System.out.println("---SETUP RUNS BEFORE EVERY TEST---");
    testBook = new Book(testIsbn, testTitle, testSubject, testPageCount, testAuthor, testDueDate);
  }

  //  resets the object for the next test.
  @AfterEach
  void tearDown() {
    System.out.println("---TEARDOWN RUNS AFTER EVERY TEST---");
    testBook = null;
  }

  @Test
  void testConstructor() {
    System.out.println("*** Testing constructor ***");
    // instantiates a Book object set to null then tests if null.
    Book testBook2 = null;
    assertNull(testBook2);

    // sets Book object equal to testBook then makes sure contents are the same;
    testBook2 = testBook;
    assertNotNull(testBook2);
    assertEquals(testBook2, testBook);
  }

  // Checks to see if getIsbn returns proper result.
  @Test
  void getIsbn() {
    System.out.println("*** Testing getIsbn ***");
    assertNotNull(testBook.getIsbn());
    assertEquals(testIsbn, testBook.getIsbn());
  }

  // Checks to see if setIsbn changes proper values.
  @Test
  void setIsbn() {
    System.out.println("*** Testing setIsbn ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that Isbn is not the same as testBook
    assertNotEquals(testBook.getIsbn(), testBook2.getIsbn());

    //changes Isbn to the same as testBook then checks if change was made.
    testBook2.setIsbn(testIsbn);
    assertEquals(testBook.getIsbn(), testBook2.getIsbn());
  }

  // Checks to see if getTitle returns proper result.
  @Test
  void getTitle() {
    System.out.println("*** Testing getTitle ***");
    assertNotNull(testBook.getTitle());
    assertEquals(testTitle, testBook.getTitle());
  }

  @Test
  void setTitle() {
    System.out.println("*** Testing setTitle ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that Title is not the same as testBook
    assertNotEquals(testBook.getTitle(), testBook2.getTitle());

    //changes Title to the same as testBook then checks if change was made.
    testBook2.setTitle(testTitle);
    assertEquals(testBook.getTitle(), testBook2.getTitle());
  }

  // Checks to see if getSubject returns proper result.
  @Test
  void getSubject() {
    System.out.println("*** Testing getSubject ***");
    assertNotNull(testBook.getSubject());
    assertEquals(testSubject, testBook.getSubject());
  }

  @Test
  void setSubject() {
    System.out.println("*** Testing setSubject ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that Subject is not the same as testBook
    assertNotEquals(testBook.getSubject(), testBook2.getSubject());

    //changes Subject to the same as testBook then checks if change was made.
    testBook2.setSubject(testSubject);
    assertEquals(testBook.getSubject(), testBook2.getSubject());
  }

  // Checks to see if getPageCount returns proper result.
  @Test
  void getPageCount() {
    System.out.println("*** Testing getPageCount ***");
    assertNotNull(testBook.getPageCount());
    assertEquals(testPageCount, testBook.getPageCount());
  }

  @Test
  void setPageCount() {
    System.out.println("*** Testing setPageCount ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that PageCount is not the same as testBook
    assertNotEquals(testBook.getPageCount(), testBook2.getPageCount());

    //changes PageCount to the same as testBook then checks if change was made.
    testBook2.setPageCount(testPageCount);
    assertEquals(testBook.getPageCount(), testBook2.getPageCount());
  }

  // Checks to see if getAuthor returns proper result.
  @Test
  void getAuthor() {
    System.out.println("*** Testing getAuthor ***");
    assertNotNull(testBook.getAuthor());
    assertEquals(testAuthor, testBook.getAuthor());
  }

  @Test
  void setAuthor() {
    System.out.println("*** Testing setAuthor ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that Author is not the same as testBook
    assertNotEquals(testBook.getAuthor(), testBook2.getAuthor());

    //changes Author to the same as testBook then checks if change was made.
    testBook2.setAuthor(testAuthor);
    assertEquals(testBook.getAuthor(), testBook2.getAuthor());
  }

  // Checks to see if getDueDate returns proper result.
  @Test
  void getDueDate() {
    System.out.println("*** Testing getDueDate ***");
    assertNotNull(testBook.getDueDate());
    assertEquals(testDueDate, testBook.getDueDate());
  }

  @Test
  void setDueDate() {
    System.out.println("*** Testing setDueDate ***");

    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    //tests that DueDate is not the same as testBook
    assertNotEquals(testBook.getDueDate(), testBook2.getDueDate());

    //changes DueDate to the same as testBook then checks if change was made.
    testBook2.setDueDate(testDueDate);
    assertEquals(testBook.getDueDate(), testBook2.getDueDate());
  }

  @Test
  void testEquals() {
    System.out.println("*** Testing Equals ***");
    // instantiates a book object with different attributes from testBook
    Book testBook2 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));
    assertNotEquals(testBook, testBook2);

    // instantiates a book object with the same attributes as testBook2
    Book testBook3 = new Book("1423", "Fake book", "fake subject", 1421, "Fake author",
        LocalDate.of(2023, 2, 4));

    // checks if values of testBook2 and testBook3 are equal
    assertEquals(true, testBook2.equals(testBook3));
  }
}