package pippin;

public class _001011 extends Instruction
//DIVI
{

    public _001011(Processor cpu, Memory memory) {
        super(cpu, memory);
    }

    @Override
    public void execute(int argument, boolean indirect)
            throws DataAccessException {
        cpu.setAccumulator (cpu.getAccumulator() / argument);
        cpu.incrementCounter();
    }
    
    @Override    
    public boolean isImmediate() {
      return true;
    }

		@Override
		public String toString()
		{
			return "DIVI";
		}

}