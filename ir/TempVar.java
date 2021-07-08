package ir;

import ast.type.Type;

public class TempVar {
	Type type;
	int number;
	String label;
	TempSet set;

	public enum TempSet {
		PARAMETER("P"), LOCAL_VARIABLE("L");

		String s;

		TempSet(String s) {
			this.s = s;
		}

		public String toString() {
			return s;
		}
	}

	public TempVar(Type t, int number, String label, TempSet s) {
		this.type = t;
		this.number = number;
		this.label = label;
		this.set = s;
	}

	public String toLongString() {
		String label = this.label == null ? "" : " [" + set + "(\"" + this.label + "\")]";
		return "TEMP " + number + ":" + type.toShortString() + label + ";";
	}

	public String toString() {
		return "T" + number;
	}

}
