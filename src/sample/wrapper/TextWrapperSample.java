package sample.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.rule.IParserRule;
import nc.liat6.data.parser.rule.ParserRuleSingleLine;
import nc.liat6.data.wrapper.IWrapper;
import nc.liat6.data.wrapper.WrapperFactory;

/**
 * txt文件导出示例
 * @author 6tail
 *
 */
public class TextWrapperSample{
  public static void useName() throws IOException{
    File file = new File("D:\\test.txt");
    IParserRule rule = new ParserRuleSingleLine(0){
      public Map<String,String> getBodyItemNames(){
        Map<String,String> names = new HashMap<String,String>();
        names.put("0,20","姓名");
        names.put("20,8","性别");
        names.put("28,8","年龄");
        names.put("36,20","民族");
        return names;
      }
    };

    IWrapper wrapper = WrapperFactory.getInstance().getWrapper("txt",file,rule);
    Block head = new Block(BlockType.body);
    for(String name:rule.getBodyItemNames().values()){
      head.addItemContent(name,name);
    }
    wrapper.writeBlock(head);

    Block row = new Block(BlockType.body);
    row.addItemContent("姓名","张三");
    row.addItemContent("性别","男");
    row.addItemContent("年龄","20");
    row.addItemContent("民族","汉");
    wrapper.writeBlock(row);

    row = new Block(BlockType.body);
    row.addItemContent("姓名","李四");
    row.addItemContent("性别","女");
    row.addItemContent("年龄","18");
    row.addItemContent("民族","汉");
    wrapper.writeBlock(row);

    //写入null块终止
    wrapper.writeBlock(null);
  }
  
  public static void main(String[] args) throws IOException{
    useName();
  }
}