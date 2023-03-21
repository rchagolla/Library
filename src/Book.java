import java.time.LocalDate;
import java.util.Objects;

/*
* Name: Rolando Chagolla-Bonilla
* Date: February - 5 - 2023
* Description: A simple java object that represents a book in a library.
*/

public class Book {

  public static final int ISBN_ = 0;
  public static final int TITLE_ = 1;
  public static final int SUBJECT_ = 2;
  public static final int PAGE_COUNT_ = 3;
  public static final int AUTHOR_ = 4;
  public static final int DUE_DATE_ = 5;

  private String isbn;
  private String title;
  private String subject;
  private int pageCount;
  private String author;
  private LocalDate dueDate;

  public Book(String isbn, String title, String subject, int pageCount, String author,
      LocalDate dueDate) {
    this.isbn = isbn;
    this.title = title;
    this.subject = subject;
    this.pageCount = pageCount;
    this.author = author;
    this.dueDate = dueDate;
  }

  @Override
  public String toString() {
    return title + " by " + author + " ISBN:" + isbn;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Book book = (Book) o;

    if (pageCount != book.pageCount) {
      return false;
    }
    if (!Objects.equals(isbn, book.isbn)) {
      return false;
    }
    if (!Objects.equals(title, book.title)) {
      return false;
    }
    if (!Objects.equals(subject, book.subject)) {
      return false;
    }
    return Objects.equals(author, book.author);
  }

  @Override
  public int hashCode() {
    int result = isbn != null ? isbn.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + pageCount;
    result = 31 * result + (author != null ? author.hashCode() : 0);
    return result;
  }
}
