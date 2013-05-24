package pippin;

abstract public class Instruction {
  protected Processor cpu;
  protected Memory memory;

  public Instruction(Processor p, Memory m) {
  	this.cpu = p;
  	this.memory = m;
  }
  
  /**
   * Method to return the name of this instruction, e.g. "NOP" "LODI"
   * @return the name of the instruction
   */
  @Override
  public String toString() {
      return getClass().getSimpleName();
  }
  /**
   * Method to indicate if this is an immediate instruction,
   * e.g. "LODI" "ADDI"
   * @return true if the instruction is immediate otherwise false
   */
  public boolean isImmediate() {
      return false; // ONLY override this method for the classes that
                    // return true
  }
  /**
   * Method to indicate if this is instruction requires an argument, only
   * NOP, NOT and HALT do not have an argument
   * @return true if the instruction is requires an argument otherwise false
   */
  public boolean requiresArgument() {
      return true; // ONLY override this method for the classes that
                   // return false
  }
 
  /**
   * Method to execute this instruction for the given argument. The details
   * are explained in list of instructions for the Pippin computer.
   * NOTE: If the instruction does not use an argument, then the argument
   * is passed as 0
   * @param arg the argument passed to the instruction
   * @param indirect indicated in the addressing mode is indirect
   * @throws DataAccessException if access to the data in Memory throws an
   * exception
   */
  public abstract void execute(int arg, boolean indirect)
          throws DataAccessException;
     
  /**
   * Method that gives 6-bit, binary representation of the opcode for this
   * instruction
   * @return opcode as a 6-bit binary number
   */
  public String get6bitRep() {
      return getClass().getSimpleName().substring(1);
  }
}