import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Name: Rolando Chagolla-Bonilla
 * Date: March - 4 - 2023
 * Description: A unit test for the class Shelf.
 */

class ShelfTest {

  private final int testShelfNumber = 1;
  private final String testSubject = "education";
  private final HashMap<Book, Integer> testBooks = new HashMap<>();
  private Book testBook = null;
  private Book testBook2 = null;
  private Shelf testShelf = null;

  @BeforeEach
  void setUp() {
    System.out.println("---SETUP RUNS BEFORE EVERY TEST---");
    testBook = new Book("1337", "Headfirst Java", "education",
        1337, "Grady Booch", LocalDate.now());
    testBook2 = new Book("5297", "Count of Monte Cristo", "Adventure",
        456, "Alexandre Dumas", LocalDate.now());
    testShelf = new Shelf(testShelfNumber, testSubject);
  }

  @AfterEach
  void tearDown() {
    System.out.println("---TEARDOWN RUNS AFTER EVERY TEST---");
    testBook = null;
    testBook2 = null;
    testShelf = null;
  }

  @Test
  void testConstructor() {
    System.out.println("*** Testing constructor ***");
    // instantiates a Shelf object set to null then tests if null.
    Shelf testShelf2 = null;
    assertNull(testShelf2);

    // sets Shelf object equal to testShelf address then makes sure contents are the same;
    testShelf2 = testShelf;
    assertNotNull(testShelf2);
    assertEquals(testShelf2, testShelf);
  }

  @Test
  void getBookCount() {
    System.out.println("*** Testing getBookCount ***");
    // adds a random amount of copies
    Random rand = new Random();
    int copies = rand.nextInt(5) + 1;
    for (int i = 0; i < copies; i++) {
      testShelf.addBook(testBook);
    }
    // check bookCount
    assertEquals(copies, testShelf.getBookCount(testBook));

    // remove 1 and check count
    testShelf.removeBook(testBook);
    assertEquals(copies - 1, testShelf.getBookCount(testBook));

    //remove the rest
    for (int i = 0; i < copies - 1; i++) {
      testShelf.removeBook(testBook);
    }
    assertEquals(0, testShelf.getBookCount(testBook));

    // checking count of non-existing book
    assertEquals(-1, testShelf.getBookCount(testBook2));

  }

  @Test
  void addBook() {
    System.out.println("*** Testing addBook ***");
    // adding book that matches to shelf should also increment count for said book
    assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
    assertEquals(1, testShelf.getBookCount(testBook));

    //add the same book
    assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
    assertEquals(2, testShelf.getBookCount(testBook));

    //add a book with wrong subject
    assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR, testShelf.addBook(testBook2));
    assertEquals(-1, testShelf.getBookCount(testBook2));
  }

  @Test
  void removeBook() {
    System.out.println("*** Testing removeBook ***");
    // removing book that doesn't exist in shelf
    assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, testShelf.removeBook(testBook));

    // adding book that matches to shelf should also increment count for said book
    assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
    assertEquals(1, testShelf.getBookCount(testBook));
    // adding book that matches to shelf should also increment count for said book
    assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
    assertEquals(2, testShelf.getBookCount(testBook));

    // removing a copy of book
    assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
    assertEquals(1, testShelf.getBookCount(testBook));

    // removing final copy of book
    assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
    assertEquals(0, testShelf.getBookCount(testBook));

    // removing final copy of book
    assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, testShelf.removeBook(testBook));
    assertEquals(0, testShelf.getBookCount(testBook));
  }

  @Test
  void listBooks() {
    System.out.println("*** Testing listBooks ***");
    //adding books to shelf
    testShelf.addBook(testBook);

    // checking function output
    String expected = "1 book on shelf: 1 : education\nHeadfirst Java by Grady Booch ISBN:1337 1\n";
    String output = testShelf.listBooks();
    assertEquals(expected, output);

    testShelf.addBook(testBook);

    // checking function output
    expected = "2 books on shelf: 1 : education\nHeadfirst Java by Grady Booch ISBN:1337 2\n";
    output = testShelf.listBooks();
    assertEquals(expected, output);
  }

  @Test
  void getShelfNumber() {
    System.out.println("*** Testing getShelfNumber ***");
    assertNotNull(testShelf.getShelfNumber());
    assertEquals(testShelfNumber, testShelf.getShelfNumber());
  }

  @Test
  void setShelfNumber() {
    System.out.println("*** Testing setShelfNumber ***");
    // instantiates a Shelf object with different attributes from testShelf
    Shelf testShelf2 = new Shelf(2, "fiction");

    //tests that shelfNumber is not the same as testShelf's shelfNumber
    assertNotEquals(testShelf.getShelfNumber(), testShelf2.getShelfNumber());

    //changes shelfNumber of testShelf2 then checks if change was made.
    testShelf2.setShelfNumber(testShelfNumber);
    assertEquals(testShelf.getShelfNumber(), testShelf2.getShelfNumber());
  }

  @Test
  void getSubject() {
    System.out.println("*** Testing getSubject ***");
    assertNotNull(testShelf.getSubject());
    assertEquals(testSubject, testShelf.getSubject());
  }

  @Test
  void setSubject() {
    System.out.println("*** Testing setSubject ***");
    // instantiates a Shelf object with different attributes from testShelf
    Shelf testShelf2 = new Shelf(2, "fiction");

    //tests that subject is not the same as testShelf's subject
    assertNotEquals(testShelf.getSubject(), testShelf2.getSubject());

    //changes subject of testShelf2 then checks if change was made.
    testShelf2.setSubject(testSubject);
    assertEquals(testShelf.getSubject(), testShelf2.getSubject());
  }

  @Test
  void getBooks() {
    System.out.println("*** Testing getBooks ***");
    assertNotNull(testShelf.getBooks());
    assertEquals(testBooks, testShelf.getBooks());
  }

  @Test
  void setBooks() {
    System.out.println("*** Testing setBooks ***");
    // instantiates a Shelf object with different attributes from testShelf
    testShelf.addBook(testBook);
    Shelf testShelf2 = new Shelf(2, "fiction");

    //tests that subject is not the same as testShelf's subject
    assertNotEquals(testShelf.getBooks(), testShelf2.getBooks());

    //changes subject of testShelf2 then checks if change was made.
    testShelf2.setBooks(testShelf.getBooks());
    assertEquals(testShelf.getBooks(), testShelf2.getBooks());
  }

  @Test
  void testEquals() {
    System.out.println("*** Testing Equals ***");
    // instantiates a Shelf object with different attributes from testShelf
    Shelf testShelf2 = new Shelf(2, "fiction");

    // instantiates a Shelf object with the same attributes as testShelf2
    Shelf testShelf3 = new Shelf(2, "fiction");

    //checks testShelf and testShelf2 are not equal
    assertFalse(testShelf.equals(testShelf2));

    // checks if values of testShelf2 and testShelf3 are equal
    assertTrue(testShelf2.equals(testShelf3));
  }

  @Test
  void testToString() {
    System.out.println("*** Testing toString ***");
    // compare testShelf's output
    String expected = "1 : education";
    assertEquals(expected, testShelf.toString());
  }
}