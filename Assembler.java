/* READ THE ASSIGNMENT CAREFULLY!! The following skeleton
 * was created simply by reading the assignment. Most, if
 * not all the information that you need is there.
 * 
 * Take an input source
 * file. Read it line by line. For each line, make sure
 * the line has a legal format. If it does, convert it
 * to a long, otherwise throw an exception.
 */
package pippin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Assembler
{
	public static Map<String, Integer> opcodes = new HashMap<String, Integer>();
	public static Map<String, Boolean> requiresArgument = new HashMap<String, Boolean>();
	static {
		opcodes.put("NOP", 0x00);
		requiresArgument.put("NOP", false);
		opcodes.put("LOD", 0x01);
		requiresArgument.put("LOD", true);
		opcodes.put("LODI", 0x02);
		requiresArgument.put("LODI", true);
		opcodes.put("STO", 0x03);
		requiresArgument.put("STO", true);
		opcodes.put("ADD", 0x04);
		requiresArgument.put("ADD", true);
		opcodes.put("SUB", 0x05);
		requiresArgument.put("SUB", true);
		opcodes.put("MUL", 0x06);
		requiresArgument.put("MUL", true);
		opcodes.put("DIV", 0x07);
		requiresArgument.put("DIV", true);
		opcodes.put("ADDI", 0x08);
		requiresArgument.put("ADDI", true);
		opcodes.put("SUBI", 0x09);
		requiresArgument.put("SUBI", true);
		opcodes.put("MULI", 0x0A);
		requiresArgument.put("MULI", true);
		opcodes.put("DIVI", 0x0B);
		requiresArgument.put("DIVI", true);
		opcodes.put("AND", 0x10);
		requiresArgument.put("AND", true);
		opcodes.put("ANDI", 0x11);
		requiresArgument.put("ANDI", true);
		opcodes.put("NOT", 0x12);
		requiresArgument.put("NOT", false);
		opcodes.put("CMPZ", 0x13);
		requiresArgument.put("CMPZ", true);
		opcodes.put("CMPL", 0x14);
		requiresArgument.put("CMPL", true);
		opcodes.put("JUMP", 0x1A);
		requiresArgument.put("JUMP", true);
		opcodes.put("JMPZ", 0x1B);
		requiresArgument.put("JMPZ", true);
		opcodes.put("HALT", 0x1F);
		requiresArgument.put("HALT", false);
	}

	public static void main(String args[]) {
		File inFile = new File("QuickSort2.pips");
		File outFile = new File("out.txt");
		assembleFile(inFile, outFile);
		try
		{
			FileInputStream fis = new FileInputStream(outFile);
			DataInputStream dis = new DataInputStream(fis);
			while(dis.available() > 0) {
				long ln = dis.readLong();
				String str =  "00000000000000000000000000000000" + 
						"00000000000000000000000000000000" + 
						Long.toBinaryString(ln);
				str = str.substring(str.length()-64);				
				System.out.println(str);
			}
			dis.close();

		}
		catch (IOException e)
		{
			System.out.println("IOException : " + e);
		}

	}

	public static void assembleFile(File inFile, File outFile)
	{
		/* Open the file into a Scanner
		 * http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Scanner.html
		 * Look at the API for scanner for details
		 */
		Scanner scan = null;
		try
		{
			scan = new Scanner(inFile);
		} catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		boolean goodProgram = true;
		boolean blankLineHit = false; //keep track of when we hit a blank line
		ArrayList<Long> codeLines = new ArrayList<Long>(); 
		ArrayList<Long> dataLines = new ArrayList<Long>(); 
		int lineCounter = 0;
		while(/*the Scanner has next line and goodProgram*/
				scan.hasNextLine() && goodProgram)
		{
			/*get the next line from the Scanner and convert to uppercase*/
			String line = scan.nextLine().toUpperCase();
			//increment lineCounter
			lineCounter++;
			//to check if line is blank or not blank use: 
			// line.trim().length() == 0 or line.trim().length() > 0
			if (line.trim().length() > 0 && blankLineHit) {
				//if line blankLineHit is true but line is not blank change goodProgram
				//to false print a message saying there are blank lines in the
				//program around line number lineCounter
				goodProgram = false;
				System.out.println("Error: There are blank lines in the program " +
						"around line number " + lineCounter);
				JOptionPane.showMessageDialog(null, "Error: There are blank lines in the program " +
						"around line number " + lineCounter, "Data Access Error",
						JOptionPane.WARNING_MESSAGE);
				//else if !blankLineHit but line is blank, change blankLineHit to true
			} else if (line.trim().length() == 0 && !blankLineHit) {
				blankLineHit = true;
				//else if !blankLineHit and line is not blank process it as follows
				//(in the case that the blankLineHit is true and the line is blank, we
				//simply skip the line and loop around--no code is needed for that)
			} else if (line.trim().length() > 0 && !blankLineHit) {
				long writeMe = 0;	
				/* if line.charAt(0) is ' ' or '\t' change goodProgram 
				to false and print a message saying there is illegal white 
				space at the start of line number lineCounter */
				if (line.charAt(0) == ' ' || line.charAt(0) == '\t') {
					goodProgram = false;
					System.out.println("Error: There is illegal white space at the " +
							"start of line number " + lineCounter);
					JOptionPane.showMessageDialog(null, "Error: There is illegal white space at the " +
							"start of line number " + lineCounter, "Data Access Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					//split the line by any white space:
					String[] parts = line.split("\\s+");
					/* if parts.length > 2 change goodProgram to false and 
					print a message saying there is an illegal instruction or
					memory assignment on line number lineCounter */
					if (parts.length > 2) {
						goodProgram = false;
						System.out.println("Error: There is an illegal instruction or " +
								"memory assignment on line number " + lineCounter);
						JOptionPane.showMessageDialog(null, "Error: There is an illegal instruction or " +
								"memory assignment on line number " + lineCounter, "Data Access Error",
								JOptionPane.WARNING_MESSAGE);
					}	else {
						//check that the first half of parts[] is an opcode
						if (opcodes.containsKey(parts[0])) {
							//check if the opcode requires an argument--that is, check the
							//boolean value for the parts[0] key in the requiresArgument Map
							if (requiresArgument.get(parts[0]) && parts.length == 2) {
								try {
									boolean indirect = false;
									//check that the second half of parts has an 'N'
									if (parts[1].charAt(0) == 'N') {
										indirect = true;
										//use substring to remove N
										parts[1] = parts[1].substring(1);
									}
									//convert parts[1] to an int
									int y = Integer.parseInt(parts[1], 16);
									//This line is format 1 if !(indirect)
									//and format 2 if indirect
									int x = opcodes.get(parts[0]);
									writeMe = ((long)x << 32) + y;
									if (indirect) {
										writeMe *= -1;
									}
									codeLines.add(writeMe);
								} catch(NumberFormatException e) {
									//report error and set goodProgram to false
									goodProgram = false;
									System.out.println("Error: A number is not in hexidecimal" +
											lineCounter);
									JOptionPane.showMessageDialog(null, "Error: A number is not in hexidecimal" +
											lineCounter, "Data Access Error",
											JOptionPane.WARNING_MESSAGE);
								}
							} else if (!requiresArgument.get(parts[0]) 
									&& parts.length == 1) {
								int x = opcodes.get(parts[0]);
								writeMe = ((long)x << 32);
								/* IMPORTANT x<<32+y MEANS x<<(32+y)
								 * SO YOU NEED (x<<32)+y */
								codeLines.add(writeMe);
							} else {
								/* change goodProgram and print a message
							saying there is an illegal instruction (if you
							want to be more specific you can say whether
							it is because an argument should or should not
							be given) */
								goodProgram = false;
								System.out.println("Error: There is an illegal instruction at line "
										+ lineCounter);
								JOptionPane.showMessageDialog(null, "Error: There is an illegal instruction at line "
										+ lineCounter, "Data Access Error",
										JOptionPane.WARNING_MESSAGE);
							}
						}	else if (parts.length == 2) {
							//(parts[0] is not a key)
							/* then use try catch to check parts[0] and parts[1]
							are in hexadecimal integer form
							if they are, create the long
							writeMe = (long)parts[0]<<32 + parts[1] */
							try {
								int x = Integer.parseInt(parts[0], 16);
								int y = Integer.parseInt(parts[1], 16);
								writeMe = ((long)x << 32) + y;
							} catch(NumberFormatException e) {
								goodProgram = false;
								System.out.println("Error: There is an illegal instruction at line "
										+ lineCounter);
								JOptionPane.showMessageDialog(null, "Error: There is an illegal instruction at line "
										+ lineCounter, "Data Access Error",
										JOptionPane.WARNING_MESSAGE);
							}
							dataLines.add(writeMe);
						} else {
							goodProgram = false;
							//write an error message
							System.out.println("Error: There is an illegal instruction at line "
									+ lineCounter);
							JOptionPane.showMessageDialog(null, "Error: There is an illegal instruction at line "
									+ lineCounter, "Data Access Error",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		}

		//If the loop ends with goodProgram being true
		if (goodProgram == true) {
			try{
				FileOutputStream fos = new FileOutputStream(outFile);
				DataOutputStream dos = new DataOutputStream(fos);
				for(long num : codeLines) {
					dos.writeLong(num);
				}
				dos.writeLong(-1L);
				for(long num : dataLines) {
					dos.writeLong(num);
				}
				dos.close();
			} catch (IOException e){
				System.out.println("IOException : " + e);
				JOptionPane.showMessageDialog(null, "IOException : " + e,
						"Data Access Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
