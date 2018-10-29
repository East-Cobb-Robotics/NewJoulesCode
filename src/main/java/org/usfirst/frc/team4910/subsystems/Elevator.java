package org.usfirst.frc.team4910.subsystems;

import org.usfirst.frc.team4910.iterator.Iterate;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {
	
	public static Elevator instance = new Elevator();
	
	private TalonSRX elevator;
	private EleState state;
	
	public static Elevator getInstance() {
		return instance == null ? instance = new Elevator() : instance;
	}
	
	public enum EleState{
		On, Off
	}
	
	public Elevator() {
		elevator = new TalonSRX(5);
		
		elevator.configNominalOutputForward(0, 10);
		elevator.configNominalOutputReverse(0, 10);
		elevator.setNeutralMode(NeutralMode.Brake);
		
		state = EleState.Off;
	}

	private final Iterate EleLoop = new Iterate() {

		@Override
		public void init() {
			// TODO Auto-generated method stub
			synchronized(Elevator.this) {
				setOff();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(Elevator.this) {
			if(state == EleState.On) {
				setOn();
			}
			else {
				setOff();
			}
			}
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
			synchronized(Elevator.this) {
				setOff();
			}
		}
	};
	
	public Iterate getIterate() {
		return EleLoop;
	}
	
	public synchronized void toggleElevator() {
		if(state == EleState.Off) {
			state = EleState.On;
		}
		else {
			state = EleState.Off;
		}
	}
	
	public synchronized void setOn() {
		state = EleState.On;
		elevator.set(ControlMode.PercentOutput, .5);
	}
	
	public synchronized void setOff() {
		state = EleState.Off;
		elevator.set(ControlMode.PercentOutput, 0);
	}
}
