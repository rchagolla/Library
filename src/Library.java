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
  private static int libraryCard = 0;
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
    System.out.println("parsing "+amount+" books");
    code = initBooks(convertInt(amount, Code.BOOK_COUNT_ERROR), fileIn);
    if (code == Code.SUCCESS) {
      System.out.println("SUCCESS");
      listBooks();
    }

    // reads in shelves
    amount = fileIn.nextLine();
    System.out.println("parsing "+amount+" shelves");
    code = initShelves(convertInt(amount, Code.SHELF_NUMBER_PARSE_ERROR), fileIn);
    if (code == Code.SUCCESS) {
      System.out.println("SUCCESS");
      listShelves(true);
    }
    // reads in readers
    amount = fileIn.nextLine();
    code = initReader(convertInt(amount, Code.READER_COUNT_ERROR), fileIn);
    if (code == Code.SUCCESS) {
      System.out.println("SUCCESS");
    }

    //temp
    return code;
  }

  public List<Reader> getReaders() {
    return readers;
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
      System.out.println("parsing book: "+input);
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
      System.out.println(books.get(newBook)+" copies of "+newBook+" in the stacks");
    } else {
      books.put(newBook, 1);
      System.out.println(newBook+" added to the stacks.");
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
        System.out.println("Parsing shelf: "+input);
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
          for (int i = 0; i < books.get(book); i++) {
            shelves.get(book.getSubject()).addBook(book);
          }
        }
      }
      return Code.SUCCESS;
    }
    System.out.println("ERROR: Shelf already exists "+shelf);
    return Code.SHELF_EXISTS_ERROR;
  }

  private Code initReader(int readerCount, Scanner scan) {
    if (readerCount < 1) {
      return Code.READER_COUNT_ERROR;
    }
    for (int i = 0; i < readerCount; i++) {
      String input = scan.nextLine();
      String[] readerAttributes = input.split(",");

      // creating Reader object
      String card = readerAttributes[Reader.CARD_NUMBER_];
      int cardNum = convertInt(card, Code.UNKNOWN_ERROR);
      String name = readerAttributes[Reader.NAME_];
      String phone = readerAttributes[Reader.PHONE_];
      Reader reader = new Reader(cardNum, name, phone);
      addReader(reader);

      // adding books to reader object
      int bookSize = convertInt(readerAttributes[Reader.BOOK_COUNT_], Code.UNKNOWN_ERROR)+Reader.BOOK_START_;
      for (int k = Reader.BOOK_START_; k <= bookSize; k=k+2) {
        Book book = getBookByISBN(readerAttributes[k]);
        if (book == null) {
          System.out.println("ERROR");
        } else {
          checkOutBook(reader, book);
          System.out.println("SUCCESS");
        }
      }
    }
    return Code.SUCCESS;
  }

  public Code addReader(Reader reader) {
    if (readers.contains(reader)) {
      System.out.println(reader.getName()+" already has an account!");
      return Code.READER_ALREADY_EXISTS_ERROR;
    }
    for (Reader reader2 : readers) {
      if (reader2.getCardNumber() == (reader.getCardNumber())) {
        System.out.println(reader2+" and "+reader+" have the same card number!");
        return Code.READER_CARD_NUMBER_ERROR;
      }
    }

    readers.add(reader);
    System.out.println(reader.getName()+" added to the library!");
    if (reader.getCardNumber() > libraryCard) {
      libraryCard = reader.getCardNumber();
    }
    return Code.SUCCESS;
  }

  public Book getBookByISBN(String isbn) {
    for (Book book : books.keySet()) {
      if (book.getIsbn().equals(isbn)) {
        return book;
      }
    }
    System.out.println("ERROR: Could not find a book with isbn: "+isbn);
    return null;
  }

  public Code checkOutBook(Reader reader, Book book) {
    if(!readers.contains(reader)) {
      System.out.println(reader.getName()+" doesn't have an account here");
      return Code.READER_NOT_IN_LIBRARY_ERROR;
    }
    if (reader.getBookCount() >= LENDING_LIMIT) {
      System.out.println(reader.getName()+" has reached the lending limit, "+LENDING_LIMIT);
      return Code.BOOK_LIMIT_REACHED_ERROR;
    }
    if (!books.containsKey(book)) {
      System.out.println("ERROR: could not find "+book);
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }
    if (!shelves.containsKey(book.getSubject())) {
      System.out.println("no shelf for "+book.getSubject()+" books!");
      return Code.SHELF_EXISTS_ERROR;
    }
    HashMap<Book, Integer> shelfBooks = shelves.get(book.getSubject()).getBooks();
    if (shelfBooks.get(book) < 1) {
      System.out.println("ERROR: no copies of "+book+" remain");
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    // adding book to reader and taking from shelf
    Code code = reader.addBook(book);
    if (!code.equals(Code.SUCCESS)) {
      System.out.println("Couldn't checkout "+book);
      return code;
    }

    code = shelves.get(book.getSubject()).removeBook(book);
    if (!code.equals(Code.SUCCESS)) {
      System.out.println("Couldn't checkout "+book);
      return code;
    }
    System.out.println(book+" checked out successfully");
    return code;
  }

  public Code returnBook(Reader reader, Book book) {
    if (!reader.hasBook(book)) {
      System.out.println(reader.getName()+" doesn't have "+book+" checked out");
      return Code.READER_DOESNT_HAVE_BOOK_ERROR;
    }
    if (!books.containsKey(book)) {
      return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    //returning book
    System.out.println(reader.getName()+" is returning "+book);
    Code code = reader.removeBook(book);
    if (code != Code.SUCCESS) {
      System.out.println("Could not return "+book);
      return code;
    }
    code = returnBook(book);
    return code;
  }

  public Code returnBook(Book book) {
    if (!shelves.containsKey(book.getSubject())) {
      System.out.println("No shelf for "+book);
      return Code.SHELF_EXISTS_ERROR;
    }

    //adding book to shelf
    return shelves.get(book.getSubject()).addBook(book);
  }

  public int listBooks() {
    int count = 0;
    for (Book book : books.keySet()) {
      System.out.println(books.get(book)+" copies of "+book);
      count += books.get(book);
    }
    System.out.println("");
    return count;
  }

  public Code listShelves(boolean showBooks) {
    if (!showBooks) {
      for (Shelf shelf : shelves.values()) {
        System.out.println(shelf+"\n");
      }
      return Code.SUCCESS;
    }
    for (Shelf shelf : shelves.values()) {
      System.out.println(shelf.listBooks());
    }
    return Code.SUCCESS;
  }

  public  int listReaders() {
    int count = 0;
    for (Reader reader : readers) {
      System.out.println(reader);
      count++;
    }

    return count;
  }

  public int listReaders(boolean showBooks) {
    int count = 0;
    if (!showBooks) {
      count = listReaders();
      return count;
    }
    for(Reader reader : readers) {
      System.out.println(reader.getName()+"(#"+reader.getCardNumber()+") has the following books:");
      System.out.println(reader.getBooks());
      count++;
    }
    return count;
  }

  public Shelf getShelf(String subject) {
    if (!shelves.containsKey(subject)) {
      System.out.println("No shelf for "+subject+" books");
      return null;
    }
    return shelves.get(subject);
  }

  public Shelf getShelf(Integer shelfNumber) {
    for (Shelf shelf : shelves.values()) {
      if (shelf.getShelfNumber() == shelfNumber) {
        return shelf;
      }
    }
    System.out.println("No shelf number "+shelfNumber+" found");
    return null;
  }

  public Reader getReaderByCard(int cardNumber) {
    for (Reader reader : readers) {
      if (reader.getCardNumber() == cardNumber) {
        System.out.println("Returning Reader "+reader);
        return reader;
      }
    }
    System.out.println("Could not find a reader with card #"+cardNumber);
    return null;
  }
}
