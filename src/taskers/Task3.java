/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses PropertyChangeSupport to implement
 * property change listeners.
 * 
 */
public class Task3 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    public ThreadState state;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public Task3(int maxValue, int notifyEvery, ThreadState state)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
        this.state=state;
    }
    
    @Override
    public void run() {
        
        doNotify("Task3 start.", state);
        state=ThreadState.RUNNING;
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task3: " + i, state);
            }
            
            if (exit) {
                return;
            }
        }
        state=ThreadState.STOPPED;
        doNotify("Task3 done.", state);
    }
    
    public void end() {
        exit = true;
    }
    
    // the following two methods allow property change listeners to be added
    // and removed
    public void addPropertyChangeListener(PropertyChangeListener listener) {
         pcs.addPropertyChangeListener(listener);
     }

     public void removePropertyChangeListener(PropertyChangeListener listener) {
         pcs.removePropertyChangeListener(listener);
     }
    
    private void doNotify(String message, ThreadState state) {
        // this provides the notification through the property change listener
        Platform.runLater(() -> {
            // I'm choosing not to send the old value (second param).  Sending "" instead.
            pcs.firePropertyChange("message", state.toString(), message);
        });
    }
}