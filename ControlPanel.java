package pippin;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ControlPanel implements Observer {
	private Machine machine;
	private JButton stepButton;
	private JButton clearButton;
	private JButton runButton;
	private JButton reloadButton;
	private ImageIcon clearIcon = new ImageIcon("clear.jpg");
	private ImageIcon pauseIcon = new ImageIcon("pause.jpg");
	private ImageIcon reloadIcon = new ImageIcon("reload.jpg");
	private ImageIcon runIcon = new ImageIcon("run.jpg");
	private ImageIcon stepIcon = new ImageIcon("step.jpg");

	public ControlPanel(Machine machine) {
		this.machine = machine;
		machine.addObserver(this);
	}

	public void checkEnabledButtons() {
		stepButton.setEnabled(machine.getStepActive());
		clearButton.setEnabled(machine.getClearActive());
		runButton.setEnabled(machine.getRunSuspendActive());
		if (machine.getRunningActive()) {
			runButton.setIcon(pauseIcon);
		} else {
			runButton.setIcon(runIcon);
		}
		reloadButton.setEnabled(machine.getReloadActive());        
	}


	private class StepListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			machine.step(); // add this void method to Machine
		}
	}

	// Create an ActionListener called ClearListener that calls machine.clearMemory();
	// from the actionPerformed method and add that void method to Machine
	private class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			machine.clearAll();
		}
	}

	// Create an ActionListener called ReloadListener that calls machine.reload();
	// from the actionPerformed method and add that void method to Machine
	private class ReloadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			machine.reload();
		}
	}

	private class RunPauseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// changes button icon; if autostepping, show a pause icon;
			// if paused, show a run icon (a play button).
			if (machine.isAutoStepOn()) {
				machine.setAutoStepOn(false); // add this void method to Machine
				runButton.setIcon(runIcon);
			} else {
				machine.setAutoStepOn(true);
				runButton.setIcon(pauseIcon);
			}
		}
	}

	public JComponent createControlDisplay() {
		// TODO
		// create a JPanel called retVal and give it the same grid layout as in ProcessorViewPanel
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(1,0));
		// do the following:		
		stepButton = new JButton(stepIcon);
		stepButton.setBackground(Color.WHITE);
		stepButton.addActionListener(new StepListener());
		retVal.add(stepButton);

		// do similar steps for the clearButton, runButton, reloadButton
		clearButton = new JButton(clearIcon);
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(new ClearListener());
		retVal.add(clearButton);

		runButton = new JButton(runIcon);
		runButton.setBackground(Color.WHITE);
		runButton.addActionListener(new RunPauseListener());
		retVal.add(runButton);

		reloadButton = new JButton(reloadIcon);
		reloadButton.setBackground(Color.WHITE);
		reloadButton.addActionListener(new ReloadListener());
		retVal.add(reloadButton);

		return retVal;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		checkEnabledButtons();

	}
}