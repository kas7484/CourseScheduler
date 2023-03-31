/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author krishshah
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addEntry;
    private static PreparedStatement getEnrolledStudents;
    private static PreparedStatement getSchedule;
    private static PreparedStatement dropSchedule;
    private static int count;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {   
            addEntry = connection.prepareStatement("insert into app.schedule (semester,studentid,coursecode,status,timestamp) values (?,?,?,?,?)");
            addEntry.setString(1, entry.getSemester());
            addEntry.setString(2, entry.getStudentID());
            addEntry.setString(3, entry.getCourseCode());
            addEntry.setString(4, entry.getStatus());
            addEntry.setTimestamp(5,entry.getTimeStamp());
            addEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }          
    } 
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedule = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester=? and studentid=?");
            getSchedule.setString(1,semester);
            getSchedule.setString(2,studentID);
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("StudentID"),resultSet.getString("Coursecode"),resultSet.getString("status"),resultSet.getTimestamp("timestamp"));
                entries.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {   
            getEnrolledStudents = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ? and status = 'S'");
            getEnrolledStudents.setString(1, currentSemester);
            getEnrolledStudents.setString(2,courseCode);
            resultSet = getEnrolledStudents.executeQuery();
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String Semester,String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedule = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester=? and courseCode=? and status = 'S'");
            getSchedule.setString(1,Semester);
            getSchedule.setString(2,courseCode);
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("StudentID"),resultSet.getString("Coursecode"),resultSet.getString("status"),resultSet.getTimestamp("timestamp"));
                entries.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;    
    }
    
    public static ArrayList<ScheduleEntry> getWaitListedStudentsByCourse(String Semester,String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedule = connection.prepareStatement("select semester,studentid,coursecode,status,timestamp from app.schedule where semester=? and courseCode=? and status = 'W' order by timestamp");
            getSchedule.setString(1,Semester);
            getSchedule.setString(2,courseCode);
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("StudentID"),resultSet.getString("Coursecode"),resultSet.getString("status"),resultSet.getTimestamp("timestamp"));
                entries.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return entries;    
    }
    
    public static void dropStudentScheduleByCourse(String Semester,String StudentID,String courseCode){
        connection = DBConnection.getConnection();
        try
        {   dropSchedule = connection.prepareStatement("delete from app.schedule where semester=? and studentID = ? and courseCode = ?");
            dropSchedule.setString(1,Semester);
            dropSchedule.setString(2,StudentID);
            dropSchedule.setString(3,courseCode);
            dropSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }   
    }    
    public static void dropScheduleByCourse(String Semester,String courseCode){
        connection = DBConnection.getConnection();
        try
        {   dropSchedule = connection.prepareStatement("delete from app.schedule where semester=? and coursecode = ?");
            dropSchedule.setString(1,Semester);
            dropSchedule.setString(2,courseCode);
            dropSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }   
    }   
    public static void updateScheduleEntry(String Semester,ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {  
            dropSchedule = connection.prepareStatement("update app.schedule set status ='S' where semester = ? and studentid= ? and coursecode = ?");
            dropSchedule.setString(1,Semester);
            dropSchedule.setString(2,entry.getStudentID());
            dropSchedule.setString(3,entry.getCourseCode());
            dropSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }   
    }   
    public static String getStudentScheduleStatusByCourse(String Semester,String StudentID,String courseCode){
        connection = DBConnection.getConnection();
        String Status ="";
        try
        {   dropSchedule = connection.prepareStatement("select status from app.schedule where semester=? and studentID = ? and courseCode = ?");
            dropSchedule.setString(1,Semester);
            dropSchedule.setString(2,StudentID);
            dropSchedule.setString(3,courseCode);
            resultSet=dropSchedule.executeQuery();
            while(resultSet.next()){
                Status = resultSet.getString(1);
            }            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        } 
        return Status;
    }      
}