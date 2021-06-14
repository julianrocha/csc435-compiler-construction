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
		// TODO: how should offset handle tabs vs spaces?
		return "Ln:" + Integer.toString(l) + ", Col:" + Integer.toString(o) + " error: " + msg;
	}

}
