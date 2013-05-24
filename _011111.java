package pippin;

public class _011111 extends Instruction
//HALT
{

  public _011111(Processor cpu, Memory memory) {
    super(cpu, memory);
  }	
	
  @Override
  public void execute(int argument, boolean indirect)
          throws DataAccessException {
  	// TODO
  }
  
  @Override
  public boolean requiresArgument() {
    return false;
  }

	@Override
	public String toString()
	{
		return "HALT";
	}
	
}
