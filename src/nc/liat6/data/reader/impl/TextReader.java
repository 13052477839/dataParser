package nc.liat6.data.reader.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.data.reader.AbstractReader;
import nc.liat6.data.reader.bean.Source;
import nc.liat6.data.util.IOHelper;
import nc.liat6.data.util.ReaderHelper;

/**
 * txt文件读取
 * 
 * @author 6tail
 *
 */
public class TextReader extends AbstractReader{
  private BufferedReader reader;

  public TextReader(Source source){
    super(source);
  }

  public void load() throws IOException{
    switch(source.getSourceType()){
      case file:
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(source.getFile()),ReaderHelper.getCharset(source.getFile())));
        break;
      case inputStream:
        reader = new BufferedReader(new InputStreamReader(source.getInputStream(),ReaderHelper.getCharset(source.getInputStream())));
        break;
    }
    stop = false;
  }

  public List<String> nextLine(){
    String line = null;
    try{
      line = reader.readLine();
    }catch(IOException e){
      IOHelper.closeQuietly(reader);
    }
    if(null==line) return null;
    List<String> cols = new ArrayList<String>();
    cols.add(line);
    return cols;
  }
}