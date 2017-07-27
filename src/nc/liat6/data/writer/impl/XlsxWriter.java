package nc.liat6.data.writer.impl;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import nc.liat6.data.parser.bean.Item;
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

  public void writeLine(List<Item> line) throws IOException{
    if(stop){
      return;
    }
    if(null==line){
      stop();
      return;
    }
    XSSFRow row = sheet.createRow(count++);
    for(int i=0,j=line.size();i<j;i++){
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
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet();
  }

}