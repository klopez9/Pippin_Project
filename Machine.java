package pippin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Machine extends Observable {
	private static final int TICK = 500; // timer tick = 1/2 second
	public final Instruction[] INSTRUCTION_SET = new Instruction[32]; 
	private Processor proc = new Processor();
	private Memory memory = new Memory();
	private boolean running = false;
	private boolean autoStepOn = false;
	private File currentlyExecutingFile = null;
	private States state;
	private CodeViewPanel codeViewPanel;
	private DataViewPanel dataViewPanel;
	private ControlPanel controlPanel;
	private ProcessorViewPanel processorPanel;
	private MenuBarBuilder menuBuilder;
	private JFrame frame;

	private String sourceDir;
	private String executableDir;
	private String eclipseDir;
	private Properties properties = null; 
	private Loader loader;

	public Machine() {
		for(int i = 0; i < INSTRUCTION_SET.length; i++) {
			String str = Integer.toBinaryString(i);
			str = "000000" + str;
			str = "pippin._" + str.substring(str.length() - 6);
			try {
				@SuppressWarnings("unchecked")
				Class<Instruction> cls = (Class<Instruction>) Class.forName(str);
				Constructor<Instruction> cons = cls.getConstructor(Processor.class, Memory.class);
				Instruction instr = (Instruction) cons.newInstance(proc, memory);
				INSTRUCTION_SET[i] = instr;
			} catch (ClassNotFoundException e) {
				// do nothing--we are expecting this for each i that is not
				// an opcode!
				// VERSION FOR Java 1.7
			} catch (NoSuchMethodException e) { 
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
		}
		loader = new Loader(this);
		// CODE TO DISCOVER THE ECLIPSE DEFAULT DIRECTORY:
		File temp = new File("propertyfile.txt");

		System.out.println("check");

		if(!temp.exists()) {
			PrintWriter out;
			try {
				out = new PrintWriter(temp);
				out.close();
				eclipseDir = temp.getAbsolutePath();
				temp.delete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			eclipseDir = temp.getAbsolutePath();
		}
		// change to forward slashes
		eclipseDir = eclipseDir.replace('\\','/');
		int lastSlash = eclipseDir.lastIndexOf('/');
		eclipseDir = eclipseDir.substring(0, lastSlash + 1);
		System.out.println(eclipseDir);			
		try { // load properties file "propertyfile.txt", if it exists
			properties = new Properties();
			properties.load(new FileInputStream("propertyfile.txt"));
			sourceDir = properties.getProperty("SourceDirectory");
			executableDir = properties.getProperty("ExecutableDirectory");
			// CLEAN UP ANY ERRORS IN WHAT IS STORED:
			if (sourceDir == null || sourceDir.length() == 0 
					|| !new File(sourceDir).exists()) {
				sourceDir = eclipseDir;
			}
			if (executableDir == null || executableDir.length() == 0 
					|| !new File(executableDir).exists()) {
				executableDir = eclipseDir;
			}
		} catch (Exception e) {
			// PROPERTIES FILE DID NOT EXIST
			sourceDir = eclipseDir;
			executableDir = eclipseDir;
		}
		createAndShowGUI();
	}

	/**
	 * Package visible constructor for testing only
	 * @param string parameter to distinguish the test constructor
	 * from the working constructor
	 */
	Machine(String string) {
	}

	/**
	 * Clears the machine completely:
	 * 
	 * Clear the array in code by calling clearCode
	 * Clear the array in data by calling clearData
	 * Set the ip and acc of the processor to 0
	 * Set the state to NOTHING_LOADED
	 * Call the "enter" method on this state
	 * Call setChanged and notifyObservers("Clear")
	 */
	public void clearAll() {
		memory.clearCode();
		memory.clearData();

		proc.setAccumulator(0);
		proc.setProgramCounter(0);

		state = States.NOTHING_LOADED;
		state.enter();
		setChanged();
		notifyObservers("Clear");
	}

	/**
	 * Method that sets up the whole GUI and locates the individual
	 * components into place. Also sets up the Menu bar. Starts a 
	 * swing timer ticking.
	 */
	private void createAndShowGUI() {
		codeViewPanel = new CodeViewPanel(this);
		dataViewPanel = new DataViewPanel(this);
		controlPanel = new ControlPanel(this);
		processorPanel = new ProcessorViewPanel(this);
		menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Pippin Simulator");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		frame.setSize(1200,600);

		frame.add(codeViewPanel.createCodeDisplay(),BorderLayout.CENTER);
		frame.add(dataViewPanel.createDataDisplay(),BorderLayout.LINE_END);
		frame.add(controlPanel.createControlDisplay(),BorderLayout.PAGE_END);
		frame.add(processorPanel.createProcessorDisplay(),BorderLayout.PAGE_START);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);

		bar.add(menuBuilder.createMenu());
		bar.add(menuBuilder.createMenu2());
		bar.add(menuBuilder.createMenu3());

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new ExitAdapter());
		// PAY ATTENTION TO THE NEXT 4 LINES. THIS IS HOW WE CHANGE STATE AND
		// NOTIFY ALL THE OBSERVERS
		state = States.NOTHING_LOADED;
		state.enter();
		setChanged();
		notifyObservers();
		javax.swing.Timer timer = new javax.swing.Timer(TICK, new TimerListener());
		timer.start();
		frame.setVisible(true);
	}

	public void exit() { // method executed when user exits the program
		int decision = JOptionPane.showConfirmDialog(
				frame, 
				"Do you really wish to exit?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION);
		if (decision == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public int getAcc() {
		return proc.getAccumulator();
	}

	public int getArgument(int location) throws CodeAccessException {
		return memory.getCode(location).getArg();
	}

	public boolean getClearActive() {
		return state.getClearActive();
	}

	public int getData(int location) throws DataAccessException {
		return memory.getData(location);
	}

	public String getDataBinary(int location) throws DataAccessException {
		int value = memory.getData(location);
		String str = "00000000000000000000000000000000" + 
				Integer.toBinaryString(value);
		str = str.substring(str.length()-32);
		return str;
	}

	// NOTE WE GOT RID OF getInstruction	
	public CodeLine getLine(int location) throws CodeAccessException {
		CodeLine line = memory.getCode(location);
		return line;
	}

	public String getInstructionBinary(int location) throws CodeAccessException {
		long binary = memory.getCode(location).getBinary();
		String str = "00000000000000000000000000000000" +
				"00000000000000000000000000000000" +
				Long.toBinaryString(binary);
		return str.substring(str.length()-64);
	}

	public int getPC() {
		return proc.getProgramCounter();
	}

	public boolean getLoadFileActive() {
		return state.getLoadFileActive();
	}

	public boolean getReloadActive() {
		return state.getReloadActive();
	}

	public boolean getRunSuspendActive() {
		return state.getRunSuspendActive();
	}		

	public boolean getRunningActive() {
		return state.getRunningActive();
	}		

	public boolean getStepActive() {
		return state.getStepActive();
	}

	public boolean getAssembleFileActive() {
		return state.getAssembleFileActive();
	}

	/**
	 * Getter method for autoStepOn
	 * @return the value of the autoStepOn flag
	 */
	public boolean isAutoStepOn() {
		return autoStepOn;
	}

	/**
	 * Getter method for running. Package visible for
	 * JUnit testing only.
	 * @return the value of the running flag
	 */
	boolean isRunning() {
		return running;
	}

	// YOU HAVE TO WRITE THE Loader CLASS BUT I HAVE GIVEN HINTS
	// THIS METHOD CALLS Loader
	public void loadFile() {
		// SAMPLE USE OF JFileChooser
		JFileChooser chooser = new JFileChooser(executableDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Pippin Executable Files", "pipe");
		chooser.setFileFilter(filter);
		// CODE TO LOAD DESIRED FILE
		int openOK = chooser.showOpenDialog(null);
		if(openOK == JFileChooser.APPROVE_OPTION) {
			currentlyExecutingFile = chooser.getSelectedFile();
		}
		if(currentlyExecutingFile .exists()) {
			// CODE TO REMEMBER WHICH DIRECTORY HAS THE pipe FILES
			// IF THE USER CHANGES TO A NEW DIRECTORY
			executableDir = currentlyExecutingFile .getAbsolutePath();
			executableDir = executableDir.replace('\\','/');
			int lastSlash = executableDir.lastIndexOf('/');
			executableDir = executableDir.substring(0, lastSlash + 1);
			try { 
				properties.setProperty("Source directory", sourceDir);
				properties.setProperty("Executable directory", executableDir);
				properties.store(new FileOutputStream("propertyfile.txt"), 
						"File locations");
			} catch (Exception e) {
				System.out.println("Error writing properties file");
			}			
		}
		finalLoad_ReloadStep();
	}

	private void finalLoad_ReloadStep() {
		try {
			loader.load(currentlyExecutingFile);
			setRunning(true);
			// FOUR MORE STEPS:
			// SET THE STATE TO PROGRAM_LOADED_NOT_AUTOSTEPPING
			// CALL "enter" ON THE STATE
			// SET CHANGED
			// NOTIFY OBSERVERS WITH THE ARGUMENT "New Program"
			state = States.PROGRAM_LOADED_NOT_AUTOSTEPPING;
			state.enter();
			setChanged();
			notifyObservers("New Program");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					frame, 
					"The file being selected has problems.\n" +
							"Cannot load the program",
							"Warning",
							JOptionPane.OK_OPTION);
		}		
	}

	/**
	 * Reload a program:
	 * 
	 * First apply clearAll and call finalLoad_ReloadStep
	 * to reload the program again from the field
	 * "currentlyExecutingFile," which stores the current file 
	 * and resets the state of the machine.
	 */
	public void reload() {
		clearAll();
		finalLoad_ReloadStep();
	}

	public void setData(int location, int value) throws DataAccessException {
		memory.setData(location, value);
	}

	public void setData(long binary) throws DataAccessException {
		int addr = (int)(binary >> 32);
		int val = (int)(-1 & binary);
		memory.setData(addr, val);
	}

	// THIS MAY BE A CHANGE FROM THE PREVIOUS LAB
	public void setInstruction(int location, long binary) 
			throws CodeAccessException {
		memory.setCode(location, new CodeLine(this, binary));
	}

	public void setRunning(boolean b) {
		running = b;
		if(running) {
			// DO THE FOUR STEPS OF A STATE CHANGE AS ABOVE BUT TO THE STATE
			// PROGRAM_LOADED_NOT_AUTOSTEPPING, NOTIFY OBSERVERS WITH NO ARGUMENT	
			state = States.PROGRAM_LOADED_NOT_AUTOSTEPPING;
			state.enter();
			setChanged();
			notifyObservers();
		} else {
			autoStepOn = false;
			// STATE CHANGE AS ABOVE BUT TO PROGRAM_HALTED
			state = States.PROGRAM_HALTED;
			state.enter();
			setChanged();
			notifyObservers();
		}
	}

	public void setAutoStepOn(boolean b) {
		autoStepOn = b;
		if(autoStepOn) {
			// STATE CHANGE AS ABOVE BUT TO AUTO_STEPPING
			state = States.AUTO_STEPPING;
			state.enter();
			setChanged();
			notifyObservers();
		} else {
			// STATE CHANGE AS ABOVE BUT TO PROGRAM_LOADED_NOT_AUTOSTEPPING
			state = States.PROGRAM_LOADED_NOT_AUTOSTEPPING;
			state.enter();
			setChanged();
			notifyObservers();
		}
	}		


	/**
	 * Single step of the simulation, which executes the instruction
	 * at the current instruction pointer value. If there is an
	 * error in the program the instruction pointer may be the index
	 * of a null value.
	 * If there is an exception (CodeAccessException, DataAccessException
	 * or NullPointerException)
	 */
	public void step() {
		CodeLine line = null;
		Instruction instr;
		try {
			// USE getLine TO GET THE CodeLine AT THE ProgramCounter VALUE 
			// GET THE instr FROM THE THE line
			// ALSO GET THE arg AND THE indirect VALUES			
			// CALL execute ON instr
			line = memory.getCode(proc.getProgramCounter());
			instr = line.getInst();
			if(instr.toString().equals("HALT")) {
				setRunning(false);
			} else {
				instr.execute(line.getArg(), line.isIndirect());
			}
			setChanged();
			notifyObservers("Step");
		} catch (CodeAccessException e) {
			//PUT IN A JOptionPane SHOWING THE ERROR--LOOK AT THE JOptionPane IN Loader
			//SHOWN IN Lab 10--INCLUDE line.toString() IF line != null
			JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
					"\n" + line.toString()) : (e.getMessage()), "Code Access Error",
					JOptionPane.WARNING_MESSAGE);
			setRunning(false);
		} catch (DataAccessException e) {
			//PUT IN A JOptionPane SHOWING THE ERROR AS ABOVE
			JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
					"\n" + line.toString()) : (e.getMessage()), "Data Access Error",
					JOptionPane.WARNING_MESSAGE);
			setRunning(false);
		} catch (NullPointerException e) {
			//PUT IN A JOptionPane SHOWING THE ERROR
			JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
					"\n" + line.toString()) : (e.getMessage()), "Null Pointer Error",
					JOptionPane.WARNING_MESSAGE);
			setRunning(false);
		} catch (ArithmeticException e) {
			e.printStackTrace();
			setRunning(false);
			JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
					"\n" + line.toString()) : (e.getMessage()), "Divide By Zero Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * AssembleFile method reads a source "pips" file and saves the
	 * file with the extension "pipe" by using the assembleFile method in
	 * Assembler 
	 */
	public void assembleFile() {
		File source = null;
		File outputExe = null;
		JFileChooser chooser = new JFileChooser(sourceDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Pippin Source Files", "pips");
		chooser.setFileFilter(filter);
		// CODE TO LOAD DESIRED FILE
		int openOK = chooser.showOpenDialog(null);
		if(openOK == JFileChooser.APPROVE_OPTION) {
			source = chooser.getSelectedFile();
		}
		if(source != null && source.exists()) {
			// CODE TO REMEMBER WHICH DIRECTORY HAS THE pipe FILES
			// WHICH WE WILL ALLOW TO BE DIFFERENT
			sourceDir = source.getAbsolutePath();
			sourceDir = sourceDir.replace('\\','/');
			int lastDot = sourceDir.lastIndexOf('.');
			String outName = sourceDir.substring(0, lastDot + 1) + "pipe";            
			int lastSlash = sourceDir.lastIndexOf('/');
			sourceDir = sourceDir.substring(0, lastSlash + 1);
			outName = outName.substring(lastSlash+1);  ///this is new
			filter = new FileNameExtensionFilter(
					"Pippin Executable Files", "pipe");
			if(executableDir.equals(eclipseDir)) {
				chooser = new JFileChooser(sourceDir);
			} else {
				System.out.println(executableDir);
				System.out.println(outName);
				chooser = new JFileChooser(executableDir);
			}
			chooser.setFileFilter(filter);
			chooser.setSelectedFile(new File(outName));
			int saveOK = chooser.showSaveDialog(null);
			if(saveOK == JFileChooser.APPROVE_OPTION) {
				outputExe = chooser.getSelectedFile();
			}
			if(outputExe != null) {
				executableDir = outputExe.getAbsolutePath();
				executableDir = executableDir.replace('\\','/');
				lastSlash = executableDir.lastIndexOf('/');
				executableDir = executableDir.substring(0, lastSlash + 1);
				try { 
					properties.setProperty("SourceDirectory", sourceDir);  // this is new
					properties.setProperty("ExecutableDirectory", executableDir);  // this is new
					properties.store(new FileOutputStream("propertyfile.txt"), 
							"File locations");
				} catch (Exception e) {
					System.out.println("Error writing properties file");
				}
				Assembler.assembleFile(source, outputExe);
				JOptionPane.showMessageDialog(
						frame, 
						"The source was assembled to an executable",
						"Success",
						JOptionPane.INFORMATION_MESSAGE);     // This is a big improvement
			} else {// outputExe Still null
				JOptionPane.showMessageDialog(
						frame, 
						"The output file has problems.\n" +
								"Cannot assemble the program",
								"Warning",
								JOptionPane.OK_OPTION);
			}
		} else {// outputExe does not exist
			JOptionPane.showMessageDialog(
					frame, 
					"The source file has problems.\n" +
							"Cannot assemble the program",
							"Warning",
							JOptionPane.OK_OPTION);                
		}
	}

	public void execute() {
		while(running) {
			CodeLine line = null;
			Instruction instr;
			try {
				line = memory.getCode(proc.getProgramCounter());
				instr = line.getInst();
				if(instr.toString().equals("HALT")) {
					setRunning(false);
				} else {
					instr.execute(line.getArg(), line.isIndirect());
				}
			} catch (CodeAccessException e) {
				e.printStackTrace();
				setRunning(false);
				JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
						"\n" + line.toString()) : (e.getMessage()), "Code Access Error",
						JOptionPane.WARNING_MESSAGE);
			} catch (DataAccessException e) {
				e.printStackTrace();
				setRunning(false);
				JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
						"\n" + line.toString()) : (e.getMessage()), "Data Access Error",
						JOptionPane.WARNING_MESSAGE);
			} catch (NullPointerException e) {
				e.printStackTrace();
				setRunning(false);
				JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
						"\n" + line.toString()) : (e.getMessage()), "Null Pointer Error",
						JOptionPane.WARNING_MESSAGE);
			} catch (ArithmeticException e) {
				e.printStackTrace();
				setRunning(false);
				JOptionPane.showMessageDialog(null, (line != null) ? (e.getMessage() +
						"\n" + line.toString()) : (e.getMessage()), "Divide By Zero Error",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Main method that drives the whole simulator
	 * @param args command line arguments are not used
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Machine(); 
			}
		});
	}

	private class ExitAdapter extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			exit();
		}
	}

	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(autoStepOn) {
				step();
			}
		}
	}

}
