package nc.liat6.data.writer;

import java.io.IOException;
import java.util.List;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.wrapper.rule.IWrapperRule;
import nc.liat6.data.writer.bean.Target;

/**
 * 输出接口
 * 
 * @author 6tail
 *
 */
public interface IWriter{
  
  /**
   * 重新加载
   */
  void load() throws IOException;
  
  /**
   * 写入下一行数据，如果写入null或停止写入，则结束
   * 
   * @param line 行数据
   * @param blockType 块类型
   * @param lineIndex 位于当前块的行序号
   * @throws IOException
   */
  void writeLine(List<Item> line,BlockType blockType,int lineIndex) throws IOException;

  /**
   * 停止写入
   */
  void stop();

  /**
   * 获取写入的数据目标
   * 
   * @return 数据目标
   */
  Target getTarget();

  /**
   * 设置封装规则
   * @param rule
   */
  void setRule(IWrapperRule rule);
}