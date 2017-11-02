package cn.zhouzy.remind.entity;

/**
 * Created by zhouzy on 2017/11/2.
 * TTS消息实体类
 */

public class TTSMessage {

	/**
	 * 操作类型
	 */
	private int what ;
	/**
	 * 时间间隔
	 */
	private int priod;

	public int getWhat()
	{
		return what;
	}

	public void setWhat(int what)
	{
		this.what = what;
	}

	public int getPriod()
	{
		return priod;
	}

	public void setPriod(int priod)
	{
		this.priod = priod;
	}
}
