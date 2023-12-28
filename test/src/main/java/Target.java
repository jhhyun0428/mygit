import java.time.LocalDateTime;

import symbolsolver.Foo;

public class Target {
	public int x;
	private Foo foo = new Foo();
	
    public static void main(String args[]){
    	// System.out.print(LocalDateTime.now());
    }
    
    public void foo() {
        // System.out.println("foo");
    }

    public void bar() {
        // System.out.println("bar");
    }
    
    public void typeSolverTest() {
        foo.bar();
    }
}