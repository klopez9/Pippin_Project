package pippin;

import java.util.Arrays;

public class Memory {

	public static final int DATA_SIZE = 256;
	private int[] data = new int[DATA_SIZE];
	public static final int CODE_SIZE = 256;
	private CodeLine[] code = new CodeLine[CODE_SIZE];

	public void setData(int index, int value)throws DataAccessException {
		if(index < 0 || index >= data.length) {
			throw new DataAccessException("ERROR: Cannot access data location " + index);
		}
		data[index] = value;
	}

	public int getData(int index) throws DataAccessException {
		if(index < 0 || index >= data.length) {
			throw new DataAccessException("ERROR: Cannot access data location " + index);
		}
		return data[index];
	}

	// Note package private -- just for JUnit test
	int[] getData() {
		return data;
	}

	public void setCode(int index, CodeLine value)throws CodeAccessException {
		if(index < 0 || index >= CODE_SIZE) {
			throw new CodeAccessException("ERROR: Cannot access code location " + index);
		}
		code[index] = value;
	}

	public CodeLine getCode(int index) throws CodeAccessException {
		if(index < 0 || index >= CODE_SIZE) {
			throw new CodeAccessException("ERROR: Cannot access code location " + index);
		}
		return code[index];
	}

	public void clearCode() {
		Arrays.fill(code, null);    
	}
	public void clearData() {
		Arrays.fill(data, 0);        
	}

}