package nc.liat6.data.wrapper.impl;

import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.CsvWriter;

/**
 * csv格式的封装器
 * 
 * @author 6tail
 *
 */
public class CsvWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "csv";
  
  public CsvWrapper(Target target){
    super(new CsvWriter(target),SUPPORT_FORMAT);
    name = "csv";
  }
}