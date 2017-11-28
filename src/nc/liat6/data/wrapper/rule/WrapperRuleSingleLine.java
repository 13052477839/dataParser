package nc.liat6.data.wrapper.rule;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 适用于简单的单行Block封装
 * 
 * @author 6tail
 *
 */
public abstract class WrapperRuleSingleLine extends WrapperRuleFixedBlockHeight{
  /** 数据开始行，从0开始计 */
  protected int bodyStartRow;
  /** 格子字段映射 */
  protected Map<String,String> bodyItemNames = new LinkedHashMap<String,String>();

  protected WrapperRuleSingleLine(int bodyStartRow){
    this.bodyStartRow = bodyStartRow;
  }

  public Map<String,String> getBodyItemNames(){
    return bodyItemNames;
  }

  public int getBodyStartRow(){
    return bodyStartRow;
  }
}