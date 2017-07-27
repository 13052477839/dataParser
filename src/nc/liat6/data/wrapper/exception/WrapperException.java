package nc.liat6.data.wrapper.exception;

import java.io.IOException;

/**
 * 封装异常
 * @author 6tail
 *
 */
public class WrapperException extends IOException{
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE = "数据封装失败";

  public WrapperException(){
    super(MESSAGE);
  }

  public WrapperException(String message){
    super(message);
  }

  public WrapperException(Throwable cause){
    this(MESSAGE,cause);
  }

  public WrapperException(String message,Throwable cause){
    super(message);
    super.initCause(cause);
  }
}
