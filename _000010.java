package pippin;

public class _000010 extends Instruction
//LODI
{

	public _000010(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
    cpu.setAccumulator (arg);
    cpu.incrementCounter();
	}
	
	@Override
	public boolean isImmediate() {
		return true;
	}

	@Override
	public String toString()
	{
		return "LODI";
	}

}
