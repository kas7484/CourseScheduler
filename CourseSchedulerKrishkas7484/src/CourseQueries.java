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

public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getSeats;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {   
            addCourse = connection.prepareStatement("insert into app.course (semester,coursecode,description,seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }          
    }
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {   
            getAllCourses = connection.prepareStatement("select coursecode,description,seats from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {   
                CourseEntry course = new CourseEntry(semester,resultSet.getString("Coursecode"),resultSet.getString("Description"),resultSet.getInt("seats"));
                courses.add(course);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {   
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }    
    public static int getAllCourseSeats(String semester, String courseCode)
    {   int seat = 0;
        connection = DBConnection.getConnection();
        try
        {   
            getSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getSeats.setString(1, semester);
            getSeats.setString(2,courseCode);
            resultSet = getSeats.executeQuery();
            while(resultSet.next()){
            seat = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seat;     
    } 
    
    public static void dropCourse(String Semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {   
            dropCourse = connection.prepareStatement("delete from app.course where semester=? and courseCode = ?");
            dropCourse.setString(1, Semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }          
    }    
}
