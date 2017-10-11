package nc.liat6.data.reader.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nc.liat6.data.reader.AbstractReader;
import nc.liat6.data.reader.bean.Source;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

/**
 * doc文件读取
 * <p>由于poi原生解析不支持单元格colspan，所以使用变通办法：将doc转为html后解析html表格。</p>
 * @author 6tail
 *
 */
public class DocReader extends AbstractReader{
  /** doc文档 */
  protected HWPFDocument doc;
  protected Elements trs;
  protected int rowCount;
  /**列数*/
  protected int colCount;
  protected int rowReaded;
  protected Map<Integer,Integer> rowspans = new HashMap<Integer,Integer>();

  public DocReader(Source source){
    super(source);
  }

  public void load() throws IOException{
    switch(source.getSourceType()){
      case file:
        doc = new HWPFDocument(new FileInputStream(source.getFile()));
        break;
      case inputStream:
        doc = new HWPFDocument(source.getInputStream());
        break;
    }
    stop = false;
    rowCount = 0;
    colCount = 0;
    rowReaded = 0;
    rowspans.clear();
    try{
      WordToHtmlConverter converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      converter.processDocument(doc);
      Document htmlDoc = converter.getDocument();
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      DOMSource domSource = new DOMSource(htmlDoc);
      StreamResult streamResult = new StreamResult(outStream);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer serializer = tf.newTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
      serializer.setOutputProperty(OutputKeys.INDENT,"no");
      serializer.setOutputProperty(OutputKeys.METHOD,"html");
      serializer.transform(domSource,streamResult);
      outStream.close();
      String content = new String(outStream.toString("utf-8"));
      org.jsoup.nodes.Document document = Jsoup.parse(content);
      Elements tables = document.getElementsByTag("table");
      if(tables.size()>0){
        trs = tables.get(0).getElementsByTag("tr");
        rowCount = trs.size();
      }
    }catch(IOException e){
      throw e;
    }catch(Exception e){
      throw new IOException(e);
    }
  }

  public List<String> nextLine(){
    if(stop) return null;
    if(rowReaded>=rowCount) return null;
    Element tr = trs.get(rowReaded);
    rowReaded++;
    List<String> line = new ArrayList<String>();
    Elements tds = tr.children();
    if(rowReaded==1){
      colCount = tds.size();
    }
    int colIndex = 0;
    for(int i=0;i<colCount;i++){
      Integer restRow = rowspans.get(i);
      if(null==restRow||restRow<1){
        Element n = tds.get(colIndex++);
        line.add(n.text().trim());
        int rowspan = 0;
        int colspan = 0;
        String attrRowspan = n.attr("rowspan");
        if(attrRowspan.length()>0){
          rowspan = Integer.parseInt(attrRowspan)-1;
          rowspans.put(i,rowspan);
        }
        String attrColspan = n.attr("colspan");
        if(attrColspan.length()>0){
          colspan = Integer.parseInt(attrColspan)-1;
          if(rowReaded==1){
            colCount += colspan;
          }
          for(int x=0;x<colspan;x++){
            rowspans.put(i+x+1,rowspan+1);
          }
        }
      }else{
        restRow--;
        rowspans.put(i,restRow);
        line.add("");
      }
    }
    return line;
  }
}