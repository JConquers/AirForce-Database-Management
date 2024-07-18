import java.sql.*;
import java.util.Scanner;

public class QueryHandler {
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

    static void processQuery10(Connection conn) throws SQLException {
        // Get the flying hours of an Aviator on a particular _Type_ of plane
        try {
            conn.setAutoCommit(false);
            // Prompt user for Aviator's service number and Aircraft _Type_
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Aviator's Service Number: ");
            String serviceNum = scanner.nextLine();
            System.out.print("Enter Aircraft _Type_ (1-Transport, 2-Fighter, 3-Chopper, 4-Reconnaissance): ");
            int aircraft_Type_ = scanner.nextInt();

            // Prepare SQL statement to retrieve flying hours of the Aviator on the
            // specified _Type_ of plane
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
            System.out.println("Total flying hours of Aviator " + serviceNum + " on Aircraft _Type_ " + aircraft_Type_
                    + ": " + totalHours);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
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
                if (rowsAffected <= 0) {
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
                        ", Holdings: " + rs.getInt("Holdings") +
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
            String baseName = scanner.nextLine();

            // Prepare SQL statement to retrieve details of the Aviator
            String sql = "DELETE FROM BASE WHERE Location = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, baseName);

            // Execute the query
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Deleted base at " + baseName);
            } else {
                System.out.println("Could not delete base");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void processQuery14(Connection conn) {
        String aid = "", baseName = "";
        try {
            conn.setAutoCommit(false);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter AID of Aircarft: ");
            aid = scanner.nextLine();
            // System.out.println("GOKHRU\n");
            System.out.println("Enter name of Base: ");
            baseName = scanner.nextLine();

            String sql = "INSERT INTO DEPLOYED_AT(AID, Base) VALUES(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aid);
            pstmt.setString(2, baseName);

            pstmt.executeUpdate();

            String sql2 = "UPDATE BASE SET Holdings=Holdings+1 WHERE Location=?;";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, baseName);
            if (pstmt2.executeUpdate() > 0) {
                System.out.println("Deployment successful.");
                conn.commit();
                conn.setAutoCommit(true);
                return;
            } else {
                System.out.println("Could not add deployment.");
                conn.rollback();
                conn.setAutoCommit(true);
                return;
            }
            // scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void processQuery15(Connection conn) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name of Base: ");
            String baseName = scanner.nextLine();
            System.out.println("Enter Service_num of Aviator: ");
            String serviceNum = scanner.nextLine();
            String sql = "UPDATE BASE SET Commander= ? WHERE Location=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, baseName);
            pstmt.setString(2, serviceNum);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(serviceNum + " now commands the airbase at " + baseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                        ", Holdings: " + rs.getInt("Holdings") +
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
    
}
