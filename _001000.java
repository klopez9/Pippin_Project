package pippin;

public class _001000 extends Instruction
//ADDI
{

    public _001000(Processor cpu, Memory memory) {
        super(cpu, memory);
    }

    @Override
    public void execute(int argument, boolean indirect)
            throws DataAccessException {
        cpu.setAccumulator (cpu.getAccumulator() + argument);
        cpu.incrementCounter();
    }
    
    @Override    
    public boolean isImmediate() {
      return true;
    }

		@Override
		public String toString()
		{
			return "ADDI";
		}

}
