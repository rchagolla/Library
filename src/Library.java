import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {
  public static final int LENDING_LIMIT = 5;
  private String name;
  private static int libraryCard;
  private List<Reader> readers;
  private HashMap<String, Shelf> shelves;
  private HashMap<Book, Integer> books;

  public Library() {

  }

  public Library(String name) {
    this.name = name;
    readers = new ArrayList<>();
    shelves = new HashMap<>();
    books = new HashMap<>();
  }

  public Code init(String filename) {
    Code code;
    File f = new File(filename);
    Scanner fileIn = null;
    // opens file
    try {
      fileIn = new Scanner(f);
    } catch (FileNotFoundException e) {
      return Code.FILE_NOT_FOUND_ERROR;
    }

    // reads in books
    String amount = fileIn.nextLine();
    code = initBooks(convertInt(amount, Code.SHELF_NUMBER_PARSE_ERROR), fileIn);

    // reads in shelves
    amount = fileIn.nextLine();
    code = initShelves(convertInt(amount, Code.BOOK_COUNT_ERROR), fileIn);

    //temp
    return code;
  }

  public HashMap<String, Shelf> getShelves() {
    return shelves;
  }

  public static void main(String[] args) {
    Library library = new Library("CSUMB");
    library.init("Library00.csv");
  }

  // Returns the Integer form of the recordCountString or if error a code.
  public int convertInt(String recordCountString, Code code) {
    int number = 0;
    try {
      number = Integer.parseInt(recordCountString);
    } catch (NumberFormatException e) {
      System.out.println("Value which caused the error: "+recordCountString);
      System.out.println("Error message: "+code.getMessage());
      switch (code) {
        case BOOK_COUNT_ERROR -> {
          System.out.println("Error: Could not read number of books");
          return code.getCode();
        }
        case PAGE_COUNT_ERROR -> {
          System.out.println("Error: could not parse page count");
          return code.getCode();
        }
        case DATE_CONVERSION_ERROR -> {
          System.out.println("Error: Could not parse date component");
          return code.getCode();
        }
        default -> {
          System.out.println("Error: Unknown conversion error");
          return code.getCode();
        }
      }
    }
    return number;
  }

  // Converts the date String to a LocalDate object or if error a code.
  public LocalDate convertDate(String date, Code errorCode) {
    LocalDate finalDate = LocalDate.of(1970, 1, 1);
    int[] convertedDate = new int[3];

    // checks if date is default
    if (!date.equals("0000")) {
      // splices date
      String[] splitDate = date.split("-");
      if (splitDate.length != 3) {
        System.out.println("ERROR: date conversion error, could not parse " + date);
        System.out.println("Using default date (01-jan-1970)");
      } else {
        // converts date to ints
        for (int i = 0; i < splitDate.length; i++) {
          int number = Integer.parseInt(splitDate[i]);
          if (number < 0) {
            System.out.println("Error converting date: Year " + splitDate[0]);
            System.out.println("Error converting date: Month " + splitDate[1]);
            System.out.println("Error converting date: Day " + splitDate[2]);
            System.out.println("Using default date (01-jan-1970)");
            return finalDate;
          }

          // if passes all checks number is saved
          convertedDate[i] = number;
        }
        finalDate = LocalDate.of(convertedDate[0],convertedDate[1], convertedDate[2]);
      }
    }

    return finalDate;
  }

  // adds all books from file to library object.
  private Code initBooks(int bookCount, Scanner scan) {
    if (bookCount < 1) {
      return Code.LIBRARY_ERROR;
    }

    for (int i = 0; i < bookCount; i++) {
      String input = scan.nextLine();
      String[] bookAttributes = input.split(",");

      // converting page count to an int
      int pgCount = convertInt(bookAttributes[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
      if (pgCount < 1) {
        return Code.PAGE_COUNT_ERROR;
      }

      // converting due-date to a LocalDate
      LocalDate dueDate = convertDate(bookAttributes[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
      if (dueDate == null) {
        return Code.DATE_CONVERSION_ERROR;
      }
      String isbn = bookAttributes[Book.ISBN_];
      String title = bookAttributes[Book.TITLE_];
      String subject = bookAttributes[Book.SUBJECT_];
      String author = bookAttributes[Book.AUTHOR_];
      Book book = new Book(isbn, title, subject, pgCount, author, dueDate);
      addBook(book);
    }
    return Code.SUCCESS;
  }

  // adds book to library stack and checks if there is shelf for said book.
  public Code addBook(Book newBook) {
    // if book is already in Library
    if (books.containsKey(newBook)) {
      books.replace(newBook, books.get(newBook)+1);
      System.out.println(books.get(newBook)+" copies of "+newBook.getTitle()+" in the stacks");
    } else {
      books.put(newBook, 1);
      System.out.println(newBook.getTitle()+" added to the stacks.");
    }
    // checks if book can be added to a shelf
    if(!shelves.containsKey(newBook.getSubject())) {
      System.out.println("No shelf for "+newBook.getSubject()+" books");
      return Code.SHELF_EXISTS_ERROR;
    }
    shelves.get(newBook.getSubject()).addBook(newBook);
    return Code.SUCCESS;
  }

  private Code initShelves(int shelfCount, Scanner scan) {
    if (shelfCount < 1) {
      return Code.SHELF_COUNT_ERROR;
    }
    if (shelfCount != Code.UNKNOWN_ERROR.getCode()) {
      for (int i = 0; i < shelfCount; i++) {
        String input = scan.nextLine();
        String[] shelfAttributes = input.split(",");
        addShelf(shelfAttributes[1]);
      }
      if (shelfCount == shelves.size()) {
        return Code.SUCCESS;
      }
      System.out.println("Number of shelves doesn't match expected");
    }
    return Code.SHELF_NUMBER_PARSE_ERROR;
  }

  public Code addShelf(String shelfSubject) {
    Shelf shelf = new Shelf(shelves.size()+1, shelfSubject);
    return addShelf(shelf);
  }

  public Code addShelf (Shelf shelf) {
    if (!shelves.containsKey(shelf.getSubject())) {
      shelves.put(shelf.getSubject(), shelf);
      for (Book book : books.keySet()) {
        if (book.getSubject().equals(shelf.getSubject())) {
          shelves.get(book.getSubject()).addBook(book);
        }
      }
    }
    System.out.println("ERROR: Shelf already exists "+shelf);
    return Code.SHELF_EXISTS_ERROR;
  }
}
