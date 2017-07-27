package nc.liat6.data.wrapper.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nc.liat6.data.parser.bean.Block;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.wrapper.AbstractWrapper;
import nc.liat6.data.writer.bean.Target;
import nc.liat6.data.writer.impl.TextWriter;

/**
 * txt格式的封装器
 * 
 * @author 6tail
 *
 */
public class TextWrapper extends AbstractWrapper{
  /** 支持的格式 */
  public static final String SUPPORT_FORMAT = "txt,text";
  private final class TextItemComparator implements Comparator<Item>{
    public int compare(Item o1,Item o2){
      return o1.getRow()-o2.getRow();
    }
  }
  
  public TextWrapper(Target target){
    super(new TextWriter(target),SUPPORT_FORMAT);
    name = "text";
  }

  public void writeHead(Block block) throws IOException{
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
    List<List<String>> rows = new ArrayList<List<String>>();
    List<Item> items = block.getItems();
    for(Item item:items){
      String o = item.getContent();
      if(null==o){
        o = "";
      }
      int fill = item.getCol()-o.getBytes(TextWriter.ENCODE).length;
      for(int i=0;i<fill;i++){
        o += " ";
      }
      item.setContent(o);
    }
    Collections.sort(items,new TextItemComparator());
    List<String> cols = new ArrayList<String>();
    for(Item item:items){
      cols.add(item.getContent());
    }
    rows.add(cols);
    for(int i=0;i<rule.getBodyStartRow()-1;i++){
      rows.add(0,new ArrayList<String>());
    }
    for(List<String> row:rows){
      writer.writeLine(row);
    }
    headSkiped = true;
  }

  public void writeBody(Block block) throws IOException{
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
    List<List<String>> rows = new ArrayList<List<String>>();
    List<Item> items = block.getItems();
    for(Item item:items){
      String o = item.getContent();
      if(null==o){
        o = "";
      }
      int fill = item.getCol()-o.getBytes(TextWriter.ENCODE).length;
      for(int i=0;i<fill;i++){
        o += " ";
      }
      item.setContent(o);
    }
    Collections.sort(items,new TextItemComparator());
    List<String> cols = new ArrayList<String>();
    for(Item item:items){
      cols.add(item.getContent());
    }
    rows.add(cols);
    if(!headSkiped){
      for(int i=0;i<rule.getBodyStartRow();i++){
        rows.add(0,new ArrayList<String>());
      }
      headSkiped = true;
    }
    for(List<String> row:rows){
      writer.writeLine(row);
    }
  }
}