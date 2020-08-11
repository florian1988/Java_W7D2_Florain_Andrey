package Florian;

import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;


public class Menu {
    Statement statement = null;

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/university";
        String user = "root";
        String password = "";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException e) {
            e.getSQLState();
            e.printStackTrace();
        }
        return connection;
    }


    public void insertNewStudents(){


        try{
            try{
                Connection con = connect();
                statement = con.createStatement();
                String sql = " insert into students (name, address)values('Andreas Herzog', 'Halldorf 5')";

                int rowsAffected = statement.executeUpdate(sql);
                System.out.println(rowsAffected);
            }finally {
                if(statement != null) statement.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void insertNewCourses(){
        try{
            try{
                Connection con = connect();
                statement = con.createStatement();
                String sql = "insert into courses (name) values('Bratwurst Kurs')";

                int rowsAffected = statement.executeUpdate(sql);
                System.out.println(rowsAffected);
            }finally {
                if(statement != null) statement.close();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void insertNewEnrollments(Integer idStudent, Integer idCourse){

        try{


            try{
                Connection con = connect();
                statement = con.createStatement();
                String sql = "insert into enrollments (fk_students , fk_courses) values (" + idStudent + "," + idCourse + ")";

                int rowsAffected = statement.executeUpdate(sql);
                System.out.println(rowsAffected);

            }finally {
                if(statement != null) statement.close();
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Print 1 if you want to enrolle again?");
        System.out.println("Print 2 for end enrollement");
        int enrolle = scan.nextInt();
        if(enrolle == 1){
           enrolingProcedureBeginn();
        }else{
            System.out.println("Exit Program");
            System.exit(0);
        }


    }

    public void enrolingProcedureBeginn(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Enrole for a course, put in your name ");
        String nameInput = scan.nextLine();
        System.out.println(nameInput);
        searchForStudent(nameInput);
    }


    public void searchForStudent(String nameInput){
        Integer idStudent = null;
        String nameStudent = null;
        try{
            try{
                Connection con = connect();
                statement = con.createStatement();
                ResultSet resultSet =null;
                try{
                    String sql = "Select students.Id_students, students.name from students where students.name like '" + nameInput +"'";
                    resultSet = statement.executeQuery(sql);

                    while(resultSet.next()){
                         idStudent = resultSet.getInt("students.id_students");
                         nameStudent = resultSet.getString("students.name");

                         System.out.println(idStudent + "  " + nameStudent);
                    }
                }finally {
                    if(resultSet != null) resultSet.close();
                }
            }finally {
                if(statement != null) statement.close();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        enrollStudent(idStudent , nameStudent);
    }


    public  void enrollStudent(int idStudent , String nameStudent){
        Integer idCourse = null;
        String nameCourse = null;

        try{
            try{
                Connection con = connect();
                statement = con.createStatement();
                ResultSet resultSet = null;
                try{
                    String sql = "Select * from courses";
                    resultSet = statement.executeQuery(sql);


                        while(resultSet.next()){
                            idCourse = resultSet.getInt("courses.ID_Courses");
                            nameCourse = resultSet.getString("courses.name");
                            System.out.println( idCourse + ")  " +nameCourse);
                        }


                }finally {
                    if(resultSet != null) resultSet.close();
                }
            }finally {
                if(statement != null) statement.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose your course write the number:");
        int inputCourse = scan.nextInt();

        insertNewEnrollments(idStudent, inputCourse);
    }








}
