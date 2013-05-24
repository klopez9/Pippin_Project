package pippin;

import java.util.Observable;
public class Processor extends Observable {
	private int accumulator;
	private int programCounter;

    public void incrementCounter() {
        programCounter++;
    }
		public int getAccumulator()
		{
			return accumulator;
		}
		public void setAccumulator(int accumulator)
		{
			this.accumulator = accumulator;
			setChanged();
      notifyObservers();
		}
		public int getProgramCounter()
		{
			return programCounter;
		}
		public void setProgramCounter(int programCounter)
		{
			this.programCounter = programCounter;
			setChanged();
      notifyObservers();
		}
}
