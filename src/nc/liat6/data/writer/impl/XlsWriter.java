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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * XLS导出
 * 
 * @author 6tail
 * 
 */
public class XlsWriter extends AbstractWriter implements Closeable{
  private OutputStream outputStream;
  private HSSFWorkbook workbook;
  private HSSFSheet sheet;
  private int count;
  private Set<Integer> columnWidths = new HashSet<Integer>();
  private HSSFCellStyle cellStyle;

  public XlsWriter(Target target){
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
    HSSFRow row = sheet.createRow(count++);
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
      HSSFCell cell = row.createCell(i);
      cell.setCellStyle(cellStyle);
      switch(item.getType()){
        case number:
          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
          try{
            cell.setCellValue(Double.parseDouble(o));
          }catch(Exception e){
            cell.setCellValue(o);
          }
          break;
        default:
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
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
    workbook = new HSSFWorkbook();
    sheet = workbook.createSheet();
    cellStyle = workbook.createCellStyle();
    int border = rule.getDefaultBorder();
    switch(border){
      case IWrapperRule.BORDER_NONE:
        break;
      case IWrapperRule.BORDER_ALL:
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        break;
    }
  }

}