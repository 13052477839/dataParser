package nc.liat6.data.writer;

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

}