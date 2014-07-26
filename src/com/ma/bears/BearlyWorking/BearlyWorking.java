package com.ma.bears.BearlyWorking;

import java.util.Vector;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Watchdog;

/**
 * Java code for testing ports on a robot
 * 
 * Not tested yet
 * 
 * TODO:
 *
 * 
 * @author Nicky Ivy nickivyca@gmail.com
 */
public class BearlyWorking extends IterativeRobot {
	
	//we use vectors because Java ME
	//doesn't support ArrayLists
	
	Vector Victors = new Vector();
	Vector DIOs = new Vector();
	Vector Relays = new Vector();
	Vector Solenoids = new Vector();
	Vector Analogs = new Vector();
    
    public BearlyWorking(){
    	for(int i = 1; i <= 10; i++){
    		Victors.addElement(new Victor(i));
    	}
    	for(int i = 1; i <= 14; i++){
    		DIOs.addElement(new DigitalInput(i));
    	}
    	for(int i = 1; i <= 8; i++){
    		Relays.addElement(new Relay(i));
    	}
    	for(int i = 1; i <= 8; i++){
    		Solenoids.addElement(new Solenoid(i));
    	}
    	for(int i = 1; i <= 7; i++){ //gives us an Analog8 is already allocated if we use it
    		Analogs.addElement(new AnalogChannel(i));
    	}
    }
    
    public void robotInit(){
    	System.out.println("BearlyWorking V: 1");
    	//during robotinit place all on SmartDashboard
    	//this includes places to set all speed
    	//controllers and relays
    	for(int i = 1; i <= 10; i++){
    		SmartDashboard.putNumber(("PWM" + i), 0);
    	}
		SmartDashboard.putNumber("AllPWM", 0);
    	for (int i = 1; i <= 8; i++){
    		SmartDashboard.putBoolean(("RelayForward" + i), false);
    	}
    	for (int i = 1; i <= 8; i++){
    		SmartDashboard.putBoolean(("RelayReverse" + i), false);
    	}
		SmartDashboard.putBoolean("RelayForwardAll", false);
		SmartDashboard.putBoolean("RelayReverseAll", false);
    	for(int i = 1; i <= 8; i++){
    		SmartDashboard.putBoolean(("Solenoid" + i), false);
    	}
    	SmartDashboard.putBoolean("SolenoidAll", false);

    	for(int i = 1; i <= 14; i++){
    		SmartDashboard.putBoolean(("DIO" + i), false);
    	}
    	for(int i = 1; i <= 8; i++){
    		SmartDashboard.putNumber(("Analog" + i), 0);
    	}
    }
    
    public void teleopPeriodic(){
    	//first deal with set-alls
    	double allpwm = SmartDashboard.getNumber("AllPWM");
    	if(allpwm != 0){
        	for(int i = 1; i <= 10; i++){
        		SmartDashboard.putNumber(("PWM" + i), allpwm);
        	}
    	}
    	boolean allforward = SmartDashboard.getBoolean("RelayForwardAll");
    	if(allforward){
        	for(int i = 1; i <= 8; i++){
        		SmartDashboard.putBoolean(("RelayForward" + i), allforward);
        	}
    	}
    	boolean allreverse = SmartDashboard.getBoolean("RelayReverseAll");
    	if(allreverse){
        	for(int i = 1; i <= 8; i++){
        		SmartDashboard.putBoolean(("RelayReverse" + i), allreverse);
        	}
    	}
    	//solenoidall = all solenoids on
    	boolean solenoidall = SmartDashboard.getBoolean("SolenoidAll");
    	if(solenoidall){
        	for(int i = 1; i <= 8; i++){
        		SmartDashboard.putBoolean(("Solenoid" + i), solenoidall);
        	}
    	}
    	
    	//get/set all values
    	//get from DIOs and set speeds/relays
    	//in a Vector the first index is 0
    	//so subtract 1 to get matching value from SmartDash
    	for(int i = 1; i <= 10; i++){
    		((Victor)Victors.elementAt(i-1)).set(SmartDashboard.getNumber("PWM" + i));
    	}
    	//for relays we can test both directions
    	//but that means we have to set them to 0
    	//at every loop
    	for(int i = 1; i <= 8; i++){
    		((Relay)Relays.elementAt(i-1)).set(Relay.Value.kOff);
    	}
    	for(int i = 1; i <= 8; i++){
    		if(SmartDashboard.getBoolean("RelayForward" + i))
    			((Relay)Relays.elementAt(i-1)).set(Relay.Value.kForward);
    	}
    	for(int i = 1; i <= 8; i++){
    		if(SmartDashboard.getBoolean("RelayReverse" + i))
    			((Relay)Relays.elementAt(i-1)).set(Relay.Value.kReverse);
    	}
    	for(int i = 1; i <= 8; i++){
    		((Solenoid)Solenoids.elementAt(i-1)).set(SmartDashboard.getBoolean("Solenoid" + i));
    	} 
    	

    	for(int i = 1; i <= 10; i++){
    		SmartDashboard.putBoolean(("DIO" + i),((DigitalInput)DIOs.elementAt(i-1)).get());
    	}
    	for(int i = 1; i <= 7; i++){
    		SmartDashboard.putNumber(("Analog" + i),((AnalogChannel)Analogs.elementAt(i-1)).getValue());
    	}
    	
        Watchdog.getInstance().feed(); //very hungry
    }
}
