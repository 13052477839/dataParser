package nc.liat6.data.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import nc.liat6.data.reader.bean.InputStreamCache;

public class ReaderHelper{
  public static final String CHARSET_UTF8 = "utf-8";
  public static final String CHARSET_GBK = "gbk";
  /** BOM头 */
  public static Map<String,byte[]> BOM = new HashMap<String,byte[]>();
  static{
    BOM.put(CHARSET_UTF8,new byte[]{(byte)0xEF,(byte)0xBB,(byte)0xBF});
  };
  
  public static String getCharset(InputStream inputStream) throws IOException{
    InputStreamCache cache = new InputStreamCache(inputStream);
    Map<String,Integer> length = new HashMap<String,Integer>();
    for(Entry<String,byte[]> entry:BOM.entrySet()){
      String charset = entry.getKey();
      String bom = new String(entry.getValue(),charset);
      String s = readAsText(cache.getInputStream(),charset);
      if(s.startsWith(bom)){
        return charset;
      }
      length.put(charset,s.length());
    }
    if(!length.containsKey(CHARSET_UTF8)){
      length.put(CHARSET_UTF8,readAsText(cache.getInputStream(),CHARSET_UTF8).length());
    }
    if(!length.containsKey(CHARSET_GBK)){
      length.put(CHARSET_GBK,readAsText(cache.getInputStream(),CHARSET_GBK).length());
    }
    return length.get(CHARSET_UTF8)<length.get(CHARSET_GBK)?CHARSET_UTF8:CHARSET_GBK;
  }

  public static String getCharset(File file) throws IOException{
    return getCharset(new FileInputStream(file));
  }

  protected static String readAsText(File file,String charset) throws IOException{
    return readAsText(new FileInputStream(file),charset);
  }

  protected static String readAsText(InputStream inputStream,String charset) throws IOException{
    StringBuilder s = new StringBuilder();
    BufferedReader br = null;
    try{
      br = new BufferedReader(new InputStreamReader(inputStream,charset));
      String line = null;
      while(null!=(line = br.readLine())){
        s.append(line);
        s.append("\r\n");
      }
    }finally{
      IOHelper.closeQuietly(br);
    }
    return s.toString();
  }
}