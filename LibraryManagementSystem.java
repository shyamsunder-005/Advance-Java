import java.util.ArrayList;
class Book {
    int bookId;
    String title, author;
    boolean isAvailable;
    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
    public void displayBookInfo() {
        System.out.println("Book-Id: " + bookId + ", Title: " + title + ", Author: " + author + ", Available: " + isAvailable);
    }
    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(title + " has been borrowed.");
        } else {
            System.out.println(title + " is not available.");
        }
    }
    public void returnBook() {
        isAvailable = true;
        System.out.println(title + " has been returned.");
    }
}
class Library {
    ArrayList<Book> books = new ArrayList<>();
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book has been added successfully!!");
    }
    public void removeBook(int bookId) {
        for (Book book : books) {
            if (book.bookId == bookId)
                books.remove(book);
        }
    }
    public void listAvailableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable) {
                book.displayBookInfo();
            }
        }
    }
}
class Student {
    int studentId;
    String name;
    ArrayList<Book> borrowedBooks = new ArrayList<>();
    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
    public void borrowBook(Book book) {
        if (borrowedBooks.size() < 3 && book.isAvailable) {
            borrowedBooks.add(book);
            book.borrowBook();
        } else {
            System.out.println("Cannot borrow " + book.title + " (Either already borrowed or limit reached).");
        }
    }
    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("You haven't borrowed this book.");
        }
    }
    public void displayBorrowedBooks() {
        System.out.println(name + " has borrowed:");
        for (Book book : borrowedBooks) {
            book.displayBookInfo();
        }
    }
}
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();       
        Book book1 = new Book(1, "book1", "author1");
        Student student = new Student(224206, "Shyam");
        library.addBook(book1);
        library.listAvailableBooks();
        student.borrowBook(book1);
        library.listAvailableBooks();
        student.returnBook(book1);
        System.out.println("Works fine.");
        
    }
}
