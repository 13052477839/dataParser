package nc.liat6.data.wrapper.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * 块高度固定的抽象解析规则
 * 
 * @author 6tail
 *
 */
public abstract class WrapperRuleFixedBlockHeight extends AbstractWrapperRule{

  public Map<Integer,String> getEndRowValues(){
    return new HashMap<Integer,String>();
  }
}