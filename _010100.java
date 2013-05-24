package pippin;

public class _010100 extends Instruction
//CMPL
{

	public _010100(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
		int address = arg;
		if (memory.getData(address) < 0) {
			cpu.setAccumulator(1);
		} else {
			cpu.setAccumulator(0);
		}
		cpu.incrementCounter();
	}

	@Override
	public String toString()
	{
		return "CMPL";
	}

}
