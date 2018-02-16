/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;


/**
 *
 * @author dalemusser
 * 
 * This example uses a Notification functional interface.
 * This allows the use of anonymous inner classes or
 * lambda expressions to define the method that gets called
 * when a notification is to be made.
 */
public class Task2 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    public ThreadState state;
   
    
    private ArrayList<Notification> notifications = new ArrayList<>();
    
    public Task2(int maxValue, int notifyEvery, ThreadState state)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
        this.state=ThreadState.RUNNING;
    }
    
    @Override
    public void run() {
        doNotify("Started Task2!", state);
        
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task2: " + i ,state);
            }
            
            if (exit) {
                return;
            }
        }
        state=ThreadState.STOPPED;
        doNotify("Task2 done.", ThreadState.STOPPED);//2 pieces of data
        
        
    }
    
    public void end() {
        exit = true;
    }
    
    // this method allows a notification handler to be registered to receive notifications
    public void setOnNotification(Notification notification) {
        this.notifications.add(notification);
    }
    
    private void doNotify(String message, ThreadState state) {
        // this provides the notification through the registered notification handler
        for (Notification notification : notifications) {
            Platform.runLater(() -> {
                notification.handle(message, state);
            });
        }
        

    }
    
    
    
    
}