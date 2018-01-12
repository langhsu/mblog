/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.lang;

/**
 * 
 * @author langhsu
 *
 */
public class MtonsException extends RuntimeException {
	private static final long serialVersionUID = -7443213283905815106L;
	private int code;

	public MtonsException() {
	}
	
	/**
	 * MtonsException
	 * @param code 错误代码
	 */
	public MtonsException(int code) {
		super("code=" + code);
		this.code = code;
	}

	/**
	 * MtonsException
	 * @param message 错误消息
	 */
	public MtonsException(String message) {
		super(message);
	}

	/**
	 * MtonsException
	 * @param cause 捕获的异常
	 */
	public MtonsException(Throwable cause) {
		super(cause);
	}

	/**
	 * MtonsException
	 * @param message 错误消息
	 * @param cause 捕获的异常
	 */
	public MtonsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * MtonsException
	 * @param code 错误代码
	 * @param message 错误消息
	 */
	public MtonsException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
