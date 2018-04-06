package com.venkat.framework.core;

public class ExcelCellFormatting
{
  private String fontName;
  private short fontSize;
  private short backColorIndex;
  private short foreColorIndex;
  
  public String getFontName()
  {
    return this.fontName;
  }
  
  public void setFontName(String fontName)
  {
    this.fontName = fontName;
  }
  
  public short getFontSize()
  {
    return this.fontSize;
  }
  
  public void setFontSize(short fontSize)
  {
    this.fontSize = fontSize;
  }
  
  public short getBackColorIndex()
  {
    return this.backColorIndex;
  }
  
  public void setBackColorIndex(short backColorIndex)
  {
    if ((backColorIndex < 8) || (backColorIndex > 64)) {
      throw new FrameworkException("Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
    }
    this.backColorIndex = backColorIndex;
  }
  
  public short getForeColorIndex()
  {
    return this.foreColorIndex;
  }
  
  public void setForeColorIndex(short foreColorIndex)
  {
    if ((foreColorIndex < 8) || (foreColorIndex > 64)) {
      throw new FrameworkException("Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
    }
    this.foreColorIndex = foreColorIndex;
  }
  
  public boolean bold = false;
  public boolean italics = false;
  public boolean centred = false;
}
