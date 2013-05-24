package pippin;

public class _000001 extends Instruction
//LOD
{

	public _000001(Processor p, Memory m)
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
    int operand = memory.getData(address);
    cpu.setAccumulator (operand);
    cpu.incrementCounter();
	}

	@Override
	public String toString()
	{
		return "LOD";
	}

}
