package pippin;

public enum States {

	AUTO_STEPPING {
		public void enter() {
			states[CLEAR] = false;
			states[LOAD] = false;
			states[RELOAD] = false;
			states[RUN] = true;
			states[RUNNING] = true;
			states[STEP] = false;
			states[ASSEMBLE] = false;
		}
	},
	NOTHING_LOADED {
		public void enter() {
			states[CLEAR] = false;
			states[LOAD] = true;
			states[RELOAD] = false;
			states[RUN] = false;
			states[RUNNING] = false;
			states[STEP] = false;
			states[ASSEMBLE] = true;
		}
	},
	PROGRAM_HALTED {
		public void enter() {
			states[CLEAR] = true;
			states[LOAD] = true;
			states[RELOAD] = true;
			states[RUN] = false;
			states[RUNNING] = false;
			states[STEP] = false;
			states[ASSEMBLE] = true;
		}
	},
	PROGRAM_LOADED_NOT_AUTOSTEPPING {
		public void enter() {
			states[CLEAR] = true;
			states[LOAD] = true;
			states[RELOAD] = true;
			states[RUN] = true;
			states[RUNNING] = false;
			states[STEP] = true;
			states[ASSEMBLE] = true;
		}
	};
	
	private static final int CLEAR = 0;
	private static final int LOAD = 1;
	private static final int RELOAD = 2;
	private static final int RUN = 3;
	private static final int RUNNING = 4;
	private static final int STEP = 5;
	private static final int ASSEMBLE = 6;

	boolean[] states = new boolean[7];

	public abstract void enter();

	public boolean getClearActive() { // NEEDED FOR CONTROLPANEL
		return states[CLEAR];
	}
	public boolean getLoadFileActive() { // NEEDED FOR FILE MENU
		return states[LOAD];
	}
	public boolean getReloadActive() { // NEEDED FOR CONTROLPANEL
		return states[RELOAD];
	}
	public boolean getRunSuspendActive() { // NEEDED FOR CONTROLPANEL
		return states[RUN];
	}
	public boolean getRunningActive() { // NEEDED TO SWITCH PAUSE/RUN ICONS
		return states[RUNNING];
	}
	public boolean getStepActive() { // NEEDED FOR CONTROLPANEL
		return states[STEP];
	}
	public boolean getAssembleFileActive() { // NEEDED FOR FILE MENU
		return states[ASSEMBLE];
	}
}