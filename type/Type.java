public abstract class Type {
	public abstract void accept (Visitor v );
	public abstract Type accept (TypeVisitor v) throws SemanticException;
	public abstract Temp accept (TempVisitor v);
	public abstract String toShortString();
	public abstract boolean equals (Object o);
}