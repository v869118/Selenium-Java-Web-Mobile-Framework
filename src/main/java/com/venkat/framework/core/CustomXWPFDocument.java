package com.venkat.framework.core;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
//import org.apache.xmlbeans.XmlToken.Factory;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

public class CustomXWPFDocument  extends XWPFDocument
{
  public CustomXWPFDocument(InputStream in)
    throws IOException
  {
    super(in);
  }
  
  public void createPicture(String blipId, int id, int width, int height)
  {
    //int EMU = 9525;
    width *= 9525;
    height *= 9525;
    
    CTInline inline = createParagraph().createRun().getCTR().addNewDrawing().addNewInline();
    
    String picXml = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">         <pic:nvPicPr>            <pic:cNvPr id=\"" + 
    
      id + "\" name=\"Generated\"/>" + 
      "            <pic:cNvPicPr/>" + 
      "         </pic:nvPicPr>" + 
      "         <pic:blipFill>" + 
      "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" + 
      "            <a:stretch>" + 
      "               <a:fillRect/>" + 
      "            </a:stretch>" + 
      "         </pic:blipFill>" + 
      "         <pic:spPr>" + 
      "            <a:xfrm>" + 
      "               <a:off x=\"0\" y=\"0\"/>" + 
      "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" + 
      "            </a:xfrm>" + 
      "            <a:prstGeom prst=\"rect\">" + 
      "               <a:avLst/>" + 
      "            </a:prstGeom>" + 
      "         </pic:spPr>" + 
      "      </pic:pic>" + 
      "   </a:graphicData>" + 
      "</a:graphic>";
    
    XmlToken xmlToken = null;
    try
    {
      xmlToken = XmlToken.Factory.parse(picXml);
    }
    catch (XmlException xe)
    {
      xe.printStackTrace();
    }
    inline.set(xmlToken);
    
    inline.setDistT(0L);
    inline.setDistB(0L);
    inline.setDistL(0L);
    inline.setDistR(0L);
    
    CTPositiveSize2D extent = inline.addNewExtent();
    extent.setCx(width);
    extent.setCy(height);
    
    CTNonVisualDrawingProps docPr = inline.addNewDocPr();
    docPr.setId(id);
    docPr.setName("Picture " + id);
    docPr.setDescr("Generated");
  }
}
