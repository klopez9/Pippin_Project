package pippin;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class InstructionTester {
	Processor cpu = new Processor();
	Memory memory = new Memory();
	int[] dataCopy = new int[Memory.DATA_SIZE];
	int accInit;
	int pcInit;
	
	@Before
	public void setup() {
		for (int i = 0; i < Memory.DATA_SIZE; i++) {
			dataCopy[i] = -5*Memory.DATA_SIZE + 10*i;
			try {
				memory.setData(i, -5*Memory.DATA_SIZE + 10*i);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			// Initially the memory will contain a known spread
			// of different numbers: 
			// -1280, -1270, -1260, ..., 0, 10, 20, ..., 1270 
			// This allows us to check that the instructions do 
			// not corrupt memory unexpectedly.
			// 0 is at index 128
		}
		accInit = 0;
		pcInit = 0;
	}
	
	@Test
	public void testNOPbasics(){
		Instruction instr = new _000000(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertFalse("Argument not required", instr.requiresArgument());
		assertEquals("Name is NOP", "NOP", instr.toString());
	}
	
	@Test
	public void testLODbasics(){
		Instruction instr = new _000001(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is LOD", "LOD", instr.toString());
	}
	
	@Test
	public void testLODIbasics(){
		Instruction instr = new _000010(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is LODI", "LODI", instr.toString());
	}

	@Test
	public void testSTObasics(){
		Instruction instr = new _000011(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is STO", "STO", instr.toString());
	}
	
	@Test
	public void testADDbasics(){
		Instruction instr = new _000100(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is ADD", "ADD", instr.toString());
	}
	
	@Test
	public void testADDIbasics(){
		Instruction instr = new _001000(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is ADDI", "ADDI", instr.toString());
	}
	
	@Test
	public void testSUBbasics(){
		Instruction instr = new _000101(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is SUB", "SUB", instr.toString());
	}
	
	@Test
	public void testSUBIbasics(){
		Instruction instr = new _001001(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is SUBI", "SUBI", instr.toString());
	}
	
	@Test
	public void testMULbasics(){
		Instruction instr = new _000110(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is MUL", "MUL", instr.toString());
	}
	
	@Test
	public void testMULIbasics(){
		Instruction instr = new _001010(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is MULI", "MULI", instr.toString());
	}
	
	@Test
	public void testDIVbasics(){
		Instruction instr = new _000111(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is DIV", "DIV", instr.toString());
	}
	
	@Test
	public void testDIVIbasics(){
		Instruction instr = new _001011(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is DIVI", "DIVI", instr.toString());
	}
	
	@Test
	public void testANDbasics(){
		Instruction instr = new _010000(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is AND", "AND", instr.toString());
	}
	
	@Test
	public void testANDIbasics(){
		Instruction instr = new _010001(cpu, memory);
		//Test intrinsic properties
		assertTrue("Is immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is ANDI", "ANDI", instr.toString());
	}
	
	@Test
	public void testNOTbasics(){
		Instruction instr = new _010010(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertFalse("Argument not required", instr.requiresArgument());
		assertEquals("Name is NOT", "NOT", instr.toString());
	}
	
	@Test
	public void testCMPZbasics(){
		Instruction instr = new _010011(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is CMPZ", "CMPZ", instr.toString());
	}
	
	@Test
	public void testCMPLbasics(){
		Instruction instr = new _010100(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is CMPL", "CMPL", instr.toString());
	}
	
	@Test
	public void testJUMPbasics(){
		Instruction instr = new _011010(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is JUMP", "JUMP", instr.toString());
	}

	@Test
	public void testJMPZbasics(){
		Instruction instr = new _011011(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertTrue("Argument is required", instr.requiresArgument());
		assertEquals("Name is JMPZ", "JMPZ", instr.toString());
	}

	@Test
	public void testHALTbasics(){
		Instruction instr = new _011111(cpu, memory);
		//Test intrinsic properties
		assertFalse("Is not immediate", instr.isImmediate());
		assertFalse("Argument is not required", instr.requiresArgument());
		assertEquals("Name is HALT", "HALT", instr.toString());
	}
	
	@Test
	public void testNOP(){
		Instruction instr = new _000000(cpu, memory);
		//Test that execute impacts the machine appropriately
		try {
			instr.execute(0,false);
		} catch (Exception ex) {
			fail("Exception thrown: " + ex.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
	}

	@Test
	public void testNOPignoresIndirect(){
		Instruction instr = new _000000(cpu, memory);
		//Test that execute impacts the machine appropriately
		try {
			instr.execute(0,true);
		} catch (Exception ex) {
			fail("Exception thrown: " + ex.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testLODindirectNegArg(){
        Instruction instr = new _000001(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testLODindirectBigArg(){
        Instruction instr = new _000001(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testLODdirectNegArg(){
        Instruction instr = new _000001(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testLODdirectBigArg(){
        Instruction instr = new _000001(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// Test whether load is correct with indirect addressing
	public void testLODindirect(){
		Instruction instr = new _000001(cpu, memory);
		cpu.setAccumulator(27);
		int argument = 133; // operand address is -1280+1330=50  
		try {
			// should load -1280+500 into the accumulator
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator modified
        assertEquals("Accumulator changed", -1280+500,
                cpu.getAccumulator());
	}

	@Test
	// Test whether load is correct with direct addressing
	public void testLODdirect(){
		Instruction instr = new _000001(cpu, memory);
		cpu.setAccumulator(27);
		int argument = 12;
		try {
			// should load -1280+120 into the accumulator
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator modified
        assertEquals("Accumulator changed", -1280+120,
                cpu.getAccumulator());
	}

	@Test
	// Test that load immediate ignores the indirect flag and works correctly
	public void testLODIindirect(){
		Instruction instr = new _000010(cpu, memory);
		cpu.setAccumulator(27);
		int argument = -12;
		try {
			instr.execute(argument, true);
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", -12,
                cpu.getAccumulator());
	}

	@Test
	// Test that load immediate works correctly
	public void testLODI(){
		Instruction instr = new _000010(cpu, memory);
		cpu.setAccumulator(27);
		int argument = 256;
		try {
			instr.execute(argument, false);
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 256,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testSTOindirectNegArg(){
        Instruction instr = new _000011(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage()); 
            assertNotNull(ex);
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testSTOindirectBigArg(){
        Instruction instr = new _000011(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testSTOdirectNegArg(){
        Instruction instr = new _000011(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testSTOdirectBigArg(){
        Instruction instr = new _000011(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// Test whether store is correct with indirect addressing
	public void testSTOindirect(){
		Instruction instr = new _000011(cpu, memory);
		int argument = 133; // affected address is -1280+1330=50
		cpu.setAccumulator(567);
		dataCopy[50] = 567;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator unchanged
        assertEquals("Accumulator unchanged", 567,
                cpu.getAccumulator());
	}

	@Test
	// Test whether store is correct with direct addressing
	public void testSTOdirect(){
		Instruction instr = new _000011(cpu, memory);
		int argument = 12;
		cpu.setAccumulator(567);
		dataCopy[12] = 567;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData());
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Test accumulator unchanged
        assertEquals("Accumulator unchanged", 567,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testADDindirectNegArg(){
        Instruction instr = new _000100(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testADDindirectBigArg(){
        Instruction instr = new _000100(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testADDdirectNegArg(){
        Instruction instr = new _000100(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testADDdirectBigArg(){
        Instruction instr = new _000100(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

    @Test 
    // this test checks whether the add is done correctly, when
    // addressing is direct: execute(..., false);
    public void testADDdirect(){
    	Instruction instr = new _000100(cpu, memory); 
        int argument = 12; // we know that memory value is -1280+120
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, false); 
            // should have added -1280+120 to accumulator
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200-1280+120,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the add is done correctly, when
    // addressing is indirect: execute(..., true);
    public void testADDindirect(){
		Instruction instr = new _000100(cpu, memory); 
        int argument = 133; // we know that memory value is -1280+1330=50 
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, true); 
            // should have added -1280+500 to accumulator
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200-1280+500,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testSUBindirectNegArg(){
        Instruction instr = new _000101(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testSUBindirectBigArg(){
        Instruction instr = new _000101(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testSUBdirectNegArg(){
        Instruction instr = new _000101(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testSUBdirectBigArg(){
        Instruction instr = new _000101(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

    @Test 
    // this test checks whether the subtraction is done correctly, when
    // addressing is direct: execute(..., false);
    public void testSUBdirect(){
        Instruction instr = new _000101(cpu, memory); 
        int argument = 12; // we know that memory value is -1280+120
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, false); 
            // should have subtracted -1280+120 from accumulator
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200+1280-120,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the subtraction is done correctly, when
    // addressing is indirect: execute(..., true);
    public void testSUBindirect(){
		Instruction instr = new _000101(cpu, memory); 
        int argument = 133; // we know that memory value is -1280+1330=50 
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, true); 
            // should have subtraction -1280+500 from accumulator
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200+1280-500,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testMULindirectNegArg(){
        Instruction instr = new _000110(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testMULindirectBigArg(){
        Instruction instr = new _000110(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testMULdirectNegArg(){
        Instruction instr = new _000110(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testMULdirectBigArg(){
        Instruction instr = new _000110(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

    @Test 
    // this test checks whether the multiplication is done correctly, when
    // addressing is direct: execute(..., false);
    public void testMULdirect(){
        Instruction instr = new _000110(cpu, memory); 
        int argument = 12; // we know that memory value is -1280+120
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, false); 
            // should have multiplied the accumulator by -1280+120
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200*(-1280+120),
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the multiplication is done correctly, when
    // addressing is indirect: execute(..., true);
    public void testMULindirect(){
		Instruction instr = new _000110(cpu, memory); 
        int argument = 133; // we know that memory value is -1280+1330=50 
    	cpu.setAccumulator(200);
        try { 
            instr.execute(argument, true); 
            // should have multiplied the accumulator by -1280+500
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 200*(-1280+500),
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is a negative index.
    public void testDIVindirectNegArg(){
        Instruction instr = new _000111(cpu, memory); 
        int argument = 12;
        try { // this is how you test that an exception is thrown
        	// the value at index 12 is -1280+120
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the actual operand
	// address is larger than the array size.
    public void testDIVindirectBigArg(){
        Instruction instr = new _000111(cpu, memory); 
        int argument = 160;
        try { // this is how you test that an exception is thrown
        	// will true to get value from index -1280+1600 > 256
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testDIVdirectNegArg(){
        Instruction instr = new _000111(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testDIVdirectBigArg(){
        Instruction instr = new _000111(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

    @Test 
    // this test checks whether the division is done correctly, when
    // addressing is direct: execute(..., false);
    public void testDIVdirect(){
        Instruction instr = new _000111(cpu, memory); 
        int argument = 136; // we know that memory value is -1280+1360=80
    	cpu.setAccumulator(222);
        try { 
            instr.execute(argument, false); 
            // should have divided the accumulator by -1280+1360 = 80
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222/(-1280+1360),
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the division is done correctly, when
    // addressing is indirect: execute(..., true);
    public void testDIVindirect(){
		Instruction instr = new _000111(cpu, memory); 
        int argument = 140; // we know that memory value is -1280+1400=120 
    	cpu.setAccumulator(222);
        try { 
            instr.execute(argument, true); 
            // should have divided the accumulator by -1280+1200 = -80
        } catch (DataAccessException ex) {
            fail("DataAccessException should not occur");           
        } catch (Exception ex) {
            fail("Exception thrown: " + ex.getClass().getSimpleName());
        }
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222/(-1280+1200),
                cpu.getAccumulator());
    }

	@Test
	// this test checks whether the immediate addition is done correctly and
	// that indirect addressing is ignored
	public void testADDIindirectIgnored(){
		Instruction instr = new _001000(cpu, memory);
		int argument = -10;
    	cpu.setAccumulator(222);
		try {
            // should have added -10 to the accumulator 
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 212,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate addition is done correctly
	public void testADDI(){
		Instruction instr = new _001000(cpu, memory);
		int argument = 300;
    	cpu.setAccumulator(222);
		try {
            // should have added 300 to the accumulator 
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 522,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate subtraction is done correctly and
	// that indirect addressing is ignored
	public void testSUBIindirectIgnored(){
		Instruction instr = new _001001(cpu, memory);
		int argument = -10;
    	cpu.setAccumulator(222);
		try {
            // should have subtracted -10 from the accumulator 
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 232,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate addition is done correctly
	public void testSUBI(){
		Instruction instr = new _001001(cpu, memory);
		int argument = 300;
    	cpu.setAccumulator(222);
		try {
            // should have subtracted 300 from the accumulator 
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222-300,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate multiplication is done correctly
	// and that indirect addressing is ignored
	public void testMULIindirectIgnored(){
		Instruction instr = new _001010(cpu, memory);
		int argument = -10;
    	cpu.setAccumulator(222);
		try {
            // should have multiplied the accumulator by -10 
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", -2220,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate multiplication is done correctly
	public void testMULI(){
		Instruction instr = new _001010(cpu, memory);
		int argument = 300;
    	cpu.setAccumulator(222);
		try {
            // should have multiplied the accumulator by 300 
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222*300,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate division is done correctly
	// and that indirect addressing is ignored
	public void testDIVIindirectIgnored(){
		Instruction instr = new _001011(cpu, memory);
		int argument = -22;
    	cpu.setAccumulator(222);
		try {
            // should have divided the accumulator by -10 
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222/-22,
                cpu.getAccumulator());
	}

	@Test
	// this test checks whether the immediate division is done correctly
	public void testDIVI(){
		Instruction instr = new _001011(cpu, memory);
		int argument = 30;
    	cpu.setAccumulator(222);
		try {
            // should have divided the accumulator by 30 
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
        assertArrayEquals(dataCopy, memory.getData()); 
        assertEquals("Program counter was incremented", pcInit + 1,
                cpu.getProgramCounter());
        assertEquals("Accumulator was changed", 222/30,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testANDdirectNegArg(){
        Instruction instr = new _010000(cpu, memory); 
        int argument = -12;
        cpu.setAccumulator(1);
        accInit = 1;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testANDdirectBigArg(){
        Instruction instr = new _010000(cpu, memory); 
        int argument = 256;
        cpu.setAccumulator(1);
        accInit = 1;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// Check that the AND of 2 true values is true and the indirect flag
	// is ignored
	public void testANDtrueTrueIndirect(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 10;
		cpu.setAccumulator(-7);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 true values is true 
	public void testANDtrueTrue(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 10;
		cpu.setAccumulator(-7);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 false values is false and the indirect flag
	// is ignored
	public void testANDfalseFalseIndirect(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 128;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 false values is false
	public void testANDfalseFalse(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 128;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of false and true values is false and the indirect 
	// flag is ignored
	public void testANDfalseTrueIndirect(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 128;
		cpu.setAccumulator(10);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of false and true values is false 
	public void testANDfalseTrue(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 128;
		cpu.setAccumulator(-10);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of true and false values is false and the indirect 
	// flag is ignored
	public void testANDtrueFalseIndirect(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 120;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of true and false values is false 
	public void testANDtrueFalse(){
		Instruction instr = new _010000(cpu, memory);
		int argument = 130;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 true values is true and the indirect flag
	// is ignored
	public void testANDItrueTrueIndirect(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 10;
		cpu.setAccumulator(-7);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 true values is true 
	public void testANDItrueTrue(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 10;
		cpu.setAccumulator(-7);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 false values is false and the indirect flag
	// is ignored
	public void testANDIfalseFalseIndirect(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 0;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of 2 false values is false
	public void testANDIfalseFalse(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 0;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of false and true values is false and the indirect 
	// flag is ignored
	public void testANDIfalseTrueIndirect(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 0;
		cpu.setAccumulator(10);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of false and true values is false 
	public void testANDIfalseTrue(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 0;
		cpu.setAccumulator(-10);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of true and false values is false and the indirect 
	// flag is ignored
	public void testANDItrueFalseIndirect(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 120;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the AND of true and false values is false 
	public void testANDItrueFalse(){
		Instruction instr = new _010001(cpu, memory);
		int argument = 130;
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Verify that true is changed to false, ignoring the indirect flag
	public void testNOTnonZeroIndirect(){
		Instruction instr = new _010010(cpu,memory);
		cpu.setAccumulator(10);
		try {
			// argument should be ignored
			instr.execute(3, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}	

	@Test
	// Verify that true is changed to false
	public void testNOTnonZero(){
		Instruction instr = new _010010(cpu,memory);
		cpu.setAccumulator(10);
		try {
			// argument should be ignored
			instr.execute(2, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}	

	@Test
	// Verify that false is changed to true, ignoring the indirect flag
	public void testNOTzeroIndirect(){
		Instruction instr = new _010010(cpu,memory);
		cpu.setAccumulator(0);
		try {
			// argument should be ignored
			instr.execute(1, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}	

	@Test
	// Verify that false is changed to true
	public void testNOTzero(){
		Instruction instr = new _010010(cpu,memory);
		cpu.setAccumulator(0);
		try {
			// argument should be ignored
			instr.execute(0, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}	

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testCMPZdirectNegArg(){
        Instruction instr = new _010011(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testCMPZdirectBigArg(){
        Instruction instr = new _010011(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// Check that the comparing to a zero value gives true and indirect is
	// ignored
	public void testCMPZzeroIndirect(){
		Instruction instr = new _010011(cpu, memory);
		int argument = 128;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing to a zero value gives true 
	public void testCMPZzeroDirect(){
		Instruction instr = new _010011(cpu, memory);
		int argument = 128;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing to a zero value gives true and indirect is
	// ignored
	public void testCMPZnonzeroIndirect(){
		Instruction instr = new _010011(cpu, memory);
		int argument = 100;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing to a zero value gives true 
	public void testCMPZnonzeroDirect(){
		Instruction instr = new _010011(cpu, memory);
		int argument = 130;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter unchanged", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is a negative index.
    public void testCMPLdirectNegArg(){
        Instruction instr = new _010100(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when direct addressing is used and the operand address 
	// is larger than the array size.
    public void testCMPLdirectBigArg(){
        Instruction instr = new _010100(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, false);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// Check that the comparing less than 0 gives true and indirect is
	// ignored
	public void testCMPLlessIndirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 100;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing less than 0 gives true
	public void testCMPLlessDirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 100;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 1
        assertEquals("Accumulator is 1", 1,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing equal to 0 gives false and indirect is
	// ignored
	public void testCMPLzeroIndirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 128;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing equal to 0 gives false
	public void testCMPLzeroDirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 130;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing to positive gives false and indirect is
	// ignored
	public void testCMPLposIndirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 130;
		try {
			instr.execute(argument, true);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test
	// Check that the comparing to positive gives false
	public void testCMPLposDirect(){
		Instruction instr = new _010100(cpu, memory);
		int argument = 130;
		try {
			instr.execute(argument, false);
		} catch(DataAccessException ex){
			fail("Should not throw DataAccessException");			
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit + 1,
                cpu.getProgramCounter());
        //Accumulator is 0
        assertEquals("Accumulator is 0", 0,
                cpu.getAccumulator());
	}

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the address is a negative index.
    public void testJUMPindirectNegArg(){
        Instruction instr = new _011010(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the address 
	// is larger than the array size.
    public void testJUMPindirectBigArg(){
        Instruction instr = new _011010(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// test the indirect jump works
	public void testJUMPIndirect(){
		Instruction instr = new _011010(cpu,memory);
		int argument = 140; // value at this index is -1280+1400=120
		try {
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter changed", -1280+1400,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator is unchanged", accInit,
                cpu.getAccumulator());
	}


	@Test
	// test the direct jump works	
	public void testJUMPdirect(){
		Instruction instr = new _011010(cpu,memory);
		int argument = 140; 
		try {
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter changed", 140,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator is unchanged", accInit,
                cpu.getAccumulator());
	}


	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the address is a negative index.
    public void testJMPZindirectNegArg(){
        Instruction instr = new _011011(cpu, memory); 
        int argument = -12;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test 
    // this test checks whether the DataAccessException is thrown 
	// when indirect addressing is used and the address 
	// is larger than the array size.
    public void testJMPZindirectBigArg(){
        Instruction instr = new _011011(cpu, memory); 
        int argument = 256;
        try { // this is how you test that an exception is thrown
            instr.execute(argument, true);
            fail("Exception not thrown"); // we should not reach this line
        } catch (DataAccessException ex) {
            assertNotNull("An exception message is expected",
                    ex.getMessage());           
        } catch (Exception ex) {
            fail("Wrong exception thrown: " +
                    ex.getClass().getSimpleName());
        }
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter untouched
        assertEquals("Program counter unchanged", pcInit,
                cpu.getProgramCounter());
        //Test program accumulator untouched
        assertEquals("Accumulator unchanged", accInit,
                cpu.getAccumulator());
    }

	@Test
	// test the indirect jump works when accumulator is 0
	public void testJMPZIndirect(){
		Instruction instr = new _011011(cpu,memory);
		int argument = 140; // value at this index is -1280+1400=120
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter changed", -1280+1400,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator is unchanged", accInit,
                cpu.getAccumulator());
	}


	@Test
	// test the direct jump works when accumulator is 0
	public void testJMPZdirect(){
		Instruction instr = new _011011(cpu,memory);
		int argument = 140; 
		cpu.setAccumulator(0);
		try {
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter changed", 140,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator is unchanged", accInit,
                cpu.getAccumulator());
	}


	@Test
	// test the indirect jump does nothing when accumulator is not 0
	public void testJMPZnonzeroIndirect(){
		Instruction instr = new _011011(cpu,memory);
		int argument = 140; // value at this index is -1280+1400=120
		cpu.setAccumulator(10);
		try {
			instr.execute(argument, true);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator was set to 10", 10,
                cpu.getAccumulator());
	}

	@Test
	// test the direct jump does nothing when accumulator is not 0
	public void testJMPZnonzeroDirect(){
		Instruction instr = new _011011(cpu,memory);
		int argument = 140; 
		cpu.setAccumulator(10);
		try {
			instr.execute(argument, false);
		} catch (Exception e) {
			fail("Should not throw any exceptions: " + 
					e.getClass().getSimpleName());
		}
		//Test memory is not changed
        assertArrayEquals(dataCopy, memory.getData()); 
        //Test program counter incremented
        assertEquals("Program counter incremented", pcInit+1,
                cpu.getProgramCounter());
        //Accumulator is unchanged
        assertEquals("Accumulator was set to 10", 10,
                cpu.getAccumulator());
	}

//// The test for HALT WILL COME, WHEN FLAG IS AVAILABLE IN THE Machine.
}