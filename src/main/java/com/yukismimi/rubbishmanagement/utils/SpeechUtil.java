package com.yukismimi.rubbishmanagement.utils;

import java.io.*;
import java.util.HashMap;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;

public class SpeechUtil {

    public static final String APP_ID = "18005055";
    public static final String API_KEY = "0Iq7um4hhrtgK4wpTRlY2zSr";
    public static final String SECRET_KEY = "QqFbIafn77Cxx20KDTjFG01qiKQmzXFb";
    private static AipSpeech client;
    private JSONObject asrRes;

    private SpeechUtil() {
        client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
//        client.setHttpProxy("10.167.196.133", 8080);
    }

    public static SpeechUtil getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static final SpeechUtil instance = new SpeechUtil();
    }

    public String asrLocalSpeech(String path) throws UnsupportedAudioFileException, IOException {
        // 对本地语音文件进行识别
        if (StringUtils.isNotEmpty(path)) {
            String targetPath = path.replace("mp3", "wav");
            byteToWav(getBytes(path), targetPath);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("dev_pid", 1536);
            String newFilePath = path.replace(".", "-") + ".wav";
            File f = new File(path);
            InputStream inputStream = new FileInputStream(f);
            BufferedInputStream zipTest = new BufferedInputStream(inputStream);
            AudioInputStream inStream = AudioSystem.getAudioInputStream(zipTest);
            AudioFormat outDataFormat = new AudioFormat(16000.0f, 16, 1, true, false);
            AudioInputStream lowResAIS = AudioSystem.getAudioInputStream(outDataFormat, inStream);
            AudioSystem.write(lowResAIS, AudioFileFormat.Type.WAVE, new File(newFilePath));
            asrRes = client.asr(newFilePath, "wav", 16000, map);
        }
        return asrRes.toString();
    }

    public boolean byteToWav(byte[] sourceBytes, String targetPath) {
        if (sourceBytes == null || sourceBytes.length == 0) {
            System.out.println("Illegal Argument passed to this method");
            return false;
        }

        try (final ByteArrayInputStream bais = new ByteArrayInputStream(sourceBytes); final AudioInputStream sourceAIS = AudioSystem.getAudioInputStream(bais)) {
            AudioFormat sourceFormat = sourceAIS.getFormat();
            // 设置MP3的语音格式,并设置16bit
            AudioFormat mp3tFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
            // 设置百度语音识别的音频格式
            AudioFormat pcmFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 16000, 16, 1, 2, 16000, false);
            try (
                    // 先通过MP3转一次，使音频流能的格式完整
                    final AudioInputStream mp3AIS = AudioSystem.getAudioInputStream(mp3tFormat, sourceAIS);
                    // 转成百度需要的流
                    final AudioInputStream pcmAIS = AudioSystem.getAudioInputStream(pcmFormat, mp3AIS)) {
                // 根据路径生成wav文件
                AudioSystem.write(pcmAIS, AudioFileFormat.Type.WAVE, new File(targetPath));
            }
            return true;
        } catch (IOException e) {
            System.out.println("文件转换异常：" + e.getMessage());
            return false;
        } catch (UnsupportedAudioFileException e) {
            System.out.println("文件转换异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 将文件转成字节流
     *
     * @param filePath
     * @return
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
