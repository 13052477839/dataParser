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
import nc.liat6.data.wrapper.rule.WrapperRuleFixedBlockHeight;

/**
 * 带格式和样式的导出示例
 * @author 6tail
 *
 */
public class StyledWrapperSample{
  public static void main(String[] args) throws IOException{
    File file = new File("D:\\带样式的.xls");
    //自定义解析规则
    IWrapperRule rule = new WrapperRuleFixedBlockHeight(){
      public Map<String,String> getHeadItemNames(){
        Map<String,String> names = new HashMap<String,String>();
        names.put("0,0","序号");
        names.put("0,1","姓名");
        names.put("0,2","性别");
        names.put("0,3","年龄");
        names.put("0,4","民族");
        return names;
      }
      public int getBodyStartRow(){
        return 1;
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
      public Map<String,ItemType> getBodyItemTypes(){
        Map<String,ItemType> types = new HashMap<String,ItemType>();
        types.put("0,0",ItemType.number);
        types.put("0,3",ItemType.number);
        return types;
      }

      public int getDefaultBorder(){
        return BORDER_ALL;
      }

      public int getDefaultColumnWidth(){
        return 20;
      }

      public int getDefaultHeadRowHeight(){
        return 25;
      }

      public int getDefaultBodyRowHeight(){
        return 21;
      }
    };

    IWrapper wrapper = WrapperFactory.getInstance().getWrapper("xls",file,rule);
    Block head = new Block(BlockType.head);
    for(String name:rule.getHeadItemNames().values()){
      head.addItemContent(name,name);
    }
    wrapper.writeBlock(head);

    Block row = new Block(BlockType.body);
    row.addItemContent("序号","1");
    row.addItemContent("name","张三");
    row.addItemContent("性别","男");
    row.addItemContent("age","20.3");
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