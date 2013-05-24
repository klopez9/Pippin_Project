package pippin;

public class _010000 extends Instruction
//AND
{
	
	public _010000(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
		int address = arg;
		if (cpu.getAccumulator() != 0 && memory.getData(address) != 0) {
			cpu.setAccumulator(1);
		} else {
			cpu.setAccumulator(0);
		}
		cpu.incrementCounter();
	}

	@Override
	public String toString()
	{
		return "AND";
	}

}
