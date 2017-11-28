package nc.liat6.data.rule;

import java.util.Map;

/**
 * 数据规则
 * @author 6tail
 *
 */
public interface IDataRule{
  /**
   * 获取头部各格子的名称映射
   * 
   * @return 头部各格子的名称映射
   */
  Map<String,String> getHeadItemNames();

  /**
   * 获取数据开始行，从0开始计
   * 
   * @return 数据开始行，从0开始计
   */
  int getBodyStartRow();

  /**
   * 获取数据块高度，即包含的行数
   * 
   * @return 块高度，即包含的行数
   */
  int getBodyBlockHeight();

  /**
   * 获取数据各格子的名称映射
   * 
   * @return 数据各格子的名称映射
   */
  Map<String,String> getBodyItemNames();
}