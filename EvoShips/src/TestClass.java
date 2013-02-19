import arena.Arena;


public class TestClass 
{

	public TestClass() 
	{
		Arena a = new Arena(10, 1, 10);
		a.run();
	}
	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
