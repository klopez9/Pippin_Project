package pippin;

public class _011011 extends Instruction
//JMPZ
{
  public _011011(Processor p, Memory m)
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
    if (cpu.getAccumulator() == 0) {
      cpu.setProgramCounter(address);
    } else {
    	cpu.incrementCounter();
    }
	}
	
	@Override
  public String toString() {
      return "JMPZ";
  }
}
