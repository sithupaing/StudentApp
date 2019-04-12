/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import student.app.dao.StudentDAO;
import student.app.model.Student;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class EditController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    
    private int studentId;
    
    private StudentDAO studentDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maleRadio.setUserData("Male");
        femaleRadio.setUserData("Female");
        studentDAO = new StudentDAO();
    }    

    @FXML
    private void updateStudent(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        RadioButton selectedRadio = (RadioButton)genderGroup.getSelectedToggle();
        String genderStr = (String)selectedRadio.getUserData();
        LocalDate dobLocalDate =  dobPicker.getValue();
        
        // Validate 
        if(name.isEmpty() || email.isEmpty() || dobLocalDate == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please fill out all fields.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.show();
            return;
        }
        
        
        Date dob = Date.valueOf(dobLocalDate);
        
        Student sd = new Student(studentId,name,email,genderStr,dob);
        
        try {
            studentDAO.updateStudent(sd);
            Stage currentStage = (Stage)saveBtn.getScene().getWindow();
            currentStage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void closeCurrentWindow(ActionEvent event) {
         Stage currentStage = (Stage)saveBtn.getScene().getWindow();
         currentStage.close();
    }
    
    
    public void setStudentData(Student selectedStudent){
        studentId = selectedStudent.getId();
        nameField.setText(selectedStudent.getName());
        emailField.setText(selectedStudent.getEmail());
        if(selectedStudent.getGender().equals("Male")){
            maleRadio.setSelected(true);
        }else{
            femaleRadio.setSelected(true);
        }
        Date sqlDob =  selectedStudent.getDob();
        dobPicker.setValue(sqlDob.toLocalDate());
    }
    
}
