package pippin;

public class _000101 extends Instruction
//SUB
{

    public _000101(Processor cpu, Memory memory) {
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
        cpu.setAccumulator (cpu.getAccumulator() - operand);
        cpu.incrementCounter();
    }

		@Override
		public String toString()
		{
			return "SUB";
		}

}