package nc.liat6.data.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.data.parser.rule.IParserRule;
import nc.liat6.data.wrapper.exception.WrapperNotSupportException;
import nc.liat6.data.wrapper.impl.CsvWrapper;
import nc.liat6.data.wrapper.impl.HtmlWrapper;
import nc.liat6.data.wrapper.impl.TextWrapper;
import nc.liat6.data.wrapper.impl.XlsWrapper;
import nc.liat6.data.wrapper.impl.XlsxWrapper;
import nc.liat6.data.wrapper.impl.XmlWrapper;
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
   * @param parserRule 解析规则
   * @return 封装器接口
   * @throws IOException IO异常
   */
  public IWrapper getWrapper(String format,OutputStream outputStream,IParserRule parserRule) throws IOException{
    return getWrapper(format,new Target(outputStream),parserRule);
  }

  /**
   * 获取封装器
   * 
   * @param format 输出格式，如csv、xls、xlsx、html、txt等
   * @param file 待写入的文件
   * @param parserRule 解析规则
   * @return 封装器接口
   * @throws WrapperNotSupportException 不支持的输出格式
   */
  public IWrapper getWrapper(String format,File file,IParserRule parserRule) throws WrapperNotSupportException{
    return getWrapper(format,new Target(file),parserRule);
  }

  /**
   * 获取封装器
   * 
   * @param format 输出格式，如csv、xls、xlsx、html、txt等
   * @param source 待封装的数据目标
   * @param parserRule 解析规则
   * @return 解析器接口
   * @throws WrapperNotSupportException 不支持的输出格式
   */
  protected IWrapper getWrapper(String format,Target target,IParserRule parserRule) throws WrapperNotSupportException{
    List<AbstractWrapper> ws = new ArrayList<AbstractWrapper>();
    List<AbstractWrapper> wrappers = new ArrayList<AbstractWrapper>();
    ws.add(new XlsWrapper(target));
    ws.add(new XlsxWrapper(target));
    ws.add(new CsvWrapper(target));
    ws.add(new HtmlWrapper(target));
    ws.add(new XmlWrapper(target));
    ws.add(new TextWrapper(target));
    for(String name:parserRule.orderBy()){
      for(AbstractWrapper wrapper:ws){
        if(wrapper.getName().equalsIgnoreCase(name)){
          wrappers.add(wrapper);
          break;
        }
      }
    }
    for(AbstractWrapper wrapper:wrappers){
      if(wrapper.support(format)){
        wrapper.setRule(parserRule);
        System.out.println("[√] wrapper "+wrapper.getName()+" >> "+target);
        return wrapper;
      }
    }
    throw new WrapperNotSupportException(target+"");
  }
}