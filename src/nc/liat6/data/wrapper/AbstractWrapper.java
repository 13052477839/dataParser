package nc.liat6.data.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.parser.rule.IParserRule;
import nc.liat6.data.writer.IWriter;

/**
 * 抽象封装器
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWrapper implements IWrapper{
  /** 封装器名称 */
  protected String name;
  /** 支持的格式，多个以逗号分隔，如果为null或空字符串表示支持所有格式 */
  protected String supportFormat;
  /** 写入接口 */
  protected IWriter writer;
  /** 解析规则接口 */
  protected IParserRule rule;
  /** 是否已加载 */
  protected boolean loaded = false;
  /** 是否已跳过头部 */
  protected boolean headSkiped = false;
  /** 是否已完成 */
  protected boolean end = false;

  protected AbstractWrapper(IWriter writer){
    this.writer = writer;
  }

  protected AbstractWrapper(IWriter writer,String supportFormat){
    this(writer);
    this.supportFormat = supportFormat;
  }

  public String getName(){
    return name;
  }

  public boolean support(String format){
    if(null==supportFormat||supportFormat.length()<1) return true;
    return (","+supportFormat+",").contains(","+format+",");
  }

  /**
   * 设置解析规则
   * 
   * @param rule 解析规则
   */
  public void setRule(IParserRule rule){
    this.rule = rule;
  }
  
  protected int getWidth(Block block){
    int width = block.getWidth();
    if(width>0) return width;
    for(Item item:block.getItems()){
      int col = item.getCol();
      if(col>=width){
        width = col+1;
      }
    }
    return width;
  }

  protected int getHeight(Block block){
    int height = block.getHeight();
    if(height>0) return height;
    for(Item item:block.getItems()){
      int row = item.getRow();
      if(row>=height){
        height = row+1;
      }
    }
    return height;
  }

  protected void writeHead(Block block) throws IOException{
    Map<String,String> headMap = new HashMap<String,String>();
    for(Entry<String,String> entry:rule.getHeadItemNames().entrySet()){
      headMap.put(entry.getValue(),entry.getKey());
    }
    for(Item item:block.getItems()){
      String name = item.getName();
      if(null==name){
        continue;
      }
      String pos = headMap.get(name);
      if(null==pos){
        continue;
      }
      String[] yx = pos.split(",",-1);
      int row = Integer.parseInt(yx[0]);
      int col = Integer.parseInt(yx[1]);
      item.setRow(row);
      item.setCol(col);
    }
    int width = getWidth(block);
    int height = getHeight(block);
    int bodyStartRow = rule.getBodyStartRow();
    if(height<bodyStartRow){
      height = bodyStartRow;
    }
    List<List<String>> rows = new ArrayList<List<String>>(height);
    for(int i=0;i<height;i++){
      List<String> cols = new ArrayList<String>(width);
      for(int j=0;j<width;j++){
        cols.add("");
      }
      rows.add(cols);
    }
    for(Item item:block.getItems()){
      rows.get(item.getRow()).set(item.getCol(),item.getContent());
    }
    for(List<String> cols:rows){
      writer.writeLine(cols);
    }
    headSkiped = true;
  }

  protected void writeBody(Block block) throws IOException{
    Map<String,String> bodyMap = new HashMap<String,String>();
    for(Entry<String,String> entry:rule.getBodyItemNames().entrySet()){
      bodyMap.put(entry.getValue(),entry.getKey());
    }
    for(Item item:block.getItems()){
      String name = item.getName();
      if(null==name){
        continue;
      }
      String pos = bodyMap.get(name);
      if(null==pos){
        continue;
      }
      String[] yx = pos.split(",",-1);
      int row = Integer.parseInt(yx[0]);
      int col = Integer.parseInt(yx[1]);
      item.setRow(row);
      item.setCol(col);
    }
    int width = getWidth(block);
    int height = getHeight(block);
    List<List<String>> rows = new ArrayList<List<String>>(height);
    for(int i=0;i<height;i++){
      List<String> cols = new ArrayList<String>(width);
      for(int j=0;j<width;j++){
        cols.add("");
      }
      rows.add(cols);
    }
    for(Item item:block.getItems()){
      rows.get(item.getRow()).set(item.getCol(),item.getContent());
    }
    if(!headSkiped){
      for(int i=0;i<rule.getBodyStartRow();i++){
        List<String> cols = new ArrayList<String>(width);
        for(int j=0;j<width;j++){
          cols.add("");
        }
        rows.add(0,cols);
      }
      headSkiped = true;
    }
    for(List<String> cols:rows){
      writer.writeLine(cols);
    }
  }

  public void writeBlock(Block block) throws IOException{
    if(end){
      return;
    }
    if(!loaded){
      writer.load();
      loaded = true;
    }
    if(null==block){
      writer.stop();
      end = true;
      return;
    }
    switch(block.getType()){
      case head:
        writeHead(block);
        break;
      case body:
        writeBody(block);
        break;
      case body_in_fragment:
        break;
    }
  }
}