package e1.tools.suite;

public interface IProgress 
{
	void notifyProgress(int value, int max, String text);
	void notifyProgressTotal(int value, int max, String text);
}
