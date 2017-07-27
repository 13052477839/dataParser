package nc.liat6.data.wrapper.impl;

import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.XlsWriter;

/**
 * xls格式的封装器
 * 
 * @author 6tail
 *
 */
public class XlsWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "xls";
  
  public XlsWrapper(Target target){
    super(new XlsWriter(target),SUPPORT_FORMAT);
    name = "xls";
  }
}