public class ASTNode {
	public int line;
	public int offset;

	public ASTNode() {
		line = -1;
		offset = -1;
	}

	public ASTNode (int l, int o ){
		line = l;
		offset = o;
	}
}