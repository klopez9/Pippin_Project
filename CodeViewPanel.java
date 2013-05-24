package pippin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CodeViewPanel implements Observer
{
	private JTextField[] stringFields = new JTextField[Memory.DATA_SIZE];
	private JTextField[] binaryFields = new JTextField[Memory.DATA_SIZE];
	private Machine machine;
	int currentLocation = 0;
	
  public CodeViewPanel(Machine machine) {
    this.machine = machine;
    machine.addObserver(this);
  }
  
  public JComponent createCodeDisplay() {
  	
  	JPanel returnPanel = new JPanel();
  	returnPanel.setLayout(new BorderLayout());
  	returnPanel.add(new JLabel("Code Memory View", JLabel.CENTER), BorderLayout.PAGE_START);
  	
  	JPanel panel = new JPanel();
  	panel.setLayout(new BorderLayout());
  	
  	JPanel numPanel = new JPanel();
  	JPanel intPanel = new JPanel();
  	JPanel binaryPanel = new JPanel();
  	numPanel.setLayout(new GridLayout(0,1));
  	intPanel.setLayout(new GridLayout(0,1));
  	binaryPanel.setLayout(new GridLayout(0,1));
  	
  	panel.add(numPanel, BorderLayout.LINE_START);
  	panel.add(intPanel, BorderLayout.CENTER);
  	panel.add(binaryPanel, BorderLayout.LINE_END);
  	
  	for (int i = 0; i < Memory.DATA_SIZE; i++) {
  		numPanel.add(new JLabel(" " + i + ": ", JLabel.RIGHT));
  		stringFields[i] = new JTextField(10);
  		intPanel.add(stringFields[i]);
  		binaryFields[i] = new JTextField(50);
  		binaryPanel.add(binaryFields[i]);
  	}
  	
  	returnPanel.add(new JScrollPane(panel));
  	return returnPanel;
  }

  @Override
  public void update(Observable o, Object msg) {
    if ("New Program".equals(msg)) {
        for(int i = 0; i < Memory.CODE_SIZE; i++) {
            try {
                CodeLine line = machine.getLine(i);
                if(line != null) { // many CodeLines may be null so WE NEED THIS CHECK
                    String source = line.toString();
                    String binary = machine.getInstructionBinary(i);
                    stringFields[i].setText(source);
                    binaryFields[i].setText(binary);
                }
            } catch (CodeAccessException e) {
//USE A JOptionPane.showMessageDialog TO SHOW e.getMessage()
            	JOptionPane.showMessageDialog(null, e.getMessage(),
            			"Code Access Error", JOptionPane.WARNING_MESSAGE);
            }
        }
//HOW TO COLORIZE A ROW (FEEL FREE TO USE A DIFFERENT COLOR SCHEME):
        currentLocation = 0;
        stringFields[currentLocation].setBackground(Color.YELLOW);
        binaryFields[currentLocation].setBackground(Color.YELLOW);
    } else if ("Clear".equals(msg)) {
        for(int i = 0; i < Memory.CODE_SIZE; i++) {
            stringFields[i].setText("");
            binaryFields[i].setText("");                
            stringFields[i].setBackground(Color.WHITE);
            binaryFields[i].setBackground(Color.WHITE);
        }            
    } else {
//SET THE COLOR OF THE TWO currentLocation FIELDS TO WHITE
    	stringFields[currentLocation].setBackground(Color.WHITE);
    	binaryFields[currentLocation].setBackground(Color.WHITE);
//CHANGE currentLocation to machine.getPC()
    	currentLocation = machine.getPC();
//SET THE COLOR OF THE TWO currentLocation FIELDS TO YELLOW
    	stringFields[currentLocation].setBackground(Color.YELLOW);
    	binaryFields[currentLocation].setBackground(Color.YELLOW);
    }
}
  
}