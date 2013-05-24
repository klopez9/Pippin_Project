package pippin;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Loader {
	private Machine machine;

	public Loader(Machine machine) {
		this.machine = machine;
	}

	public void load(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		try {
			boolean incode = true;
			int count = 0;

			while(dis.available() > 0) {
				long ln = dis.readLong();
				// HERE IS YOUR Loader CHALLENGE
				// IF ln is -1, YOU SWITCH incode to false AND GO TO THE NEXT ITERATION
				// ELSE IF YOU ARE IN CODE, JUST USE machine.setInstruction WITH count AND ln
				// MAKE SURE YOU INCREMENT THE count
				// THIS HAS TO BE IN A try/catch
				if (ln == -1) {
					incode = false;
				} else if (incode) {
					machine.setInstruction(count, ln);
					count++;
				} else {
					machine.setData(ln);
					count++;
				}

			}
			// HERE IS THE HANDLER:
		} catch (CodeAccessException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(),
					"Code Failure", JOptionPane.WARNING_MESSAGE);
			// ELSE YOU ARE IN data AND SIMPLY USE THE NEW METHOD machine.setData(ln)
			// THIS HAS TO BE IN A try/catch
			// HERE IS THE HANDLER:
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(),
					"Data Failure", JOptionPane.WARNING_MESSAGE);
		} finally {
			dis.close();
		}
	}
}