package sad;

public class child extends parent{
	child(String s){
		System.out.println(s);
	}
public static void main(String[] args) {
	child c=new child("child");
	System.out.println(c);
}
}
