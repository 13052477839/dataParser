package nc.liat6.data.writer.impl;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nc.liat6.data.parser.bean.BlockType;
import nc.liat6.data.parser.bean.Item;
import nc.liat6.data.wrapper.rule.IWrapperRule;
import nc.liat6.data.writer.AbstractWriter;
import nc.liat6.data.writer.bean.Target;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * XLSX导出
 * 
 * @author 6tail
 * 
 */
public class XlsxWriter extends AbstractWriter implements Closeable{
  private OutputStream outputStream;
  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private int count;
  private Set<Integer> columnWidths = new HashSet<Integer>();

  public XlsxWriter(Target target){
    super(target);
  }

  public void close(){
    if(null!=workbook&&null!=outputStream){
      try{
        workbook.write(outputStream);
        outputStream.flush();
      }catch(IOException e){
      }
    }
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
      return;
    }
    Integer rowHeight = IWrapperRule.MODE_DEFAULT;
    switch(blockType){
      case head:
        rowHeight = rule.getHeadRowHeights().get(lineIndex+"");
        if(null==rowHeight){
          rowHeight = rule.getDefaultHeadRowHeight();
        }
        break;
      case body:
        rowHeight = rule.getBodyRowHeights().get(lineIndex+"");
        if(null==rowHeight){
          rowHeight = rule.getDefaultBodyRowHeight();
        }
        break;
      default:
    }
    XSSFRow row = sheet.createRow(count++);
    switch(rowHeight){
      case IWrapperRule.MODE_AUTO:
        break;
      case IWrapperRule.MODE_DEFAULT:
        break;
      default:
        if(rowHeight>0){
          row.setHeight((short)(rowHeight*20));
        }
        break;
    }
    for(int i=0,j=line.size();i<j;i++){
      if(!columnWidths.contains(i)){
        Integer colWidth = rule.getColumnWidths().get(i+"");
        if(null==colWidth){
          colWidth = rule.getDefaultColumnWidth();
        }
        switch(colWidth){
          case IWrapperRule.MODE_AUTO:
            sheet.autoSizeColumn(i,true);
            break;
          case IWrapperRule.MODE_DEFAULT:
            break;
          default:
            if(colWidth>0){
              sheet.setColumnWidth(i,colWidth*256);
            }
            break;
        }
        columnWidths.add(i);
      }
      Item item = line.get(i);
      if(null==item) continue;
      String o = item.getContent();
      if(null==o) continue;
      XSSFCell cell = row.createCell(i);
      switch(item.getType()){
        case number:
          cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
          try{
            cell.setCellValue(Double.parseDouble(o));
          }catch(Exception e){
            cell.setCellValue(o);
          }
          break;
        default:
          cell.setCellType(XSSFCell.CELL_TYPE_STRING);
          cell.setCellValue(o);
          break;
      }
    }
  }

  public void load() throws IOException{
    switch(target.getTargetType()){
      case file:
        outputStream = new FileOutputStream(target.getFile());
        break;
      case outputStream:
        outputStream = target.getOutputStream();
        break;
    }
    count = 0;
    columnWidths.clear();
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet();
  }

}