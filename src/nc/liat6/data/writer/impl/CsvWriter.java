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
 * CSV导出
 * 
 * @author 6tail
 * 
 */
public class CsvWriter extends AbstractWriter implements Closeable{
  /** 默认的文件编码 */
  public static final String DEFAULT_ENCODE = "GBK";
  /** 文件编码 */
  public static String ENCODE = DEFAULT_ENCODE;
  /** 回车符 */
  public static String CR = "\r";
  /** 换行符 */
  public static String LF = "\n";
  /** 列间隔符 */
  public static final String SPACE = ",";
  /** 双引号 */
  public static final String QUOTE = "\"";
  private Writer writer;

  public CsvWriter(Target target){
    super(target);
  }

  public void close(){
    if(null!=writer){
      try{
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
    for(int i = 0,j=line.size();i<j;i++){
      Item item = line.get(i);
      String o = null==item?"":item.getContent();
      String ro = o;
      if(null==o){
        ro = "";
      }else{
        boolean needQuote = false;
        if(o.contains(QUOTE)){
          ro = o.replace(QUOTE,QUOTE+QUOTE);
          needQuote = true;
        }
        if(o.contains(CR)||o.contains(LF)||o.contains(SPACE)){
          needQuote = true;
        }
        if(needQuote){
          ro = QUOTE+ro+QUOTE;
        }
      }
      s.append(ro);
      if(i<j-1){
        s.append(SPACE);
      }
    }
    s.append(CR);
    s.append(LF);
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
  }

}