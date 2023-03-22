import java.util.HashMap;
import java.util.Objects;

/*
 * Name: Rolando Chagolla-Bonilla
 * Date: March - 4 - 2023
 * Description: A simple java object that represents a Shelf in a library.
 */

public class Shelf {

  public static final int SHELF_NUMBER_ = 0;
  public static final int SUBJECT_ = 1;
  private int shelfNumber;
  private String subject;
  private HashMap<Book, Integer> books;

  public Shelf() {

  }

  public Shelf(int shelfNumber, String subject) {
    this.shelfNumber = shelfNumber;
    this.subject = subject;
    this.books = new HashMap<>();
  }

  // returns book count of book or -1 if not in shelf.
  public int getBookCount(Book book) {
    if (books.containsKey(book)) {
      return books.get(book);
    }
    return -1;
  }

  //returns SUCCESS if book was added to shelf or MISMATCH if subject is wrong.
  public Code addBook(Book book) {
    // increments count of book if already in shelf
    if (books.containsKey(book)) {
      books.replace(book, books.get(book) + 1);
      System.out.println(book + " added to shelf " + this);
      return Code.SUCCESS;
    }

    // if book is the same subject as shelf it is added.
    if (book.getSubject().equals(subject)) {
      books.put(book, 1);
      System.out.println(book + " added to shelf " + this);
      return Code.SUCCESS;
    }

    // if book subject doesn't match to shelf return this code.
    return Code.SHELF_SUBJECT_MISMATCH_ERROR;
  }

  // tries to remove book from shelf
  public Code removeBook(Book book) {
    // checks if book is on the shelf.
    if (!books.containsKey(book)) {
      System.out.println(book + " is not on shelf " + subject);
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    // checks if there is a copy to remove
    if (books.get(book) == 0) {
      System.out.println("No copies of " + book + " remain on shelf " + subject);
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    // book is on shelf and there is a copy, so it's count is decremented.
    books.replace(book, books.get(book) - 1);
    System.out.println(book + " successfully removed from shelf " + subject);
    return Code.SUCCESS;
  }

  public String listBooks() {
    String output = "";
    int count = 0;
    for (Book book : books.keySet()) {
      count += books.get(book);
    }
    if (count != 1) {
      output = count + " books on shelf: " + this + "\n";
    } else {
      output = count + " book on shelf: " + this + "\n";
    }

    for (Book book : books.keySet()) {
      output = output.concat(book + " " + books.get(book) + "\n");
    }
    return output;
  }

  public int getShelfNumber() {
    return shelfNumber;
  }

  public void setShelfNumber(int shelfNumber) {
    this.shelfNumber = shelfNumber;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public HashMap<Book, Integer> getBooks() {
    return books;
  }

  public void setBooks(HashMap<Book, Integer> books) {
    this.books = books;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Shelf shelf = (Shelf) o;

    if (shelfNumber != shelf.shelfNumber) {
      return false;
    }
    return Objects.equals(subject, shelf.subject);
  }

  @Override
  public int hashCode() {
    int result = shelfNumber;
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return shelfNumber + " : " + subject;
  }
}
