package nc.liat6.data.wrapper.impl;

import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.HtmlWriter;

/**
 * html格式的封装器
 * 
 * @author 6tail
 *
 */
public class HtmlWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "html";
  
  public HtmlWrapper(Target target){
    super(new HtmlWriter(target),SUPPORT_FORMAT);
    name = "html";
  }
}