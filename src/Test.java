import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Test
{
  public static void main(String[] args)
    throws IOException, InputFormatException, EncoderException
  {
    System.out.println("请输入配置文件路径,例如file.txt");
    Scanner scan = new Scanner(System.in);
    String filePath = scan.nextLine();
    System.out.println("读取配置文件：" + filePath);

    File file = new File(filePath);
    if (!(file.exists())) {
      System.out.println("无法找到配置文件" + filePath);
      return;
    }
    FileReader reader = new FileReader(file);
    BufferedReader breader = new BufferedReader(reader);
    String s;
    while ((s = breader.readLine()) != null) {
      String sourcePath = s;
      String targetPath = s.substring(0, s.indexOf(".")) + ".mp4";
      if (!(new File(sourcePath).exists())) {
        System.out.println("--------------不存在的文件" + sourcePath);
      }
      else if (new File(targetPath).exists()) {
        System.out.println("--------------已经存在文件" + targetPath);
      }
      else
      {
        System.out.print("转换" + sourcePath + "为" + targetPath);
        long start = System.currentTimeMillis();
        changeToMp4(sourcePath, targetPath);
        System.out.print("(" + (System.currentTimeMillis() - start) + "ms)");
        System.out.println();
      }
    }
  }

  public static void changeToMp4(String sourcePath, String targetPath)
    throws InputFormatException, EncoderException
  {
    File source = new File(sourcePath);

    File target = new File(targetPath);
    try
    {
      VideoAttributes video = new VideoAttributes();
      video.setCodec("mpeg4");
      video.setBitRate(Integer.valueOf(160000));
      video.setFrameRate(new Integer(16));
      video.setSize(new VideoSize(320, 240));
      EncodingAttributes attrs = new EncodingAttributes();
      attrs.setFormat("mp4");
      attrs.setVideoAttributes(video);
      Encoder encoder = new Encoder();
      encoder.encode(source, target, attrs);

      encoder.encode(source, target, attrs);
    } catch (Exception e) {
      if (target.exists()) {
        target.delete();
      }
      e.printStackTrace();
    }
  }
}