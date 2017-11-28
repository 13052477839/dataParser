package nc.liat6.data.writer.impl;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.util.IOHelper;
import nc.liat6.data.writer.AbstractWriter;
import nc.liat6.data.writer.bean.Target;

/**
 * txt导出
 * 
 * @author 6tail
 * 
 */
public class TextWriter extends AbstractWriter implements Closeable{
  /** 默认的文件编码 */
  public static final String DEFAULT_ENCODE = "GBK";
  /** 文件编码 */
  public static String ENCODE = DEFAULT_ENCODE;
  /** 回车符 */
  public static String CR = "\r";
  /** 换行符 */
  public static String LF = "\n";
  private Writer writer;

  public TextWriter(Target target){
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

  public void writeLine(List<Item> line,BlockType blockType,int lineIndex) throws IOException{
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
      if(null!=o){
        s.append(o);
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