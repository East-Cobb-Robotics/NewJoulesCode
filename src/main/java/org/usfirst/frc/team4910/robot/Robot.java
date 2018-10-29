/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4910.robot;

import org.usfirst.frc.team4910.iterator.Iterator;
import org.usfirst.frc.team4910.subsystems.Climber;
import org.usfirst.frc.team4910.subsystems.DriveTrain;
import org.usfirst.frc.team4910.subsystems.Elevator;
import org.usfirst.frc.team4910.subsystems.DriveTrain.DriveState;
import org.usfirst.frc.team4910.subsystems.Gates;
import org.usfirst.frc.team4910.subsystems.ShootFeed;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	private static Joystick leftJoy, rightJoy, thirdJoy;

	private DriveTrain drive;
	private Gates gates;
	private Elevator elevator;
	private ShootFeed shooter;
	private Climber climber;
	
	private Iterator teleopIterator;
	
	private Compressor comp;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		leftJoy = new Joystick(0);
		rightJoy = new Joystick(1);
		thirdJoy = new Joystick(2);
		
		comp = new Compressor();
		comp.setClosedLoopControl(true);
		
		teleopIterator = new Iterator();
		
		drive = DriveTrain.getInstance();
		gates = Gates.getInstance();
		elevator = Elevator.getInstance();
		shooter = ShootFeed.getInstance();
		climber = Climber.getInstance();
		
		teleopIterator.register(drive.getIterate());
		teleopIterator.register(gates.getIterate());
		teleopIterator.register(elevator.getIterate());
		teleopIterator.register(shooter.getIterate());
		teleopIterator.register(climber.getIterate());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void disabledInit() {
		drive.setState(DriveState.Brake);
		elevator.setOff();
		shooter.stopSystem();
		teleopIterator.stop();
		climber.lock();
	}
	
	public void disabledPeriodic() {
		drive.setState(DriveState.Brake);
		elevator.setOff();
		shooter.stopSystem();
		climber.lock();
	}
	
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}
	
	public void teleopInit() {
		drive.setState(DriveState.VBus);
		teleopIterator.start();
		drive.setHighGear();
		gates.closeGates();
		elevator.setOff();
		shooter.stopSystem();
		climber.lock();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		double leftDrive = Math.abs(leftJoy.getY()) <= 0.1 ? 0 : leftJoy.getY();
		double rightDrive= Math.abs(rightJoy.getY()) <= 0.1 ? 0 : rightJoy.getY();
		
		drive.driveVBus(leftDrive, rightDrive);
		if(leftJoy.getRawButton(2)) {
			while(leftJoy.getRawButton(2)) {};
			drive.toggleShift();
		}
		
		if(leftJoy.getRawButton(3)) {
			while(leftJoy.getRawButton(3)) {};
			gates.toggleGates();
		}
		
		if(leftJoy.getRawButton(4)) {
			while(leftJoy.getRawButton(4)) {};
			elevator.toggleElevator();
		}
		
		if(leftJoy.getRawButton(5)) {
			while(leftJoy.getRawButton(5)) {};
			climber.toggleClimb();
		}
		
		if(leftJoy.getRawButton(6)) {
			while(leftJoy.getRawButton(6)) {};
			shooter.systemToggle();
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
