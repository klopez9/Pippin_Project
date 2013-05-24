package pippin;

public class _010010 extends Instruction
//NOT
{

	public _010010(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
		if (cpu.getAccumulator() == 0) {
			cpu.setAccumulator(1);
		} else {
			cpu.setAccumulator(0);
		}
		cpu.incrementCounter();
	}
	
	@Override
	public boolean requiresArgument() {
		return false;
	}
	
	@Override
	public String toString()
	{
		return "NOT";
	}

}
