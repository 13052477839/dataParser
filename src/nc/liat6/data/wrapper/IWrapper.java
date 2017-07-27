package nc.liat6.data.wrapper;

import java.io.IOException;
import nc.liat6.data.parser.bean.Block;

/**
 * 封装器接口
 * 
 * @author 6tail
 *
 */
public interface IWrapper{
  /**
   * 获取封装器名称
   * 
   * @return 封装器名称
   */
  String getName();

  /**
   * 是否支持指定格式
   * @param format 格式
   * @return true/false 支持/不支持
   */
  boolean support(String format);

  /**
   * 写入一个块，直到写入null块时停止。如果未设置head的写入规则，不写入head块。
   * 
   * @param 块
   */
   void writeBlock(Block block) throws IOException;
}