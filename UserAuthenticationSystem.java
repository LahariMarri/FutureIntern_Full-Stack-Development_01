package userForm;

import java.sql.*;
import java.util.Scanner;

public class UserAuthenticationSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option from the below:");
            System.out.println("1. **Register**");
            System.out.println("2. **Login**");
            System.out.println("3. **View Users**");
            System.out.println("4. **Exit**");

            int choice = Integer.parseInt(sc.nextLine());
            try 
            {
                if (choice == 1)
                {
                    registerUser(sc);
                } 
                else if (choice == 2) 
                {
                    loginUser(sc);
                }
                else if (choice == 3) 
                {
                    viewUsers();
                } 
                else if (choice == 4) 
                {
                    System.out.println("Your are Exiting the page...");
                    break;   
                } 
                else 
                {
                    System.out.println("Oops!! Invalid choice. Please try again.");
                }
            } 
            catch (SQLException e) 
            {
            	System.out.println("An error occured in the internal server");
                e.printStackTrace();
            }
        }
        sc.close();
    }

    private static void registerUser(Scanner sc) throws SQLException {
        System.out.print("Enter UserName: ");
        String UserName = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        int hashedPassword = Password.hashPassword(password);

        System.out.print("Enter role (USER/ADMIN): ");
        String Role = sc.nextLine().toUpperCase();

        try (Connection con = DBConnection.getConnection()) 
        {
            String sql = "INSERT INTO RegistrationForm (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, UserName);
            ps.setInt(2, hashedPassword);
            ps.setString(3, Role.toUpperCase());

            int result = ps.executeUpdate();
            if (result > 0) 
            {
                System.out.println("Hi User You Completed the Registration successfully! Thank you!");
            } 
            else 
            {
                System.out.println("Registration failed. Please try again.");
            }
        }
           catch(SQLException e)
          {
        	  System.out.println("Error Occured at the time of registration...Please Wait!!!");
        	  e.printStackTrace();
          
          }
       
    }

    private static void loginUser(Scanner sc) throws SQLException {
        System.out.print("Enter UserName: ");
        String UserName = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        int hashedPassword = Password.hashPassword(password);

        try (Connection con = DBConnection.getConnection()) 
        {
            String sql = "SELECT role FROM RegistrationForm WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, UserName);
            ps.setInt(2, hashedPassword);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String role = rs.getString("role");
                System.out.println("Login successful! You are logged in as " + role);
            } else 
            {
                System.out.println("Invalid username or password.");
            }
        }
            catch(SQLException e)
            {
          	  System.out.println("Error Occured During Login ...Please Wait!!!");
          	  e.printStackTrace();
            
            }
        }
    
    private static void viewUsers() throws SQLException 
    {
        try (Connection con = DBConnection.getConnection()) 
        {
            PreparedStatement ps = con.prepareStatement("SELECT UserName, password, role FROM RegistrationForm");
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- User List ---");
            while (rs.next())
            {
                String UserName = rs.getString("username");
                String password = rs.getString("password");
                String Role = rs.getString("role");
                System.out.println("Username: " + UserName + "\tPassword: " + password + "\tRole: " + Role);
            }
        }
        catch(SQLException e)
        {
      	  System.out.println("Error Occured during fetching the userlist...Please Wait!!!");
      	  e.printStackTrace();
        
        }
    }
}
