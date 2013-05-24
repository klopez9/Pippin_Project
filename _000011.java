package pippin;

public class _000011 extends Instruction
//STO
{

	public _000011(Processor p, Memory m)
	{
		super(p, m);
	}

	@Override
	public void execute(int arg, boolean indirect) throws DataAccessException
	{
    int address = arg;
    if(indirect){
        address = memory.getData(arg);
    }
    memory.setData(address, cpu.getAccumulator());
		cpu.incrementCounter();
	}

	@Override
	public String toString()
	{
		return "STO";
	}

}
