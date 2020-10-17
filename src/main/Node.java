package main;

import java.util.Comparator;

public class Node {
	public short n;
	public Pair<Integer, Integer> coords;
	
	public Node(short num, int x, int y)
	{
		n = num;
		coords = new Pair<Integer, Integer>(x, y);
	}
}


class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node arg0, Node arg1) {
		int firstDiff = -(arg1.coords.first + arg1.coords.second), secondDiff = -(arg1.coords.first + arg1.coords.second);
		if (firstDiff < secondDiff)
			return 1;
		else if (firstDiff > secondDiff)
			return -1;
		return 0;
	}
		
}
