package sample.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.bean.ItemType;
import nc.liat6.data.wrapper.IWrapper;
import nc.liat6.data.wrapper.WrapperFactory;
import nc.liat6.data.wrapper.rule.IWrapperRule;
import nc.liat6.data.wrapper.rule.impl.WrapperRuleLetter;

/**
 * 最简单的文件导出示例
 * @author 6tail
 *
 */
public class MostSimpleWrapperSample{

  public static void useLabel() throws IOException{
    File file = new File("D:\\test.xls");

    //使用列对应字母，数据从第1行开始（行从0开始计）
    IWrapperRule rule = new WrapperRuleLetter(0,"A","D"){
      public Map<String,ItemType> getBodyItemTypes(){
        Map<String,ItemType> types = new HashMap<String,ItemType>();
        types.put("0,2",ItemType.number);
        return types;
      }
    };

    IWrapper wrapper = WrapperFactory.getInstance().getWrapper("xls",file,rule);
    Block head = new Block(BlockType.body);
    head.addItemContent("A","姓名");
    head.addItemContent("B","性别");
    head.addItemContent("C","年龄");
    head.addItemContent("D","民族");
    wrapper.writeBlock(head);

    Block row = new Block(BlockType.body);
    row.addItemContent("A","张三");
    row.addItemContent("B","男");
    row.addItemContent("C","20");
    row.addItemContent("D","汉");
    wrapper.writeBlock(row);

    row = new Block(BlockType.body);
    row.addItemContent("A","李四");
    row.addItemContent("B","女");
    row.addItemContent("C","18");
    row.addItemContent("D","汉");
    wrapper.writeBlock(row);

    //写入null块终止
    wrapper.writeBlock(null);
  }

  public static void main(String[] args) throws IOException{
    useLabel();
  }
}