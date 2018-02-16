/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable, Startable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    @FXML
    private Button task1Button;
    @FXML
    private Button task2Button;
    @FXML
    private Button task3Button;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
     
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
       System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000, ThreadState.RUNNING);
            task1.setNotificationTarget(this);
            task1.start();
            task1Button.setText("Stop task 1");
        }
        else if(task1.state== ThreadState.RUNNING)
        {
            task1Button.setText("Start task 1");
            task1.end();
            task1=null;
            
        }
        else if(task1.state== ThreadState.STOPPED)
        {
            task1Button.setText("Start task 1");
            task1.end();
            task1=null;
            
        }
        
        
    }
    
    @Override
    public void notify(String message, ThreadState state) {
        if (message.equals("Task1 done. State now is STOPPED")) {
            task1Button.setText("Start task 1");
            task1 = null;
            
        }
        else if (message.equals("Task1 stopped. State now is STOPPED")) {
              task1Button.setText("Start task 1");
                task1 = null;  
        }
        textArea.appendText(message + "\n");
        
        
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2Button.setText("Stop task 2");
            task2 = new Task2(2147483647, 1000000, ThreadState.RUNNING);
            task2.setOnNotification((String message, ThreadState state) -> {
                textArea.appendText(message + " State: "+ state +"\n");
                if(state.equals(ThreadState.STOPPED))
                {
                    task2Button.setText("Start Task 2");
                }
            });
            
            task2.start();
            
        }        
        else if(task2.state.equals(ThreadState.RUNNING))
        {
            //stop it
            task2Button.setText("Start task 2");
            task2.end();
            task2=null;
            
        }
        else if(task2.state.equals(ThreadState.STOPPED))
        {
            //start it
            task2Button.setText("Start task 2");
            task2.end();
            task2=null;
            
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if (task3 == null) {
        System.out.println("start task 3");
        task3Button.setText("Stop Task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000, ThreadState.RUNNING);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + " State: " + (String)evt.getOldValue()+ "\n");
                if(((String)evt.getOldValue()).equals(ThreadState.STOPPED.toString()))
                {
                    task3Button.setText("Start Task 3");
                }
            });
            
            task3.start();
        }
        }else if(task3.state.equals(ThreadState.RUNNING))
        {
            //stop it
            task3Button.setText("Start task 3");
            task3.end();
            task3=null;
            
        }
        else if(task3.state.equals(ThreadState.STOPPED))
        {
            //start it
            task3Button.setText("Start task 3");
            task3.end();
            task3=null;
            
        }
        
        
    } 
}
