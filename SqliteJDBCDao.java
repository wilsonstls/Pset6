package pset6.dao; 

 
import pset6.AppOutput; 
import pset6.MetrolinkDao; 
import pset6.Stop;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.text.ParseException;
import java.util.*;
import java.util.Date;


/**
 * User: wilsonstls
 * Date: 5/27/15
 */
public class SqliteJDBCDao implements MetrolinkDao { 
 
 
     public static final String JDBC_SQLITE_METROLINK_DB = "jdbc:sqlite:metrolink.db"; 
     public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";
 
     private AppOutput appOutput; 
 
     /** list out all the Metrolink Stations */
     public List<Stop> getStopsAllStops() {
         appOutput.print("Fetching Metrolink stations...");
         try (Connection connection = getConnection();) { 
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT stop_name, stop_id FROM stops " +
                     "WHERE stop_name LIKE '%LINK STATION' ORDER BY stop_name asc");
             ResultSet resultSet = preparedStatement.executeQuery(); 
             List<Stop> stops = new ArrayList<>();
             while (resultSet.next()) { 
                 Stop stop = new Stop(); 
                 stop.setStopName(resultSet.getString("stop_name"));
                 stop.setStopID(resultSet.getString("stop_id"));
                 stops.add(stop); 
             }
             return stops;
         } catch (SQLException e) {
             throw new RuntimeException("Error retrieving stations from stops");
         }

     }

    /** prompt user for input on current location */
    public List<Stop> getStopsMyStop() {
        Scanner userInput = new Scanner(System.in);
        appOutput.print("\n  Enter the ID number of the station you are currently at  ");
        String stationID = userInput.nextLine();


        try (Connection connection = getConnection()) {

            PreparedStatement preStmt = connection.prepareStatement("SELECT stops.stop_name, stops.stop_id, " +
                    "stop_times.arrival_time FROM stops, stop_times WHERE (stops.stop_id = ?) AND " +
                    "(stop_times.stop_id = stops.stop_id) AND (stop_times.arrival_time > (time('now', 'localtime'))) " +
                    "ORDER BY stop_times.arrival_time asc LIMIT 1") ;

            preStmt.setString(1, stationID);

            ResultSet rS = preStmt.executeQuery();
                if (!rS.next()) {
                    appOutput.print("Entry -  " + stationID + "  - is not valid");
                }
                else
                   appOutput.print("\nMetrolink station where you are currently located...");
                   List<Stop> stops = new ArrayList<Stop>();
                   Stop stop = new Stop();
                   stop.setStopName(rS.getString("stop_name"));
                   stop.setStopID(rS.getString("stop_id"));
                /** calculate number of minutes for the next train to arrive */
                   String arrTime = rS.getString("arrival_time");
                   SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                   Date date1 = format.parse(String.valueOf(LocalTime.now()));
                   Date date2 = format.parse(arrTime);
                   long diffMinutes = (date2.getTime() - date1.getTime()) / 60 / 1000;

                   stop.setStopMinutes(diffMinutes);
                   stops.add(stop);
                   return stops;


        } catch(SQLException e){
            throw new RuntimeException("SQL Error -   " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException("Parsing Error -  " + e.getMessage());
        }

    }


        private static Connection getConnection() throws SQLException {
         try { 
             Class.forName(ORG_SQLITE_JDBC); 
         } catch (ClassNotFoundException e) { 
             throw new RuntimeException("Unable to find class for loading the database", e); 
         } 
         return DriverManager.getConnection(JDBC_SQLITE_METROLINK_DB); 
        }
 
 
        public void setAppOutput(AppOutput appOutput) {
         this.appOutput = appOutput; 
        }
} 
