package nc.liat6.data.reader.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.data.reader.AbstractReader;
import nc.liat6.data.reader.bean.Source;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

/**
 * docx文件读取
 * 
 * @author 6tail
 *
 */
public class DocxReader extends AbstractReader{
  /** docx文档 */
  private XWPFDocument doc;
  private XWPFTable table;
  /** 当前行，从0开始计 */
  private int rowCount = 0;
  /** 它认为的最后一行，从0开始计，如果为-1则不以它为准 */
  private int lastRow = -1;

  public DocxReader(Source source){
    super(source);
  }

  public void load() throws IOException{
    switch(source.getSourceType()){
      case file:
        doc = new XWPFDocument(new FileInputStream(source.getFile()));
        break;
      case inputStream:
        doc = new XWPFDocument(source.getInputStream());
        break;
    }
    List<XWPFTable> tables = doc.getTables();
    if(tables.size()>0){
      table = tables.get(0);
      lastRow = table.getRowBandSize()-1;
    }else{
      table = null;
      lastRow = -1;
    }
    stop = false;
    rowCount = 0;
  }

  private List<String> getLine(int index){
    if(lastRow>-1){
      if(index>lastRow){
        return null;
      }
    }
    XWPFTableRow row = table.getRow(index);
    if(null==row){
      if(-1==lastRow){
        return null;
      }else{
        return new ArrayList<String>();
      }
    }
    List<XWPFTableCell> cells = row.getTableCells();
    List<String> rs = new ArrayList<String>();
    for(XWPFTableCell cell:cells){
      String v = "";
      int colspan = 0;
      if(null!=cell){
        v = (cell.getText()+"").trim();
        CTTcPr cpr = cell.getCTTc().getTcPr();
        CTDecimalNumber span = cpr.getGridSpan();
        if(null!=span){
          colspan = span.getVal().intValue()-1;
        }
      }
      rs.add(v);
      for(int i=0;i<colspan;i++){
        rs.add("");
      }
    }
    return rs;
  }

  public List<String> nextLine(){
    if(stop) return null;
    return getLine(rowCount++);
  }
}