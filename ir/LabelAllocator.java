package ir;

import java.util.ArrayList;
import java.util.List;

public class LabelAllocator {
	public List<Label> labels;
	public int next;

	public LabelAllocator() {
		labels = new ArrayList<Label>();
		next = 0;
	}

	public Label allocate() {
		Label l = new Label(next);
		labels.add(l);
		next++;
		return l;
	}

}
