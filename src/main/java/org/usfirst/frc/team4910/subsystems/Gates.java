package org.usfirst.frc.team4910.subsystems;

import org.usfirst.frc.team4910.iterator.Iterate;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Gates {
	
	public static Gates instance = new Gates();
	
	private DoubleSolenoid gates;
	
	public static Gates getInstance() {
		return instance == null ? instance = new Gates() : instance;
	}
	
	public Gates() {
		gates = new DoubleSolenoid(4, 5);
	}
	
	public enum GateState{
		Open, Closed
	}
	
	private final Iterate GateLoop = new Iterate() {

		@Override
		public void init() {
			// TODO Auto-generated method stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public Iterate getIterate() {
		return GateLoop;
	}
	
	public synchronized void toggleGates() {
		if(gates.get() == DoubleSolenoid.Value.kForward) {
			gates.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			gates.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	public synchronized void openGates() {
		gates.set(DoubleSolenoid.Value.kForward);
	}
	
	public synchronized void closeGates() {
		gates.set(DoubleSolenoid.Value.kReverse);
	}

}
