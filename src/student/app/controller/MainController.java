/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import student.app.dao.StudentDAO;
import student.app.model.Student;

/**
 *
 * @author Sithu
 */
public class MainController implements Initializable {

    @FXML
    private ToggleGroup gender;
    
    @FXML
    private Button saveBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private DatePicker dobPicker;
    
    private StudentDAO studentDAO;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> idCol;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> emailCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, Date> dobCol;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        maleRadio.setUserData("Male");
        femaleRadio.setUserData("Female");
        
        initColumns();
        
        studentDAO = new StudentDAO();
        
        loadTableData();
        
    }    

    @FXML
    private void saveStudentInfo(ActionEvent event) {
        
        String name = nameField.getText();
        String email = emailField.getText();
        RadioButton selectedRadio = (RadioButton)gender.getSelectedToggle();
        String genderStr = (String)selectedRadio.getUserData();
        LocalDate dobLocalDate =  dobPicker.getValue();
        
        // Validate 
        if(name.isEmpty() || email.isEmpty() || dobLocalDate == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("please fill out all fields.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.show();
            return;
        }
        
        
        Date dob = Date.valueOf(dobLocalDate);
        
        Student sd = new Student(name,email,genderStr,dob);
        
        
        try {
            studentDAO.saveStudent(sd);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Student successfully saved.");
            alert.setHeaderText(null);
            alert.setTitle("Success");
            alert.show();
            clearForm();
            loadTableData();
        } catch (SQLException ex) {
            System.out.println("Error");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void clearForm() {
       nameField.clear();
       emailField.clear();
       maleRadio.setSelected(true);
       dobPicker.setValue(null);
    }

    private void initColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadTableData() {
        try {
            List<Student> students =  studentDAO.getStudents();
            studentTable.getItems().setAll(students);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadEditWindow(ActionEvent event) throws IOException {
        
       // Selected Student 
       Student selectedStudent =  studentTable.getSelectionModel().getSelectedItem();
       
       if(selectedStudent==null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select the student you want to delete.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.show();
            return;
       }
        
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/app/views/edit.fxml"));
       Parent root =  loader.load(); // EditController
       EditController controller = loader.getController();
       controller.setStudentData(selectedStudent);
       Scene scene = new Scene(root);
       Stage editStage = new Stage();
       editStage.setScene(scene);
       editStage.initModality(Modality.WINDOW_MODAL);
       editStage.initOwner(studentTable.getScene().getWindow());   
       editStage.showAndWait();
       loadTableData();
    }

    @FXML
    private void deleteSelectedStudent(ActionEvent event) {
       Student selectedStudent =  studentTable.getSelectionModel().getSelectedItem();
       
       if(selectedStudent==null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select the student you want to delete.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.show();
            return;
       }
       
        try {
            studentDAO.deleteStudent(selectedStudent.getId());
            studentTable.getItems().remove(selectedStudent);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
