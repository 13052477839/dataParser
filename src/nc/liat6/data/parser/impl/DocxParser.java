package nc.liat6.data.parser.impl;

import nc.liat6.data.parser.AbstractParser;
import nc.liat6.data.reader.bean.Source;
import nc.liat6.data.reader.impl.DocxReader;

/**
 * docx格式的解析器
 * 
 * @author 6tail
 *
 */
public class DocxParser extends AbstractParser{
  /** 支持的文件后缀 */
  public static final String SUPPORT_FILE_SUFFIX = ".docx";

  public DocxParser(Source source){
    super(new DocxReader(source),SUPPORT_FILE_SUFFIX);
    name = "docx";
  }
}