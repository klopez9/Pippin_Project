package pippin;

public class _011010 extends Instruction
//JUMP
{

	public _011010(Processor p, Memory m)
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
    cpu.setProgramCounter(address);
	}

	@Override
	public String toString()
	{
		return "JUMP";
	}

}
