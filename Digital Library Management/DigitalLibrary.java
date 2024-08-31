import java.io.*;
import java.util.*;

class Book {
    String id, title, author;
    boolean isIssued = false;

    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + isIssued;
    }

    static Book fromString(String data) {
        String[] fields = data.split(",");
        Book book = new Book(fields[0], fields[1], fields[2]);
        book.isIssued = Boolean.parseBoolean(fields[3]);
        return book;
    }
}

class User {
    String id, name;

    User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "," + name;
    }

    static User fromString(String data) {
        String[] fields = data.split(",");
        return new User(fields[0], fields[1]);
    }
}

public class DigitalLibrary {
    private static final String BOOKS_FILE = "books.txt";
    private static final String USERS_FILE = "users.txt";
    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadBooks();
        loadUsers();

        while (true) {
            System.out.println("1. Admin 2. User 3. Exit");
            switch (scanner.nextInt()) {
                case 1 -> adminModule();
                case 2 -> userModule();
                case 3 -> { saveBooks(); saveUsers(); return; }
            }
        }
    }

    private static void adminModule() {
        System.out.println("1. Add Book 2. View Books 3. Remove Book 4. Add User 5. View Users");
        switch (scanner.nextInt()) {
            case 1 -> books.add(new Book(nextLine("Book ID"), nextLine("Title"), nextLine("Author")));
            case 2 -> viewBooks();
            case 3 -> books.removeIf(book -> book.id.equals(nextLine("Book ID to remove")));
            case 4 -> users.add(new User(nextLine("User ID"), nextLine("User Name")));
            case 5 -> users.forEach(user -> System.out.println(user.id + ": " + user.name));
        }
    }

    private static void userModule() {
        System.out.println("1. View Books 2. Issue Book 3. Return Book");
        switch (scanner.nextInt()) {
            case 1 -> viewBooks();
            case 2 -> toggleBookStatus(true);
            case 3 -> toggleBookStatus(false);
        }
    }

    private static void viewBooks() {
        books.forEach(book -> System.out.println(book.id + ": " + book.title + " by " + book.author + " (Issued: " + book.isIssued + ")"));
    }

    private static void toggleBookStatus(boolean issue) {
        String id = nextLine("Book ID");
        books.stream().filter(book -> book.id.equals(id)).findFirst().ifPresent(book -> book.isIssued = issue);
    }

    private static String nextLine(String prompt) {
        System.out.print(prompt + ": ");
        scanner.nextLine();  // Consume newline
        return scanner.nextLine();
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Books file not found, starting fresh.");
        }
    }

    private static void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            books.forEach(pw::println);
        } catch (IOException e) {
            System.out.println("Failed to save books!");
        }
    }

    private static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Users file not found, starting fresh.");
        }
    }

    private static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            users.forEach(pw::println);
        } catch (IOException e) {
            System.out.println("Failed to save users!");
        }
    }
}
