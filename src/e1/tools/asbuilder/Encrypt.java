package e1.tools.asbuilder;

public class Encrypt 
{
	private int Pos1;
	private int Pos2;
	private EncryptCode enCode;
	
	public void InitEncrypt(int a1, int b1, int c1, int fst1, int a2, int b2, int c2, int fst2)
	{
		enCode = new EncryptCode();
		enCode.initEncrypt(a1, b1, c1, fst1, a2, b2, c2, fst2);
	}
	
	public void DoEncrypt(byte []buff , int begin , int length , boolean move)
	{
		int oldPos1 = Pos1;
		int oldPos2 = Pos2;
		for (int i = begin; i < begin + length; i++)
		{
			buff[i] ^= enCode.BufferEncrypt1[Pos1];
			buff[i] ^= enCode.BufferEncrypt2[Pos2];
			Pos1++;
			if (this.Pos1 >= 256) 
			{
				this.Pos1 = 0;
				this.Pos2++;
				if (this.Pos2 >= 256) 
				{
					this.Pos2 = 0;
				}
			}
		}
		if (!move)
		{
			this.Pos1 = oldPos1;
			this.Pos2 = oldPos2;
		}
	}
}

class EncryptCode {
	public byte []BufferEncrypt1;
	public byte []BufferEncrypt2;
	
	public void initEncrypt(int a1, int b1, int c1, int fst1, int a2, int b2, int c2, int fst2)
	{
		BufferEncrypt1 = new byte[256];
		BufferEncrypt2 = new byte[256];
		byte code = (byte)(fst1 & 0xFF);
		
		for (int i = 0; i < 256; i++)
		{
			this.BufferEncrypt1[i] = code;
			code = (byte)((a1*code*code + b1*code + c1) % 256);
		}
		
		code = (byte)(fst2 & 0xFF);
		
		for (int i = 0; i < 256; i++)
		{
			this.BufferEncrypt2[i] = code;
			code = (byte)((a2*code*code + b2*code + c2) % 256);
		}
	}
}