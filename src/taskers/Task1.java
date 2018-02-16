/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;


/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    public ThreadState state;
    boolean exit = false;
    //ObjectProperty state;
    
    private Notifiable notificationTarget;
    
    public Task1(int maxValue, int notifyEvery, ThreadState state)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
        this.state = state;
    }
    
    @Override
    public void run() {
        doNotify("Task1 start.");
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task1: " + i + " State of task is "+ state );
            }
            
            if (exit) {
                state= ThreadState.STOPPED;
                doNotify("Task1 stopped. State now is "+ state);
                return;
            }
        }
        state= ThreadState.STOPPED;
        doNotify("Task1 done. State now is "+ state);
        
    }
    
    public void end() {
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(String message) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> {
                notificationTarget.notify(message, state);
            });
        }
    }
}