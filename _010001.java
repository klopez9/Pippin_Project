package pippin;

public class _010001 extends Instruction
//ANDI
{

	public _010001(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
		if (cpu.getAccumulator() != 0 && arg != 0) {
			cpu.setAccumulator(1);
		} else {
			cpu.setAccumulator(0);
		}
		cpu.incrementCounter();
	}
	
	@Override
	public boolean isImmediate() {
		return true;
	}

	@Override
	public String toString()
	{
		return "ANDI";
	}

}
