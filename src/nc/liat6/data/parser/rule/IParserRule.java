package nc.liat6.data.parser.rule;

import java.util.Map;
import nc.liat6.data.rule.IDataRule;

/**
 * 解析规则接口，第一个数据块之上的内容，合并为一个head块。
 * 
 * @author 6tail
 *
 */
public interface IParserRule extends IDataRule{
  /**
   * 获取解析器的先后顺序
   * @return
   */
  String[] orderBy();

  /**
   * 获取解析规则类型
   * 
   * @return 解析规则类型
   */
  ParserRuleType getType();

  /**
   * 获取数据结束行，包含，如果为-1表示直到终点行，一般用于定义分片的区域
   * 
   * @return 数据结束行，包含，如果为-1表示直到终点行
   */
  int getBodyEndRow();

  /**
   * 获取数据开始列，从0开始计
   * 
   * @return 数据开始列，从0开始计
   */
  int getBodyStartCol();

  /**
   * 获取数据结束列，包含，如果为-1表示直到终点列
   * 
   * @return 数据结束列，包含，如果为-1表示直到终点列
   */
  int getBodyEndCol();

  /**
   * 获取数据分片的名称映射
   * 
   * @return 数据分片的名称映射
   */
  Map<String,IParserRule> getFragmentRule();

  /**
   * 获取块结束行指定列的内容映射，仅用于根据标识自动检测块结束行
   * @return 块结束行指定列的内容映射
   */
  Map<Integer,String> getEndRowValues();

}