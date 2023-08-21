package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactProgram {
	private String jdbcURL;
	private String username; 
	private String password;
	private Connection connection;

    public static void main(String[] args) {
    	String jdbcURL = "jdbc:postgresql://localhost:5432/Jira_Plugin_DB";
        String username = "postgres";
        String password = "1234";
        
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to PostgreSQL server");

            // DELETE METHOD
            deleteContact(connection, 9);

            // INSERT METHOD
            insertContact(connection, "Barry", "Allen6", "flash@gmail.com");
            
            // SELECT METHOD
            printTable(connection);
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in connecting to PostgreSQL server");
            e.printStackTrace();
        }
    }
  
    public static boolean insertContact(Connection connection, String firstName, String lastName, String email) {
        try {
          // code from above
            String sql = "INSERT INTO Contacts (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("A new contact has been inserted.");
        return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting contact.");
            e.printStackTrace();
        }
    
    return false;
    }
    
 	public static boolean deleteContact(Connection connection, int id) {
        try {
          // code from above
            String deleteSQL = "DELETE FROM contacts WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setInt(1, id);
            int rows = deleteStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("The contact has been deleted.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting contact.");
            e.printStackTrace();
        }
    
    return false;
    }

 	public static void printTable(Connection connection) throws SQLException {
      PreparedStatement printOutStatement = connection.prepareStatement("SELECT * FROM contacts");
      ResultSet resultSet = printOutStatement.executeQuery();
      while (resultSet.next()) {
          System.out.print(resultSet.getInt("Id"));
          System.out.print(" - " + resultSet.getString("first_name"));
          System.out.print(" - " + resultSet.getString("last_name"));
          System.out.println(" - " + resultSet.getString("email"));
      }
  }
	public String getJdbcURL() {
	    return jdbcURL;
	}

	public void setJdbcURL(String jdbcURL) {
	    this.jdbcURL = jdbcURL;
	}

	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}

	public Connection getConnection() {
	    return connection;
	}

	public void setConnection(Connection connection) {
	    this.connection = connection;
	}
}
