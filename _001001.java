package pippin;

public class _001001 extends Instruction
//SUBI
{

    public _001001(Processor cpu, Memory memory) {
        super(cpu, memory);
    }

    @Override
    public void execute(int argument, boolean indirect)
            throws DataAccessException {
        cpu.setAccumulator (cpu.getAccumulator() - argument);
        cpu.incrementCounter();
    }
    
    @Override    
    public boolean isImmediate() {
      return true;
    }

		@Override
		public String toString()
		{
			return "SUBI";
		}

}