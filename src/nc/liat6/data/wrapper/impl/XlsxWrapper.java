package nc.liat6.data.wrapper.impl;

import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.XlsxWriter;

/**
 * xlsx格式的封装器
 * 
 * @author 6tail
 *
 */
public class XlsxWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "xlsx";
  
  public XlsxWrapper(Target target){
    super(new XlsxWriter(target),SUPPORT_FORMAT);
    name = "xlsx";
  }
}