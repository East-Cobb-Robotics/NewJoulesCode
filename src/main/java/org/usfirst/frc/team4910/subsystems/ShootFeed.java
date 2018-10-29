package org.usfirst.frc.team4910.subsystems;

import org.usfirst.frc.team4910.iterator.Iterate;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Timer;

public class ShootFeed {

	public static ShootFeed instance = new ShootFeed();
	private TalonSRX shooter, feeder;
	private ShootFeedState state;
	
	public static ShootFeed getInstance() {
		return instance == null ? instance = new ShootFeed() : instance;
	}
	
	public enum ShootFeedState{
		SystemRunning, Off
	}
	
	public ShootFeed() {
		shooter = new TalonSRX(8);
		feeder = new TalonSRX(7);
		
		shooter.configNominalOutputForward(0, 10);
		shooter.configNominalOutputReverse(0, 10);
		feeder.configNominalOutputForward(0, 10);
		feeder.configNominalOutputReverse(0, 10);
		
		shooter.setNeutralMode(NeutralMode.Coast);
		feeder.setNeutralMode(NeutralMode.Brake);
		
		state = ShootFeedState.Off;
	}
	
	private final Iterate ShootFeedLoop = new Iterate() {

		@Override
		public void init() {
			// TODO Auto-generated method stub
			synchronized(ShootFeed.this) {
				stopSystem();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
			synchronized(ShootFeed.this) {
				stopSystem();
			}
		}
		
	};
	
	public Iterate getIterate() {
		return ShootFeedLoop;
	}
	
	public synchronized void systemToggle() {
		if(state == ShootFeedState.SystemRunning) {
			stopSystem();
		}
		else {
			runSystem();
		}
	}
	
	public synchronized void runSystem() {
		state = ShootFeedState.SystemRunning;
		shooter.set(ControlMode.PercentOutput, -.5);
		Timer.delay(1.5);
		feeder.set(ControlMode.PercentOutput, .4);
	}

	public synchronized void stopSystem() {
		state = ShootFeedState.Off;
		shooter.set(ControlMode.PercentOutput, 0);
		feeder.set(ControlMode.PercentOutput, 0);
	}
}
