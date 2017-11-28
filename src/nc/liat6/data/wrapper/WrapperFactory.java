package nc.liat6.data.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.data.wrapper.exception.WrapperNotSupportException;
import nc.liat6.data.wrapper.impl.CsvWrapper;
import nc.liat6.data.wrapper.impl.HtmlWrapper;
import nc.liat6.data.wrapper.impl.TextWrapper;
import nc.liat6.data.wrapper.impl.XlsWrapper;
import nc.liat6.data.wrapper.impl.XlsxWrapper;
import nc.liat6.data.wrapper.impl.XmlWrapper;
import nc.liat6.data.wrapper.rule.IWrapperRule;
import nc.liat6.data.writer.bean.Target;

/**
 * 封装器工厂
 * 
 * @author 6tail
 *
 */
public class WrapperFactory{
  /** 封装器工厂实例 */
  private static WrapperFactory instance;

  private WrapperFactory(){}

  /**
   * 获取封装器工厂实例
   * 
   * @return 封装器工厂实例
   */
  public static synchronized WrapperFactory getInstance(){
    if(null==instance){
      instance = new WrapperFactory();
    }
    return instance;
  }

  /**
   * 获取封装器
   * @param format 输出格式，如csv、xls、xlsx、html、txt等
   * @param outputStream 输出流
   * @param wrapperRule 封装规则
   * @return 封装器接口
   * @throws IOException IO异常
   */
  public IWrapper getWrapper(String format,OutputStream outputStream,IWrapperRule wrapperRule) throws IOException{
    return getWrapper(format,new Target(outputStream),wrapperRule);
  }

  /**
   * 获取封装器
   * 
   * @param format 输出格式，如csv、xls、xlsx、html、txt等
   * @param file 待写入的文件
   * @param wrapperRule 封装规则
   * @return 封装器接口
   * @throws WrapperNotSupportException 不支持的输出格式
   */
  public IWrapper getWrapper(String format,File file,IWrapperRule wrapperRule) throws WrapperNotSupportException{
    return getWrapper(format,new Target(file),wrapperRule);
  }

  /**
   * 获取封装器
   * 
   * @param format 输出格式，如csv、xls、xlsx、html、txt等
   * @param source 待封装的数据目标
   * @param wrapperRule 封装规则
   * @return 解析器接口
   * @throws WrapperNotSupportException 不支持的输出格式
   */
  protected IWrapper getWrapper(String format,Target target,IWrapperRule wrapperRule) throws WrapperNotSupportException{
    List<AbstractWrapper> wrappers = new ArrayList<AbstractWrapper>();
    wrappers.add(new XlsWrapper(target));
    wrappers.add(new XlsxWrapper(target));
    wrappers.add(new CsvWrapper(target));
    wrappers.add(new HtmlWrapper(target));
    wrappers.add(new XmlWrapper(target));
    wrappers.add(new TextWrapper(target));
    
    for(AbstractWrapper wrapper:wrappers){
      if(wrapper.support(format)){
        wrapper.setRule(wrapperRule);
        System.out.println("[√] wrapper "+wrapper.getName()+" >> "+target);
        return wrapper;
      }
    }
    throw new WrapperNotSupportException(target+"");
  }
}