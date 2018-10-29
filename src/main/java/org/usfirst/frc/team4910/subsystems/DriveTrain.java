package org.usfirst.frc.team4910.subsystems;

import org.usfirst.frc.team4910.iterator.Iterate;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveTrain {

	public static DriveTrain instance = new DriveTrain();
	
	private TalonSRX leftDrive1, leftDrive2, rightDrive1, rightDrive2;
	private DoubleSolenoid shifter;
	
	private DriveState state;
	
	public enum DriveState{
		VBus, Brake
	}
	
	public static DriveTrain getInstance() {
		return instance == null ? instance = new DriveTrain(): instance;
	}
	
	public DriveTrain() {
		leftDrive1 = new TalonSRX(1);
		leftDrive2 = new TalonSRX(2);
		rightDrive1 = new TalonSRX(3);
		rightDrive2 =  new TalonSRX(4);
		
		leftDrive1.setNeutralMode(NeutralMode.Brake);
		leftDrive2.setNeutralMode(NeutralMode.Brake);
		rightDrive1.setNeutralMode(NeutralMode.Brake);
		rightDrive2.setNeutralMode(NeutralMode.Brake);
		
		leftDrive1.configNominalOutputForward(0, 10);
		leftDrive2.configNominalOutputForward(0, 10);
		rightDrive1.configNominalOutputReverse(0, 10);
		rightDrive2.configNominalOutputReverse(0, 10);
		
		leftDrive2.set(ControlMode.Follower, 1);
		rightDrive2.set(ControlMode.Follower, 3);
		
		shifter = new DoubleSolenoid(6, 7);
		state = DriveState.Brake;
	}
	
	private final Iterate DriveLoop = new Iterate(){

		@Override
		public void init() {
			// TODO Auto-generated method stub
			synchronized(DriveTrain.this) {
			driveVBus(0, 0);
			setState(DriveState.VBus);
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(state == DriveState.Brake) {
				driveVBus(0, 0);
			}
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
			synchronized(DriveTrain.this) {
				driveVBus(0, 0);
			}
		}
		
	};
	
	public Iterate getIterate() {
		return DriveLoop;
	}
	
	public synchronized void toggleShift() {
		if(shifter.get() == DoubleSolenoid.Value.kForward) {
			shifter.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			shifter.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	public synchronized void setHighGear() {
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	
	public synchronized void setLowGear() {
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public synchronized void setState(DriveState newState) {
		state = newState;
	}
	
	//Accounts for reversals
	public synchronized void driveVBus(double left, double right) {
		leftDrive1.set(ControlMode.PercentOutput, -left);
		rightDrive1.set(ControlMode.PercentOutput, right);
	}
}
