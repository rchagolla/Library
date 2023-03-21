import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Name: Rolando Chagolla-Bonilla
 * Date: February - 6 - 2023
 * Description: A simple java object that represents a Reader of a library.
 */
public class Reader {
  public static final int CARD_NUMBER_ = 0;
  public static final int NAME_ = 1;
  public static final int PHONE_ = 2;
  public static final int BOOK_COUNT_ = 3;
  public static final int BOOK_START_ = 4;

  private int cardNumber;
  private String name;
  private String phone;
  private List<Book> books;

  Reader(int cardNumber, String name, String phone) {
    this.cardNumber = cardNumber;
    this.name = name;
    this.phone = phone;
    this.books = new ArrayList<Book>();
  }

  // Returns true if the user has the book in their list
  public Boolean hasBook(Book book) {
    for (Book b : books) {
      if (book.equals(b)) {
        return true;
      }
    }
    return false;
  }

  // Returns a string that looks like: Bob Barker (#2187) has checked out {Book1, Book2}
  @Override
  public String toString() {
    String booksList = new String();
    // converts arrayList of books into a string
    for (Book book : books) {
      booksList = booksList + book.toString() + ", ";
    }
    if (books.size() > 0) {
      booksList = booksList.substring(0, booksList.length()-2);
    }

    return name + " (#" + cardNumber + ") has checked out {" + booksList + "}";
  }

   public Code addBook(Book book) {
    if (hasBook(book)) {
      return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
    }
    books.add(book);
    return Code.SUCCESS;
  }

  public Code removeBook(Book book) {
    if (!hasBook(book)) {
      return Code.READER_DOESNT_HAVE_BOOK_ERROR;
    }
    if (hasBook(book)) {
      books.remove(book);
      return Code.SUCCESS;
    }
    return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
  }

  public int getBookCount() {
    return books.size();
  }


  public int getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
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

    Reader reader = (Reader) o;

    if (cardNumber != reader.cardNumber) {
      return false;
    }
    if (!Objects.equals(name, reader.name)) {
      return false;
    }
    return Objects.equals(phone, reader.phone);
  }

  @Override
  public int hashCode() {
    int result = cardNumber;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    return result;
  }
}
