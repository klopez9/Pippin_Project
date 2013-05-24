package pippin;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProcessorViewPanel implements Observer {
  private JTextField accumulator = new JTextField();
  private JTextField programCounter = new JTextField();
  private Machine machine;
  
  public ProcessorViewPanel(Machine machine) {
  	this.machine = machine;
  	machine.addObserver(this);
  }
  
  @Override
  public void update(Observable arg0, Object arg1) {
    accumulator.setText(""+machine.getAcc());
    programCounter.setText(""+machine.getPC());
  }
  
  public JComponent createProcessorDisplay() {
    // create a JPanel called retVal
  	JPanel retVal = new JPanel();
    // give retPanel a GridLayout(1,0)
  	retVal.setLayout(new GridLayout(1,0));
    // put in a JLabel using
    retVal.add(new JLabel("Accumulator: ", JLabel.RIGHT));
    // add the JTextField accumulator
    retVal.add(accumulator);
    // add a similar JLabel with the text "ProgramCounter: "
    retVal.add(new JLabel("ProgramCounter: ", JLabel.RIGHT));
    // add programCounter
    retVal.add(programCounter);
    return retVal;
  }
}