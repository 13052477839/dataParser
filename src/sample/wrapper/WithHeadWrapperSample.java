package sample.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.rule.IParserRule;
import nc.liat6.data.parser.rule.ParserRuleFixedBlockHeight;
import nc.liat6.data.wrapper.IWrapper;
import nc.liat6.data.wrapper.WrapperFactory;

/**
 * 带表头的导出示例
 * @author 6tail
 *
 */
public class WithHeadWrapperSample{
  public static void main(String[] args) throws IOException{
    File file = new File("D:\\带表头的.xlsx");
    //自定义解析规则
    IParserRule rule = new ParserRuleFixedBlockHeight(){
      public Map<String,String> getHeadItemNames(){
        Map<String,String> names = new HashMap<String,String>();
        names.put("0,0","表名");
        names.put("1,0","填报人");
        names.put("1,1","填报人内容");
        names.put("3,0","序号");
        names.put("3,1","姓名");
        names.put("3,2","性别");
        names.put("3,3","年龄");
        names.put("3,4","民族");
        return names;
      }
      public int getBodyStartRow(){
        return 4;
      }
      public Map<String,String> getBodyItemNames(){
        Map<String,String> names = new HashMap<String,String>();
        names.put("0,0","序号");
        names.put("0,1","name");
        names.put("0,2","性别");
        names.put("0,3","age");
        names.put("0,4","民族");
        return names;
      }
    };

    IWrapper wrapper = WrapperFactory.getInstance().getWrapper("xlsx",file,rule);
    Block head = new Block(BlockType.head);
    for(String name:rule.getHeadItemNames().values()){
      head.addItemContent(name,name);
    }
    head.addItemContent("表名","聚会名单");
    head.addItemContent("填报人内容","麻子");
    wrapper.writeBlock(head);

    Block row = new Block(BlockType.body);
    row.addItemContent("序号","1");
    row.addItemContent("name","张三");
    row.addItemContent("性别","男");
    row.addItemContent("age","20");
    row.addItemContent("民族","汉");
    wrapper.writeBlock(row);

    row = new Block(BlockType.body);
    row.addItemContent("序号","2");
    row.addItemContent("name","李四");
    row.addItemContent("性别","女");
    row.addItemContent("age","18");
    row.addItemContent("民族","汉");
    wrapper.writeBlock(row);

    //写入null块终止
    wrapper.writeBlock(null);
  }
}