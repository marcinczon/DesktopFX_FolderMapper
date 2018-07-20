package Scanner;

public class StringWrap
{

	private String string;

	public StringWrap()
	{
		this.string = "";
	}

	public StringWrap(String s)
	{
		this.string = s;
		System.out.println("Kon wrap: " + this.string);
	}

	@Override
	public String toString()
	{
		return "StringWrapper [string= " + string + "]";
	}

	// ****************************
	//
	// Getters and Setters
	//
	// ****************************

	public String getString()
	{
		System.out.println("Get wrap: " + this.string);
		return this.string;
	}

	public void setString(String s)
	{
		this.string = s;
		System.out.println("Set wrap: " + this.string);
	}

}
