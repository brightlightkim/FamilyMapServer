import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessExample {

    public static void main(String [] args) {
        try {
            DatabaseAccessExample example = new DatabaseAccessExample();
            List<Book> books = example.doTransaction();
            System.out.println("Final Book List:");
            example.printBooks("\t", books);
        } catch (SQLException e) {
            System.err.println("Unable to list books because of exception " + e.getMessage());
        }
    }

    public List<Book> doTransaction() throws SQLException {

        List<Book> books;

        String dbName = "db" + File.separator + "bookclub.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;

        Connection connection = null;
        try(Connection c = DriverManager.getConnection(connectionURL)) {
            connection = c;

            // Start a transaction
            connection.setAutoCommit(false);

            // Remove all books
            removeBooks(connection);
            System.out.println();

            // Insert books
            insertBooks(connection, getBooksToInsert());
            System.out.println();

            // Retrieve books
            books = getBooks(connection);
            System.out.println("Retrieved the following books from the database:");
            printBooks("\t", books);
            System.out.println();

            // Change the author of the first book
            Book book = books.get(0);
            book.setAuthor("Jerod Wilkerson");
            updateBook(connection, books.get(0));
            System.out.println();

            // Commit the transaction
            connection.commit();
        } catch(SQLException ex) {
            if(connection != null) {
                connection.rollback();
            }
            throw ex;
        }

        return books;
    }

    private List<Book> getBooksToInsert() {
        List<Book> books = new ArrayList<>();

        books.add(new Book("Decision Points", "George W. Bush", "NonFiction", 7));
        books.add(new Book("The Work and the Glory", "Gerald Lund", "HistoricalFiction", 3));
        books.add(new Book("Dracula", "Bram Stoker", "Fiction", 8));
        books.add(new Book("The Holy Bible", "The Lord", "NonFiction", 5));

        return books;
    }

    private void printBooks(String prefix, List<Book> books) {
        for(Book book : books) {
            System.out.println(prefix + book);
        }
    }

    private void removeBooks(Connection connection) throws SQLException {
        String sql = "delete from book";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {

            int count = stmt.executeUpdate();

            // Reset the auto-increment counter so new books start over with an id of 1
            sql = "delete from sqlite_sequence where name = 'book'";
            try(PreparedStatement resetSequenceStatement = connection.prepareStatement(sql)) {
                resetSequenceStatement.executeUpdate();
            }

            System.out.printf("Deleted %d books\n", count);
        }
    }

    private void insertBooks(Connection connection, List<Book> books) throws SQLException {
        String sql = "insert into book (title, author, genre, category_id) values (?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            for(Book book : books) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getGenre());
                stmt.setInt(4, book.getCategoryId());

                if(stmt.executeUpdate() == 1) {
                    try(Statement keyStmt =  connection.createStatement();
                        ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()")) {
                        keyRS.next();
                        int id = keyRS.getInt(1); // ID of the inserted book
                        book.setId(id);
                    }

                    System.out.println("Inserted book " + book);
                } else {
                    System.out.println("Failed to insert book");
                }
            }
        }
    }

    private List<Book> getBooks(Connection connection) throws SQLException {

        List<Book> books = new ArrayList<>();

        String sql = "select id, title, author, genre, category_id from book";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String genre = rs.getString(4);
                int categoryId = rs.getInt(5);

                books.add(new Book(id, title, author, genre, categoryId));
            }
        }

        return books;
    }

    private void updateBook(Connection connection, Book book) throws SQLException {
        String sql = "update book " +
                "set title = ?, author = ?, genre = ?, category_id = ? " +
                "where id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());
            stmt.setInt(4, book.getCategoryId());
            stmt.setInt(5, book.getId());

            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated book " + book.getId());
            } else {
                System.out.println("Failed to update book " + book.getId());
            }
        }
    }
}