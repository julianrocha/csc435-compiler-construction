package ast.visitors;

public class SemanticException extends RuntimeException {
	private int l;
	private int o;
	private String msg;

	public SemanticException(int l, int o, String msg) {
		this.l = l;
		this.o = o;
		this.msg = msg;
	}

	public String toString() {
		// NOTE: for offset, tabs will be counted as one char even if IDE count as 4
		// spaces
		return "Error:" + Integer.toString(l) + ":" + Integer.toString(o) + ":" + msg;
	}

}
