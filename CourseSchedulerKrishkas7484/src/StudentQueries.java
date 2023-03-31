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
public class StudentQueries {

    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    private static StudentEntry student;


    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (StudentID,Firstname,lastname) values (?,?,?)");
            addStudent.setString(1, student.StudentID);
            addStudent.setString(2, student.FirstName);
            addStudent.setString(3, student.LastName);
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }        
        
    } 
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentID,Firstname,lastname from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry student = new StudentEntry(resultSet.getString("Studentid"),resultSet.getString("Firstname"),resultSet.getString("Lastname"));
                students.add(student);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;    
    }
    public static StudentEntry getStudent(String StudentID){
        connection = DBConnection.getConnection();
        try
        {
            getAllStudents = connection.prepareStatement("select studentID,Firstname,lastname from app.student where studentID = (?)");
            getAllStudents.setString(1,StudentID);
            resultSet = getAllStudents.executeQuery();
            while (resultSet.next()){
                student = new StudentEntry(resultSet.getString("Studentid"),resultSet.getString("Firstname"),resultSet.getString("Lastname"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }  
        return student;
    }
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {   
            dropStudent = connection.prepareStatement("delete from app.student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }          
    }          
}
    
    
 
