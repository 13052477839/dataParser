package nc.liat6.data.wrapper.impl;

import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.XmlWriter;

/**
 * xml格式的封装器
 * 
 * @author 6tail
 *
 */
public class XmlWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "xml";
  
  public XmlWrapper(Target target){
    super(new XmlWriter(target),SUPPORT_FORMAT);
    name = "xml";
  }
}