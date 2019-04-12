/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import student.app.dao.StudentDAO;
import student.app.model.Student;

/**
 *
 * @author Sithu
 */
public class StudentDAOTest {
    
    public StudentDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     
     public void testSave() throws SQLException {
         Date date = new Date(System.currentTimeMillis());
         Student sd = new Student("Mg Mg","mgmg@gmail.com","Male",date);
         StudentDAO studentDAO = new StudentDAO();
         assertEquals(1, studentDAO.saveStudent(sd));
     }
     
    
      public void testGetStudents() throws SQLException {
         
         StudentDAO studentDAO = new StudentDAO();
         assertEquals(2, studentDAO.getStudents().size());
         
     }
      
      public void testGetStudent() throws SQLException {
         
         StudentDAO studentDAO = new StudentDAO();
         assertEquals("Su Su", studentDAO.getStudent(10).getName());
         
     }
      
      
      public void testUpdateStudent() throws SQLException {
         
         StudentDAO studentDAO = new StudentDAO();
         Student student = studentDAO.getStudent(10);
         student.setEmail("susu@code2.com");
         assertEquals(1, studentDAO.updateStudent(student));
         
     }
      
      @Test
      public void testDelete() throws SQLException {
         
         StudentDAO studentDAO = new StudentDAO();
         
         assertEquals(1, studentDAO.deleteStudent(11));
         
     }
}
