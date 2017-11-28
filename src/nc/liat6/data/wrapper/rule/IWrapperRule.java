package nc.liat6.data.wrapper.rule;

import java.util.Map;
import nc.liat6.data.parser.bean.ItemType;
import nc.liat6.data.rule.IDataRule;

/**
 * 封装规则接口
 * 
 * @author 6tail
 *
 */
public interface IWrapperRule extends IDataRule{

  /** 默认模式 */
  int MODE_DEFAULT = 0;
  /** 自适应模式 */
  int MODE_AUTO = -1;

  /**边框：无*/
  int BORDER_NONE = 0;
  /**边框：所有*/
  int BORDER_ALL = 1;

  /**
   * 获取默认列宽度(单位：1/256字符)
   * @return 默认列宽度：默认模式、自适应模式、指定值(>0)
   */
  int getDefaultColumnWidth();

  /**
   * 获取表头默认行高度(单位：1/20点)
   * @return 表头默认行高度：默认模式、自适应模式、指定值(>0)
   */
  int getDefaultHeadRowHeight();

  /**
   * 获取数据默认行高度(单位：1/20点)
   * @return 数据默认行高度：默认模式、自适应模式、指定值(>0)
   */
  int getDefaultBodyRowHeight();

  /**
   * 获取默认边框样式
   * @return 边框样式
   */
  int getDefaultBorder();

  /**
   * 获取数据各格子的类型映射
   * 
   * @return 数据各格子的类型映射
   */
  Map<String,ItemType> getBodyItemTypes();

  /**
   * 获取各列的宽度映射
   * 
   * @return 各列的宽度映射
   */
  Map<String,Integer> getColumnWidths();

  /**
   * 获取表头各行的高度映射
   * 
   * @return 数据表头的高度映射
   */
  Map<String,Integer> getHeadRowHeights();

  /**
   * 获取数据各行的高度映射
   * 
   * @return 数据各行的高度映射
   */
  Map<String,Integer> getBodyRowHeights();
}