package pippin;

public class _000100 extends Instruction
//ADD
{
    public _000100(Processor cpu, Memory memory) {
        super(cpu, memory);
    }

    @Override
    public void execute(int argument, boolean indirect)
            throws DataAccessException {
        int address = argument;
        if(indirect){
            address = memory.getData(argument);
        }
        int operand = memory.getData(address);
        cpu.setAccumulator (cpu.getAccumulator() + operand);
        cpu.incrementCounter();
    }

		@Override
		public String toString()
		{
			return "ADD";
		}

}