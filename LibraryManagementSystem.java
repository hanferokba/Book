
/*
Abdulrhman 445101939
Okbah 446110046
*/
//Github Link: https://github.com/hanferokba/Book.git
import java.util.Scanner;
// ============================
// Book CLASS
// ============================
class Book {
    private int bookID;
    private String title;
    private String author;
    private int stock;

    private static int totalBooksAdded = 0;
    private static int totalBooksRemoved = 0;

    public Book(int bookID, String title, String author, int stock) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.stock = stock;
        totalBooksAdded++;
    }

    public boolean borrow() {
        if (stock > 0) {
            stock--;
            return true;
        }
        return false;
    }

    public void returnBook() {
        stock++;
    }

    public void displayBookInfo() {
        System.out.printf("ID: %d | Title: %s | Author: %s | Stock: %d\n", bookID, title, author, stock);
    }

    public static void incrementRemovedBooks() {
        totalBooksRemoved++;
    }

    public int getBookID() {
        return bookID;
    }

    public int getStock() {
        return stock;
    }

    public static int getTotalBooksAdded() {
        return totalBooksAdded;
    }

    public static int getTotalBooksRemoved() {
        return totalBooksRemoved;
    }
}

// ============================
// Member CLASS
// ============================
class Member {
    private int memberID;
    private String name;
    private int borrowedBooks = 0;
    private int totalBorrows = 0;
    private int totalReturns = 0;

    private static int totalMembersAdded = 0;
    private static int totalBooksBorrowed = 0;
    private static int totalBooksReturned = 0;

    private int borrowedBookID = -1;
    
    public Member(int memberID, String name) {
        this.memberID = memberID;
        this.name = name;
        totalMembersAdded++;
    }

    public boolean borrowBook(Book book) {
        if (borrowedBookID == -1 && book != null && book.borrow()) {
            borrowedBooks++;
            totalBorrows++;
            totalBooksBorrowed++;
            borrowedBookID = book.getBookID();
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (book != null && borrowedBookID == book.getBookID()) {
            book.returnBook();
            borrowedBooks--;
            totalReturns++;
            totalBooksReturned++;
            borrowedBookID = -1;
            return true;
        }
        return false;
    }

    public void displayMemberInfo() {
        System.out.printf("Member ID: %d | Name: %s | Borrowed Books: %d\n", memberID, name, borrowedBooks);
    }

    public void displayStatistics() {
        System.out.printf("Total Books Borrowed: %d | Total Books Returned: %d\n", totalBorrows, totalReturns);
    }

    public int getMemberID() {
        return memberID;
    }

    public int getBorrowedBookID() {
        return borrowedBookID;
    }

    public static int getTotalMembersAdded() {
        return totalMembersAdded;
    }

    public static int getTotalBooksBorrowed() {
        return totalBooksBorrowed;
    }

    public static int getTotalBooksReturned() {
        return totalBooksReturned;
    }
}

// ============================
// Library CLASS
// ============================
class Library {
    private Book[] books;
    private Member[] members;
    private int bookCount = 0;
    private int memberCount = 0;

    public Library(int maxBooks, int maxMembers) {
        books = new Book[maxBooks];
        members = new Member[maxMembers];
    }

    public void addBook(int bookID, String title, String author, int stock) {
        // Prevent duplicate book IDs
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookID() == bookID) {
                System.out.println("❌ Book ID already exists!");
                return;
            }
        }
        if (bookCount < books.length) {
            books[bookCount++] = new Book(bookID, title, author, stock);
        } else {
            System.out.println("❌ Book storage full!");
        }
    }

    public void removeBook(int bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookID() == bookID) {
                Book.incrementRemovedBooks();
                books[i] = books[--bookCount];
                System.out.println("✅ Book removed successfully.");
                return;
            }
        }
        System.out.println("❌ Book not found.");
    }

    public void addMember(int memberID, String name) {
        if (memberCount < members.length) {
            members[memberCount++] = new Member(memberID, name);
        } else {
            System.out.println("❌ Member limit reached!");
        }
    }

    public void generateLibraryReport() {
        System.out.println("\n📊 Library Report:");
        System.out.printf("Total Books Added: %d | Total Books Removed: %d\n",
                Book.getTotalBooksAdded(), Book.getTotalBooksRemoved());
        System.out.printf("Total Members: %d\n", Member.getTotalMembersAdded());
        System.out.printf("Total Books Borrowed: %d | Total Books Returned: %d\n",
                Member.getTotalBooksBorrowed(), Member.getTotalBooksReturned());
    }

    public Book getBook(int bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookID() == bookID) {
                return books[i];
            }
        }
        return null;
    }

    public Member getMember(int memberID) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberID() == memberID) {
                return members[i];
            }
        }
        return null;
    }

    public void displayAvailableBooks() {
        System.out.println("\n📚 Available Books:");
        for (int i = 0; i < bookCount; i++) {
            books[i].displayBookInfo();
        }
    }
}

// ============================
// MAIN CLASS
// ============================
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(10, 5);

        // Add sample books
        library.addBook(112, "The Great Gatsby", "F. Scott Fitzgerald", 7);
        library.addBook(1123, "1984", "George Orwell", 7);
        library.addBook(11234, "To Kill a Mockingbird", "Harper Lee", 7);
        library.addBook(11235, "Pride and Prejudice", "Jane Austen", 7);
        library.addBook(112345, "Moby Dick", "Herman Melville", 7);

        // Add one sample member
        library.addMember(406, "Eyas");

        while (true) {
            System.out.println("\n👋 Welcome to the Library System!");
            System.out.print("Enter your Member ID: ");
            int memberID = scanner.nextInt();

            Member member = library.getMember(memberID);
            if (member == null) {
                System.out.println("❌ Invalid Member ID!");
                continue;
            }

            System.out.println("Welcome, " + memberID + "!");

            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("\n===== Member Menu =====");
                System.out.println("1. View Available Books");
                System.out.println("2. Borrow a Book");
                System.out.println("3. Return a Book");
                System.out.println("4. View My Statistics");
                System.out.println("5. Log Out");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        library.displayAvailableBooks();
                        break;
                    case 2:
                        System.out.print("Enter Book ID to Borrow: ");
                        int borrowBookID = scanner.nextInt();
                        Book borrowBook = library.getBook(borrowBookID);

                        if (borrowBook != null && member.borrowBook(borrowBook)) {
                            System.out.println("✅ Book Borrowed Successfully!");
                        } else {
                            System.out.println("❌ Book Unavailable or Already Borrowed!");
                        }
                        break;
                    case 3:
                        Book borrowedBook = library.getBook(member.getBorrowedBookID());
                        if (borrowedBook != null && member.returnBook(borrowedBook)) {
                            System.out.println("✅ Book Returned Successfully!");
                        } else {
                            System.out.println("❌ No Borrowed Book to Return!");
                        }
                        break;
                    case 4:
                        member.displayStatistics();
                        break;
                    case 5:
                        System.out.println("👋 Logging out...");
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice!");
                }
            }
        }
    }
}
