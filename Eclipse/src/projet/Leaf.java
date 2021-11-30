package projet;

public class Leaf {
	public String name;
	public Object attribute;
	
	public Leaf(String name, Object attribute) {
		this.name = name;
		this.attribute=attribute;
	}
	public String toString() {
		return(name);
	}
}
