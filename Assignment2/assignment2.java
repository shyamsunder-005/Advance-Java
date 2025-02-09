//Abstract class bird have common method sleep for all kinds of birds
//this follows srp as it have only single responsibility that is sleep
abstract class Bird{
	void sleep(){
		System.out.println("Sleeping");
	}
}
//interface canfly represent birds which can fly as seperte responsibility
interface canfly{
	void fly();
}
//sparrow inherts sleep from bird and it is able to implement canfly
class Sparrow extends Bird implements canfly{
	public void fly(){
		System.out.println("Sparrow can fly!!!");
	}
}
// Penguin extends Bird but does not implement canfly, as it cannot fly.
//  it  maintains sleep in Bird (sleep())
// this follows Liskov Substitution Principle ensures that subclasses do not break expected behavior.
class Penguin extends Bird{
	public void swim(){
		System.out.println("Penguin can swim!!!");
	}
}
public class assignment2{
 	public static void main(String[] args) {
        	Sparrow sparrow = new Sparrow();
       		Penguin penguin = new Penguin();
		sparrow.fly();
		sparrow.sleep();
		penguin.swim();
		penguin.sleep();	
       	}
}	
