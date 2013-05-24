package pippin;

public class CodeLine {
	
  private Instruction inst;
  private int arg;
  private long binary;
  private boolean indirect;
  
  /**
	 * @param inst
	 * @param arg
	 * @param binary
	 * @param indirect
	 */
	public CodeLine(Instruction inst, int arg, long binary, boolean indirect)
	{
		super();
		this.inst = inst;
		this.arg = arg;
		this.binary = binary;
		this.indirect = indirect;
	}
  
  public CodeLine(Machine m, long ln) {
  	// this is part of the final project: reconstruct
    // instr, arg and indirect from binary, which is the encoding of
    // the instruction done by the Assembler
	    binary = ln;
	    indirect = false;
	    if (ln < 0) {
	        indirect = true;
	        ln = -ln;
	    }
	    int high = (int)(ln >> 32);
	    arg = (int)(-1 & ln); // USE "bitwise and" WITH -1 (32 1's as a 2-s complement int)
	    inst = m.INSTRUCTION_SET[high];
	}

   
  @Override
  public String toString() {
      return inst + (indirect?" N":" ") + Integer.toString(arg,16);
  }


	public Instruction getInst()
	{
		return inst;
	}


	public int getArg()
	{
		return arg;
	}


	public long getBinary()
	{
		return binary;
	}


	public boolean isIndirect()
	{
		return indirect;
	}
}
