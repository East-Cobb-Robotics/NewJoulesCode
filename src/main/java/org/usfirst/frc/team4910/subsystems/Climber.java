package org.usfirst.frc.team4910.subsystems;

import org.usfirst.frc.team4910.iterator.Iterate;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber {
	
	public static Climber instance = new Climber();
	
	private TalonSRX climber;
	private ClimbState state;
	
	public static Climber getInstance() {
		return instance == null ? instance= new Climber(): instance;
	}
	
	public enum ClimbState{
		Climbing, Off
	}
	
	public Climber() {
		climber = new TalonSRX(6);
		
		climber.configNominalOutputForward(0, 10);
		climber.configNominalOutputReverse(0, 10);
		
		climber.setNeutralMode(NeutralMode.Brake);
		state = ClimbState.Off;
	}
	
	private final Iterate ClimbLoop = new Iterate() {

		@Override
		public void init() {
			// TODO Auto-generated method stub
			synchronized(Climber.this) {
				lock();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
			synchronized(Climber.this) {
				lock();
			}
		}
		
	};
	
	public Iterate getIterate() {
		return ClimbLoop;
	}
	
	public synchronized void toggleClimb() {
		if(state == ClimbState.Off) {
			climb();
		}
		else {
			lock();
		}
	}
	
	public synchronized void climb() {
		state = ClimbState.Climbing;
		climber.set(ControlMode.PercentOutput, 1);
	}
	
	public synchronized void lock() {
		state = ClimbState.Off;
		climber.set(ControlMode.PercentOutput, 0);
	}

}
