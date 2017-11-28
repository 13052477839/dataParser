package nc.liat6.data.parser.rule;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.data.rule.AbstractDataRule;

/**
 * 抽象解析规则
 * 
 * @author 6tail
 *
 */
public abstract class AbstractParserRule extends AbstractDataRule implements IParserRule{
  /** 默认的解析顺序 */
  public static final String[] DEFAULT_ORDER = {"xls","xlsx","doc","docx","csv","html","text","xml"};

  public String[] orderBy(){
    return DEFAULT_ORDER;
  }

  public int getBodyEndRow(){
    return -1;
  }

  public int getBodyStartCol(){
    return 0;
  }

  public int getBodyEndCol(){
    return -1;
  }

  public Map<String,IParserRule> getFragmentRule(){
    return new HashMap<String,IParserRule>();
  }

  public Map<String,String> getHeadItemNames(){
    return new HashMap<String,String>();
  }

}