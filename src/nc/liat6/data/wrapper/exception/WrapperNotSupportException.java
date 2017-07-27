package nc.liat6.data.wrapper.exception;

/**
 * 不支持的输出格式
 * 
 * @author 6tail
 *
 */
public class WrapperNotSupportException extends WrapperException{
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE = "不支持的输出格式";

  public WrapperNotSupportException(){
    super(MESSAGE);
  }

  public WrapperNotSupportException(String message){
    super(message);
  }

  public WrapperNotSupportException(Throwable cause){
    this(MESSAGE,cause);
  }

  public WrapperNotSupportException(String message,Throwable cause){
    super(message,cause);
  }
}