package nc.liat6.data.writer;

import nc.liat6.data.wrapper.rule.IWrapperRule;
import nc.liat6.data.writer.bean.Target;

/**
 * 抽象写
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWriter implements IWriter{
  /** 数据目标 */
  protected Target target;
  protected IWrapperRule rule;
  /** 是否停止写入 */
  protected boolean stop;

  protected AbstractWriter(Target target){
    this.target = target;
  }

  public Target getTarget(){
    return target;
  }

  public void stop(){
    stop = true;
  }

  /**
   * 设置封装规则
   * 
   * @param rule 封装规则
   */
  public void setRule(IWrapperRule rule){
    this.rule = rule;
  }
}