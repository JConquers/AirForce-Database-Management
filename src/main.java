// export CLASSPATH='/home/jinesh14/mysql-connector-j-8.3.0.jar:.'

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class main {

    // Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306?useSSL=false";

    // Database credentials
    static String USER = "_xyz_";// add your user or enter through terminal
    static String PASSWORD = "_xyz_";// add password or enter through terminal

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        Scanner scanner = new Scanner(System.in);
        // Prompt the user to input username
        System.out.print("Enter username: ");
        USER = scanner.nextLine();

        // Prompt the user to input password
        System.out.print("Enter password: ");
        PASSWORD = scanner.nextLine();
        System.out.print("password: " + PASSWORD);

        // STEP 2. Connecting to the Database
        try {
            // STEP 2a: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 2b: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            // STEP 2c: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            // --- Preliminary steps to create and populate the DB
            // Run createTables.sql script
            runScript("sql/createTables.sql", conn);
            // Run populateForDemo.sql script
            runScript("sql/populateForDemo.sql", conn);

            int q_choice = -1;
            showMenu();
            conn.setAutoCommit(false); // ---- IMP ----
            do {
                System.out.println("\n");
                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt())
                    q_choice = scanner.nextInt();

                switch (q_choice) {
                    case 1: {
                        QueryHandler.processQuery1(conn);
                        conn.commit();
                        break;
                    }
                    case 2: {
                        QueryHandler.processQuery2(conn);
                        conn.commit();
                        break;
                    }
                    case 3: {
                        QueryHandler.processQuery3(conn);
                        conn.commit();
                        break;
                    }
                    case 4: {
                        QueryHandler.processQuery4(conn);
                        conn.commit();
                        break;
                    }
                    case 5: {
                        QueryHandler.processQuery5(conn);
                        conn.commit();
                        break;
                    }
                    case 6: {
                        QueryHandler.processQuery6(conn);
                        conn.commit();
                        break;
                    }
                    case 7: {
                        QueryHandler.processQuery7(conn);
                        conn.commit();
                        break;
                    }
                    case 8: {
                        QueryHandler.processQuery8(conn);
                        conn.commit();
                        break;
                    }
                    case 9: {
                        QueryHandler.processQuery9(conn);
                        conn.commit();
                        break;
                    }
                    case 10: {
                        QueryHandler.processQuery10(conn);
                        conn.commit();
                        break;
                    }
                    case 11: {
                        QueryHandler.processQuery11(conn);
                        conn.commit();
                        break;
                    }
                    case 12: {
                        QueryHandler.processQuery12(conn);
                        conn.commit();
                        break;
                    }
                    case 13: {
                        QueryHandler.processQuery13(conn);
                        conn.commit();
                        break;
                    }
                    case 14: {
                        QueryHandler.processQuery14(conn);
                        conn.commit();
                        break;
                    }
                    case 15: {
                        QueryHandler.processQuery15(conn);
                        conn.commit();
                        break;
                    }
                    case 16: {
                        QueryHandler.processQuery16(conn);
                        conn.commit();
                        break;
                    }
                    case 17: {
                        QueryHandler.processQuery17(conn);
                        conn.commit();
                        break;
                    }
                    case 18: {
                        QueryHandler.processQuery18(conn);
                        conn.commit();
                        break;
                    }
                    case 19: {
                        QueryHandler.processQuery19(conn);
                        conn.commit();
                        break;
                    }
                    case 20: {
                        QueryHandler.processQuery20(conn);
                        conn.commit();
                        break;
                    }

                }
            } while (q_choice != -1);

        } catch (SQLException se) { // Handle errors for JDBC
            conn.rollback();
            se.printStackTrace();
        } catch (Exception e) { // Handle errors for Class.forName
            e.printStackTrace();
        } finally { // finally block used to close resources regardless of whether an exception was
                    // thrown or not
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("End of Code");
    } // end main

    // --- To run SQL script ---
    static void runScript(String filePath, Connection connection) {
        try {
            // Read the SQL script file
            File file = new File(filePath);
            StringBuilder scriptContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    scriptContent.append(line);
                    scriptContent.append("\n");
                }
            }

            // Split script content into individual SQL statements
            String[] sqlStatements = scriptContent.toString().split(";");

            // Execute each SQL statement
            try (Statement statement = connection.createStatement()) {
                for (String sqlStatement : sqlStatements) {
                    String trimmedSqlStatement = sqlStatement.trim();
                    if (!trimmedSqlStatement.isEmpty()) {
                        statement.execute(trimmedSqlStatement); // Execute the SQL statement
                    }
                }
            }

            System.out.println("Script executed successfully: " + filePath);
        } catch (IOException | SQLException e) {
            // Handle SQL exception due to table already exists
            if (e.getMessage().contains("Table") && e.getMessage().contains("already exists")) {
                System.out.println("Table already exists: " + filePath);
            } 
            else  if (e.getMessage().contains("database exists")) {
                System.out.println("DATABASE ALREADY EXISTS\n");
                try(Statement stmt = connection.createStatement()){
                    stmt.execute("USE AirForceDB;");
                }
                catch(SQLException s){
                    System.out.println("Failed to choose DB.");
                }
            }
            else {
                e.printStackTrace();
            }
        }
    }

    static void showMenu() {
        displayMenu("Ranks",
                "1- Air commodore\n" +
                        "2- Group Captain\n" +
                        "3- Wing commander\n" +
                        "4- Squadron Leader\n" +
                        "5- Flight Lieutenant.");

        displayMenu("Aircraft _Type_",
                "1- Transport\n" +
                        "2- Fighter\n" +
                        "3- Chopper\n" +
                        "4- Reconnaissance");

        displayMenu("Queries",
                "1. Add an Aviator. (Service_num: SNxxx)\n" +
                        "2. Add a Base. (Case sensitive)\n" +
                        "3. Add an Aircraft. (AID: IAxxx)\n" +
                        "4. Get list of Aircrafts stationed at a Base.\n" +
                        "5. Get list of Aviators with a minimum number of flying hours.\n" +
                        "6. Get the Aircraft with the highest flight time.\n" +
                        "8. Get the Aviator with the highest flying hours.\n" +
                        "9. Get the details of all Aircrafts flown by an Aviator.\n" +
                        "10. Get the flying hours of an Aviator on a particular _Type_ of plane.\n" +
                        "11. Record a sortie/ flight (update the flight hours).\n" +
                        "12. Get details of a Base.\n" +
                        "13. Remove a Base\n" +
                        "14. Deploy an Aircaft.\n" +
                        "15. Assign Commander for a Base.\n" +
                        "16. Show Aircrafts.\n" +
                        "17. Show Bases.\n" +
                        "18. Show Aviators\n" +
                        "19. Show deployments\n" +
                        "20. Show flying records.\n");
    }

    public static void displayMenu(String title, String options) {
        int width = 80;
        // Print the top line
        System.out.print("+");
        for (int i = 0; i < width - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        // Print the title
        System.out.print("|");
        int spaces = (width - title.length() - 2) / 2;
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.print(title);
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println("|");

        // Print the middle line
        System.out.print("+");
        for (int i = 0; i < width - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        // Print the options
        System.out.println(options);

        // Print the bottom line
        System.out.print("+");
        for (int i = 0; i < width - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

}

// Note : By default autocommit is on. you can set to false using
// con.setAutoCommit(false)
