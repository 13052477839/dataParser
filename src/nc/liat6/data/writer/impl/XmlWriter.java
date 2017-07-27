package nc.liat6.data.writer.impl;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.util.IOHelper;
import nc.liat6.data.writer.AbstractWriter;
import nc.liat6.data.writer.bean.Target;

/**
 * XML导出
 * 
 * @author 6tail
 * 
 */
public class XmlWriter extends AbstractWriter implements Closeable{
  /** 默认的文件编码 */
  public static final String DEFAULT_ENCODE = "UTF-8";
  /** 文件编码 */
  public static String ENCODE = DEFAULT_ENCODE;
  private Writer writer;

  public XmlWriter(Target target){
    super(target);
  }

  public void close(){
    if(null!=writer){
      try{
        writer.write("</rows></data>");
        writer.flush();
      }catch(IOException e){
      }
    }
    IOHelper.closeQuietly(writer);
  }

  public void stop(){
    if(!stop){
      close();
    }
    super.stop();
  }

  public void writeLine(List<Item> line) throws IOException{
    if(stop){
      return;
    }
    if(null==line){
      stop();
      close();
      return;
    }
    StringBuffer s = new StringBuffer();
    s.append("<row>");
    for(int i = 0,j=line.size();i<j;i++){
      Item item = line.get(i);
      String o = null==item?"":item.getContent();
      s.append("<col>");
      if(null!=o){
        if(o.contains("<")||o.contains(">")){
          s.append("<![CDATA[");
          s.append(o);
          s.append("]]>");
        }else{
          s.append(o);
        }
      }
      s.append("</col>");
    }
    s.append("</row>");
    writer.write(s.toString());
  }

  public void load() throws IOException{
    switch(target.getTargetType()){
      case file:
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target.getFile()),ENCODE));
        break;
      case outputStream:
        writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream(),ENCODE));
        break;
    }
    writer.write("<?xml version=\"1.0\" encoding=\""+ENCODE+"\"?><data><rows>");
  }

}