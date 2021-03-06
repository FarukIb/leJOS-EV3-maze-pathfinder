package main;

import java.util.Comparator;

public class Pair<E, T> {
	public E first;
	public T second;
	
	public Pair(E a, T b) {
		first = a;
		second = b;
	}
	
	public String toString()
	{
		return "[" + first.toString() + ", " + second.toString() + "]";
	}
}

