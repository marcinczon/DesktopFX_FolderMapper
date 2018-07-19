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
	}

	public String getString()
	{
		return this.string;
	}

	public void setString(String s)
	{
		this.string = s;
	}

	@Override
	public String toString()
	{
		return "StringWrapper [string= " + string + "]";
	}
}
