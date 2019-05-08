package com.hangyi.eyunda.util;
/**
 * 平安银行 b2bic系统 密码生成
 */
import java.io.Console;
import java.io.PrintStream;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesReader
{
  private static byte[] pwd = "Pingan01".getBytes();
  private static final String ALGORITHM = "DES";
  private static final String PADDING = "/ECB/PKCS5Padding";
  private static final byte equalSign = 61;
  
  public String read(String src)
  {
    if (src == null) {
      return null;
    }
    try
    {
      byte[] dbase64 = decode(src.getBytes());
      byte[] bsrc = decrypt(dbase64);
      return new String(bsrc);
    }
    catch (Exception e)
    {
      throw new RuntimeException("加密字段值非法:" + src, e);
    }
  }
  
  public String write(String src)
  {
    if (src == null) {
      return null;
    }
    try
    {
      byte[] bencry = encrypt(src.getBytes());
      byte[] ebase64 = encode(bencry);
      return new String(ebase64);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  private static byte[] encrypt(byte[] data)
    throws Exception
  {
    DESKeySpec desKeySpec = new DESKeySpec(pwd);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey deskey = keyFactory.generateSecret(desKeySpec);
    Cipher c = Cipher.getInstance(deskey.getAlgorithm() + "/ECB/PKCS5Padding");
    c.init(1, deskey);
    byte[] bRet = c.doFinal(data);
    return bRet;
  }
  
  private static byte[] decrypt(byte[] data)
    throws Exception
  {
    DESKeySpec desKeySpec = new DESKeySpec(pwd);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey deskey = keyFactory.generateSecret(desKeySpec);
    Cipher c = Cipher.getInstance(deskey.getAlgorithm() + "/ECB/PKCS5Padding");
    c.init(2, deskey);
    byte[] bRet = c.doFinal(data);
    return bRet;
  }
  
  static char[] digits = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
    'K', 'L', 'M', 
    'N', 
    'O', 
    'P', 
    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 
    'd', 
    'e', 
    'f', 
    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
    't', 'u', 
    'v', 
    'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', 
    '9', '+', '/' };
  
  private static byte[] decode(byte[] data)
  {
    if (data.length == 0) {
      return data;
    }
    int lastRealDataIndex = data.length - 1;
    while (data[lastRealDataIndex] == 61) {
      lastRealDataIndex--;
    }
    int padBytes = data.length - 1 - lastRealDataIndex;
    int byteLength = data.length * 6 / 8 - padBytes;
    byte[] result = new byte[byteLength];
    
    int dataIndex = 0;
    int resultIndex = 0;
    int allBits = 0;
    
    int resultChunks = (lastRealDataIndex + 1) / 4;
    for (int i = 0; i < resultChunks; i++)
    {
      allBits = 0;
      for (int j = 0; j < 4; j++) {
        allBits = allBits << 6 | decodeDigit(data[(dataIndex++)]);
      }
      for (int j = resultIndex + 2; j >= resultIndex; j--)
      {
        result[j] = ((byte)(allBits & 0xFF));
        allBits >>>= 8;
      }
      resultIndex += 3;
    }
    switch (padBytes)
    {
    case 1: 
      allBits = 0;
      for (int j = 0; j < 3; j++) {
        allBits = allBits << 6 | decodeDigit(data[(dataIndex++)]);
      }
      allBits <<= 6;
      
      allBits >>>= 8;
      for (int j = resultIndex + 1; j >= resultIndex; j--)
      {
        result[j] = ((byte)(allBits & 0xFF));
        
        allBits >>>= 8;
      }
      break;
    case 2: 
      allBits = 0;
      for (int j = 0; j < 2; j++) {
        allBits = allBits << 6 | decodeDigit(data[(dataIndex++)]);
      }
      allBits <<= 6;
      allBits <<= 6;
      
      allBits >>>= 8;
      allBits >>>= 8;
      result[resultIndex] = ((byte)(allBits & 0xFF));
    }
    return result;
  }
  
  private static int decodeDigit(byte data)
  {
    char charData = (char)data;
    if ((charData <= 'Z') && (charData >= 'A')) {
      return charData - 'A';
    }
    if ((charData <= 'z') && (charData >= 'a')) {
      return charData - 'a' + 26;
    }
    if ((charData <= '9') && (charData >= '0')) {
      return charData - '0' + 52;
    }
    switch (charData)
    {
    case '+': 
      return 62;
    case '/': 
      return 63;
    }
    throw new IllegalArgumentException(
      "Invalid char to decode: " + data);
  }
  
  private static byte[] encode(byte[] data)
  {
    int sourceChunks = data.length / 3;
    int len = (data.length + 2) / 3 * 4;
    byte[] result = new byte[len];
    int extraBytes = data.length - sourceChunks * 3;
    
    int dataIndex = 0;
    int resultIndex = 0;
    int allBits = 0;
    for (int i = 0; i < sourceChunks; i++)
    {
      allBits = 0;
      for (int j = 0; j < 3; j++) {
        allBits = allBits << 8 | data[(dataIndex++)] & 0xFF;
      }
      for (int j = resultIndex + 3; j >= resultIndex; j--)
      {
        result[j] = ((byte)digits[(allBits & 0x3F)]);
        
        allBits >>>= 6;
      }
      resultIndex += 4;
    }
    switch (extraBytes)
    {
    case 1: 
      allBits = data[(dataIndex++)];
      allBits <<= 8;
      allBits <<= 8;
      for (int j = resultIndex + 3; j >= resultIndex; j--)
      {
        result[j] = ((byte)digits[(allBits & 0x3F)]);
        
        allBits >>>= 6;
      }
      result[(result.length - 1)] = 61;
      result[(result.length - 2)] = 61;
      break;
    case 2: 
      allBits = data[(dataIndex++)];
      allBits = allBits << 8 | data[(dataIndex++)] & 0xFF;
      
      allBits <<= 8;
      for (int j = resultIndex + 3; j >= resultIndex; j--)
      {
        result[j] = ((byte)digits[(allBits & 0x3F)]);
        
        allBits >>>= 6;
      }
      result[(result.length - 1)] = 61;
    }
    return result;
  }
  
  public static void main(String[] args)
  {
	  	String passwd = "12345678";
        System.out.println(new DesReader().write(passwd));
    
  }
}
