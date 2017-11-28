package nc.liat6.data.wrapper.rule;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.data.parser.bean.ItemType;
import nc.liat6.data.rule.AbstractDataRule;

/**
 * 抽象封装规则
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWrapperRule extends AbstractDataRule implements IWrapperRule{

  public int getBodyBlockHeight(){
    return 1;
  }

  public Map<String,String> getHeadItemNames(){
    return new HashMap<String,String>();
  }

  public Map<String,ItemType> getBodyItemTypes(){
    return new HashMap<String,ItemType>();
  }
  
  public Map<String,String> getBodyItemNames(){
    return new HashMap<String,String>();
  }

  public int getDefaultColumnWidth(){
    return MODE_DEFAULT;
  }

  public int getDefaultHeadRowHeight(){
    return MODE_DEFAULT;
  }

  public int getDefaultBodyRowHeight(){
    return MODE_DEFAULT;
  }

  public int getDefaultBorder(){
    return BORDER_NONE;
  }

  public Map<String,Integer> getHeadRowHeights(){
    return new HashMap<String,Integer>();
  }

  public Map<String,Integer> getBodyRowHeights(){
    return new HashMap<String,Integer>();
  }

  public Map<String,Integer> getColumnWidths(){
    return new HashMap<String,Integer>();
  }
}