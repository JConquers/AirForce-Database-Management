// export CLASSPATH='/home/jinesh14/mysql-connector-j-8.3.0.jar:.'

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class AirForceDB {

   // Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/AirForce?useSSL=false";


   // Database credentials
   static final String USER = "root";// add your user
   static final String PASSWORD = "root";// add password

   static void showMenu(){
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
        "13. Remove a Base\n"+
        "14. Deploy an Aircaft.\n"+
        "15. Assign Commander for a Base.\n"+
        "16. Show Aircrafts.\n"+
        "17. Show Bases.\n"+
        "18. Show Aviators\n"+
        "19. Show deployments\n"+
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
    




public static void main(String[] args) throws SQLException {
      Connection conn = null;
      Statement stmt = null;

      
      Scanner scanner = new Scanner(System.in);
      // Prompt the user to input username
      System.out.print("Enter username: ");
      String USER = scanner.nextLine();

      // Prompt the user to input password
      System.out.print("Enter password: ");
      String PASSWORD = scanner.nextLine();
      System.out.print("password: "+PASSWORD);


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

          // Run createTables.sql script
          runScript("/home/jinesh14/imt2022044_JDBC_Project/sql/createTables.sql", conn);
                
          // Run populateForDemo.sql script
          runScript("/home/jinesh14/imt2022044_JDBC_Project/sql/populateForDemo.sql", conn);



         int q_choice=-1;
         showMenu();
         conn.setAutoCommit(false);
        do{
            System.out.println("\n");
            System.out.print("Enter your choice: ");
            if(scanner.hasNextInt()) q_choice = scanner.nextInt();

            switch(q_choice){
                case 1:{ processQuery1(conn); conn.commit();break;}
                case 2:{ processQuery2(conn); conn.commit();break;}
                case 3:{ processQuery3(conn); conn.commit();break; }
                case 4:{ processQuery4(conn); conn.commit();break;}
                case 5:{ processQuery5(conn); conn.commit();break;}
                case 6:{ processQuery6(conn); conn.commit();break;}
                case 7:{ processQuery7(conn); conn.commit();break;}
                case 8:{ processQuery8(conn); conn.commit();break;}
                case 9:{ processQuery9(conn); conn.commit();break;}
                case 10:{ processQuery10(conn); conn.commit();break;}
                case 11:{ processQuery11(conn); conn.commit();break;}
                case 12:{ processQuery12(conn); conn.commit();break;}
                case 13:{ processQuery13(conn); conn.commit();break;}
                case 14:{ processQuery14(conn); conn.commit();break;}
                case 15:{ processQuery15(conn); conn.commit();break;}
                case 16:{ processQuery16(conn); conn.commit();break;}
                case 17:{ processQuery17(conn); conn.commit();break;}
                case 18:{ processQuery18(conn); conn.commit();break;}
                case 19:{ processQuery19(conn); conn.commit();break;}
                case 20:{ processQuery20(conn); conn.commit();break;}


            }
        } while(q_choice!=-1);

            
        
        
      } catch (SQLException se) { // Handle errors for JDBC
         conn.rollback();
         se.printStackTrace();
      } catch (Exception e) { // Handle errors for Class.forName
         e.printStackTrace();
      } 
      finally { // finally block used to close resources regardless of whether an exception was
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

   

   static void processQuery1(Connection conn) {
    // Add an Aviator
    try {
        // Prompt user for Aviator details
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Service Number: ");
        String serviceNum = scanner.nextLine();
        System.out.print("Enter Rank: ");
        int rank = scanner.nextInt();
        System.out.print("Enter Flying Hours: ");
        int flyingHours = scanner.nextInt();
        
        // Prepare SQL statement to insert Aviator
        String sql = "INSERT INTO AVIATOR (Service_num, _Rank_, Flying_hours) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, serviceNum);
        pstmt.setInt(2, rank);
        pstmt.setInt(3, flyingHours);
        
        // Execute the statement
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " Aviator(s) inserted successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery2(Connection conn) {
    // Add a Base
    try {
        // Prompt user for Base details
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        System.out.print("Enter Holdings: ");
        int holdings = scanner.nextInt();
        System.out.print("Enter Commander's Service Number: ");
        String commander = scanner.next();
        
        // Prepare SQL statement to insert Base
        String sql = "INSERT INTO BASE (Location, Capacity, Holdings, Commander) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        pstmt.setInt(2, capacity);
        pstmt.setInt(3, holdings);
        pstmt.setString(4, commander);
        
        // Execute the statement
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " Base(s) inserted successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery3(Connection conn) {
    // Add an Aircraft
    try {
        // Prompt user for Aircraft details
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Aircraft ID: ");
        String aid = scanner.nextLine();
        System.out.print("Enter _Type_: ");
        int _Type_ = scanner.nextInt();
        System.out.print("Enter Crew Count: ");
        int crew = scanner.nextInt();
        
        // Prepare SQL statement to insert Aircraft
        String sql = "INSERT INTO AIRCRAFT (AID, _Type_, Crew) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, aid);
        pstmt.setInt(2, _Type_);
        pstmt.setInt(3, crew);
        
        // Execute the statement
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " Aircraft(s) inserted successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery4(Connection conn) {
    // Get list of Aircrafts stationed at a Base
    try {
        // Prompt user for Base location
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Base Location: ");
        String baseLocation = scanner.nextLine();
        
        // Prepare SQL statement to retrieve Aircrafts stationed at the Base
        String sql = "SELECT AID FROM DEPLOYED_AT WHERE Base = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, baseLocation);
        
        // Execute the query
        ResultSet rs = pstmt.executeQuery();
        
        // Display the result
        System.out.println("Aircraft(s) stationed at " + baseLocation + ":");
        while (rs.next()) {
            System.out.println(rs.getString("AID"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


static void processQuery5(Connection conn) {
    // Get list of Aviators with a minimum number of flying hours
    try {
        // Prompt user for minimum flying hours
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum flying hours: ");
        int minFlyingHours = scanner.nextInt();
        
        // Prepare SQL statement to retrieve Aviators with minimum flying hours
        String sql = "SELECT * FROM AVIATOR WHERE Flying_hours >= ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, minFlyingHours);
        
        // Execute the query
        ResultSet rs = pstmt.executeQuery();
        
        // Display the result
        System.out.println("Aviators with a minimum of " + minFlyingHours + " flying hours:");
        while (rs.next()) {
            System.out.println("Service Number: " + rs.getString("Service_num") +
                               ", Rank: " + rs.getInt("_Rank_") +
                               ", Flying Hours: " + rs.getInt("Flying_hours"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery6(Connection conn) {
    // Get the Aircraft with the highest flight time
    try {
        // Prepare SQL statement to retrieve Aircraft with the highest flight time
        String sql = "SELECT AID FROM FLIES GROUP BY AID ORDER BY SUM(Hours) DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        if (rs.next()) {
            System.out.println("Aircraft with the highest flight time: " + rs.getString("AID"));
        } else {
            System.out.println("No aircraft found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery7(Connection conn) {
    // Get the Base with the highest capacity
    try {
        // Prepare SQL statement to retrieve Base with the highest capacity
        String sql = "SELECT Location FROM BASE ORDER BY Capacity DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        if (rs.next()) {
            System.out.println("Base with the highest capacity: " + rs.getString("Location"));
        } else {
            System.out.println("No base found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
static void processQuery8(Connection conn) {
    // Get the Aviator with the highest flying hours
    try {
        // Prepare SQL statement to retrieve Aviator with the highest flying hours
        String sql = "SELECT Service_num FROM AVIATOR ORDER BY Flying_hours DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        if (rs.next()) {
            System.out.println("Aviator with the highest flying hours: " + rs.getString("Service_num"));
        } else {
            System.out.println("No aviator found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery9(Connection conn) {
    // Get the details of all Aircrafts flown by an Aviator
    try {
        // Prompt user for Aviator's service number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Aviator's Service Number: ");
        String serviceNum = scanner.nextLine();
        
        // Prepare SQL statement to retrieve Aircrafts flown by the Aviator
        String sql = "SELECT AID FROM FLIES WHERE Service_num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, serviceNum);
        
        // Execute the query
        ResultSet rs = pstmt.executeQuery();
        
        // Display the result
        System.out.println("Aircraft(s) flown by Aviator " + serviceNum + ":");
        while (rs.next()) {
            System.out.println(rs.getString("AID"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery10(Connection conn) {
    // Get the flying hours of an Aviator on a particular _Type_ of plane
    try {
        // Prompt user for Aviator's service number and Aircraft _Type_
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Aviator's Service Number: ");
        String serviceNum = scanner.nextLine();
        System.out.print("Enter Aircraft _Type_ (1-Transport, 2-Fighter, 3-Chopper, 4-Reconnaissance): ");
        int aircraft_Type_ = scanner.nextInt();
        
        // Prepare SQL statement to retrieve flying hours of the Aviator on the specified _Type_ of plane
        String sql = "SELECT Hours FROM FLIES f JOIN AIRCRAFT a ON f.AID = a.AID WHERE Service_num = ? AND _Type_ = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, serviceNum);
        pstmt.setInt(2, aircraft_Type_);
        
        // Execute the query
        ResultSet rs = pstmt.executeQuery();
        
        // Calculate total flying hours
        int totalHours = 0;
        while (rs.next()) {
            totalHours += rs.getInt("Hours");
        }
        
        // Display the result
        System.out.println("Total flying hours of Aviator " + serviceNum + " on Aircraft _Type_ " + aircraft_Type_ + ": " + totalHours);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



  
static void processQuery11(Connection conn) {
    // Record a sortie/flight (update the flight hours)
    try {
        // Prompt user for Aircraft ID, Aviator's Service Number, and hours flown
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Aircraft ID: ");
        String aid = scanner.nextLine();
        System.out.print("Enter Aviator's Service Number: ");
        String serviceNum = scanner.nextLine();
        System.out.print("Enter Hours Flown: ");
        int hours = scanner.nextInt();
        
        // Prepare SQL statement to update flight hours for the aircraft
        String sql = "UPDATE FLIES SET Hours = Hours + ? WHERE AID = ? AND Service_num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, hours);
        pstmt.setString(2, aid);
        pstmt.setString(3, serviceNum);
        // Execute the update
        int rowsAffected = pstmt.executeUpdate();
        // Check if update was successful
        if (rowsAffected > 0) {
            System.out.println("Flight recorded successfully.");
        } else { // FLight not present. Hence a tuple has to be added.
            sql = "INSERT INTO FLIES(AID, Service_num, Hours) VALUES(?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aid);
            pstmt.setString(2, serviceNum);
            pstmt.setInt(3, hours);
            rowsAffected = pstmt.executeUpdate();
            if(rowsAffected<=0){
                System.out.println("Failed to add");
                return;
            }
        // Execute the update
        }

        // Prepare SQL statement to update flight hours for the aviator
        sql = "UPDATE AVIATOR SET FLying_hours = Flying_hours + ? WHERE Service_num = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, hours);
        pstmt.setString(2, serviceNum);
        // Execute the update
        rowsAffected = pstmt.executeUpdate();
        // Check if update was successful
        if (rowsAffected > 0) {
            System.out.println("Flight recorded successfully.");
        } else {
            System.out.println("Failed to record flight.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery12(Connection conn) {
    // Get details of a Base
    try {
        // Prompt user for Base location
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Base Location: ");
        String location = scanner.nextLine();
        
        // Prepare SQL statement to retrieve details of the Base
        String sql = "SELECT * FROM BASE WHERE Location = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, location);
        
        // Execute the query
        ResultSet rs = pstmt.executeQuery();
        
        // Display the result
        if (rs.next()) {
            System.out.println("Base Location: " + rs.getString("Location") +
                               ", Capacity: " + rs.getInt("Capacity") +
                               ", Holdings: "+ rs.getInt("Holdings")+
                               ", Commander: " + rs.getString("Commander"));
        } else {
            System.out.println("Base not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery13(Connection conn) {
    // Get details of an Aviator
    try {
        // Prompt user for Aviator's Service Number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter loaction of base to  be deleted: ");
        String baseName= scanner.nextLine();
        
        // Prepare SQL statement to retrieve details of the Aviator
        String sql = "DELETE FROM BASE WHERE Location = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, baseName);
        
        // Execute the query
        if(pstmt.executeUpdate()>0){
            System.out.println("Deleted base at "+baseName);
        }
        else{
            System.out.println("Could not delete base" );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery14(Connection conn) {
    String aid="", baseName="";
    try{
        //conn.setAutoCommit(false);

        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter AID of Aircarft: "); aid=scanner.nextLine();
        //System.out.println("GOKHRU\n");
        System.out.println("Enter name of Base: "); baseName=scanner.nextLine();
        
        String sql="INSERT INTO DEPLOYED_AT(AID, Base) VALUES(?, ?)";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, aid);
        pstmt.setString(2, baseName);

        pstmt.executeUpdate();

        String sql2="UPDATE BASE SET Holdings=Holdings+1 WHERE Location=?;";
        PreparedStatement pstmt2=conn.prepareStatement(sql2);
        pstmt2.setString(1, baseName);
        if(pstmt2.executeUpdate()>0){
            System.out.println("Deployment successful.");
            /*conn.commit();
            conn.setAutoCommit(true);*/
            return;
        }
        else{ 
            System.out.println("Could not add deployment.");
            /*conn.rollback();
            conn.setAutoCommit(true); */
            return;
        }
        //scanner.close();
        
    }
    catch (SQLException e) {
         e.printStackTrace(); 
    }
        

}


static void processQuery15(Connection conn) {
    try{
    Scanner scanner=new Scanner(System.in);
    System.out.println("Enter name of Base: "); String baseName=scanner.nextLine();
    System.out.println("Enter Service_num of Aviator: "); String serviceNum=scanner.nextLine();
    String sql="UPDATE BASE SET Commander= ? WHERE Location=?";

    PreparedStatement pstmt=conn.prepareStatement(sql);
    pstmt.setString(1, baseName);
    pstmt.setString(2, serviceNum);
    int rowsAffected = pstmt.executeUpdate();
    System.out.println(serviceNum+ " now commands the airbase at "+ baseName);
    }
    catch(SQLException e){ e.printStackTrace(); }
}

static void processQuery16(Connection conn) {
    // Show Aircrafts
    try {
        // Prepare SQL statement to retrieve all aircrafts
        String sql = "SELECT * FROM AIRCRAFT";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        System.out.println("List of Aircrafts:");
        while (rs.next()) {
            System.out.println("Aircraft ID: " + rs.getString("AID") +
                               ", _Type_: " + rs.getInt("_Type_") +
                               ", Crew: " + rs.getInt("Crew"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery17(Connection conn) {
    // Show Bases
    try {
        // Prepare SQL statement to retrieve all bases
        String sql = "SELECT * FROM BASE";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        System.out.println("List of Bases:");
        while (rs.next()) {
            System.out.println("Base Location: " + rs.getString("Location") +
            ", Capacity: " + rs.getInt("Capacity") +
            ", Holdings: "+ rs.getInt("Holdings")+
            ", Commander: " + rs.getString("Commander"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery18(Connection conn) {
    // Show Aviators
    try {
        // Prepare SQL statement to retrieve all aviators
        String sql = "SELECT * FROM AVIATOR";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        System.out.println("List of Aviators:");
        while (rs.next()) {
            System.out.println("Service Number: " + rs.getString("Service_num") +
                               ", Rank: " + rs.getInt("_Rank_") +
                               ", Flying Hours: " + rs.getInt("Flying_hours"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery19(Connection conn) {
    // Show Deployments
    try {
        // Prepare SQL statement to retrieve all deployments
        String sql = "SELECT * FROM DEPLOYED_AT";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        System.out.println("List of Deployments:");
        while (rs.next()) {
            System.out.println("Aircraft ID: " + rs.getString("AID") +
                               ", Base Location: " + rs.getString("Base"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

static void processQuery20(Connection conn) {
    // Show Flying Records
    try {
        // Prepare SQL statement to retrieve all flying records
        String sql = "SELECT * FROM FLIES";
        Statement stmt = conn.createStatement();
        
        // Execute the query
        ResultSet rs = stmt.executeQuery(sql);
        
        // Display the result
        System.out.println("List of Flying Records:");
        while (rs.next()) {
            System.out.println("Aircraft ID: " + rs.getString("AID") +
                               ", Service Number: " + rs.getString("Service_num") +
                               ", Hours: " + rs.getInt("Hours"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

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
                    // Trim excess whitespace and newline characters
                    String trimmedSqlStatement = sqlStatement.trim();
                    if (!trimmedSqlStatement.isEmpty()) {
                        // Execute the SQL statement
                        statement.execute(trimmedSqlStatement);
                    }
                }
            }
            
            System.out.println("Script executed successfully: " + filePath);
        } catch (IOException | SQLException e) {
            // Handle SQL exception due to table already exists
            if (e.getMessage().contains("Table") && e.getMessage().contains("already exists")) {
                System.out.println("Table already exists: " + filePath);
            } else {
                e.printStackTrace();
            }
        }
    }
    



   
} // end class

// Note : By default autocommit is on. you can set to false using
// con.setAutoCommit(false)

