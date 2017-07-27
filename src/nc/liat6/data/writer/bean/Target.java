package nc.liat6.data.writer.bean;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 数据目标
 * 
 * @author 6tail
 *
 */
public class Target{
  /** 数据目标类型 */
  private TargetType targetType;
  /** 文件 */
  private File file;
  /** 输出流 */
  private OutputStream outputStream;
  
  public Target(){}
  
  public Target(File file){
    setFile(file);
  }
  
  public Target(OutputStream outputStream) throws IOException{
    setOutputStream(outputStream);
  }

  public TargetType getTargetType(){
    return targetType;
  }

  public void setTargetType(TargetType targetType){
    this.targetType = targetType;
  }

  public File getFile(){
    return file;
  }

  public void setFile(File file){
    this.file = file;
    setTargetType(TargetType.file);
  }

  public OutputStream getOutputStream(){
    return outputStream;
  }

  public void setOutputStream(OutputStream outputStream) throws IOException{
    this.outputStream = outputStream;
    setTargetType(TargetType.outputStream);
  }
  
  public String toString(){
    switch(targetType){
      case file:
        return file.getAbsolutePath();
      case outputStream:
        return "outputStream target";
    }
    return "null target";
  }
}