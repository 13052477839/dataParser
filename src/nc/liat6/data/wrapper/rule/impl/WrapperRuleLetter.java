package nc.liat6.data.wrapper.rule.impl;

import nc.liat6.data.wrapper.rule.WrapperRuleSingleLine;

/**
 * 以列标来命名格子的默认封装规则，适用于简单的单行Block封装，又不想自定义规则的
 * 
 * @author 6tail
 *
 */
public class WrapperRuleLetter extends WrapperRuleSingleLine{
  /**
   * 指定数据开始行的构造方法
   * 
   * @param startRow 数据开始行，从0开始计
   * @param startColLabel 数据开始列标，A、B、C、AB之类的
   * @param endColLabel 数据结束列标，A、B、C、AB之类的
   */
  public WrapperRuleLetter(int startRow,String startColLabel,String endColLabel){
    super(startRow);
    int startCol = getPos(startColLabel);
    int endCol = getPos(endColLabel);
    for(int i = startCol;i<=endCol;i++){
      bodyItemNames.put("0,"+i,getLabel(i));
    }
  }

  /**
   * 默认构造方法，数据从第0行，A列开始，Z列(含)结束
   */
  public WrapperRuleLetter(){
    this(0,"A","Z");
  }
}