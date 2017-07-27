package nc.liat6.data.writer.impl;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import nc.liat6.data.util.IOHelper;
import nc.liat6.data.writer.AbstractWriter;
import nc.liat6.data.writer.bean.Target;

/**
 * HTML导出
 * 
 * @author 6tail
 * 
 */
public class HtmlWriter extends AbstractWriter implements Closeable{
  /** 默认的文件编码 */
  public static final String DEFAULT_ENCODE = "UTF-8";
  /** 文件编码 */
  public static String ENCODE = DEFAULT_ENCODE;
  private Writer writer;

  public HtmlWriter(Target target){
    super(target);
  }

  public void close(){
    if(null!=writer){
      try{
        writer.write("</tbody></table></body></html>");
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

  public void writeLine(List<String> line) throws IOException{
    if(stop){
      return;
    }
    if(null==line){
      stop();
      close();
      return;
    }
    StringBuffer s = new StringBuffer();
    s.append("<tr>");
    for(int i = 0,j=line.size();i<j;i++){
      String o = line.get(i);
      s.append("<td>");
      if(null!=o){
        s.append(o);
      }
      s.append("</td>");
    }
    s.append("</tr>");
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
    writer.write("<!DOCTYPE html><html><head><meta charset=\""+ENCODE+"\"></head><body><table><tbody>");
  }

}