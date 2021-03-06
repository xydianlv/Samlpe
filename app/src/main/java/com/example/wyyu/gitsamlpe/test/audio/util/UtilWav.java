package com.example.wyyu.gitsamlpe.test.audio.util;

import com.example.wyyu.gitsamlpe.test.audio.recorder.RecorderParameter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by wyyu on 2018/1/9.
 * Wav 文件操作类
 **/

class UtilWav {

    // 将一组 PCM 文件转为 WAV 文件，传入 PCM 的本地地址和待转换的 WAV文件 地址
    static Boolean mergePcmListToWav(List<String> pathList, String pathWav) {

        return true;
    }

    // 将指定 PCM 文件转为 WAV 文件，传入 PCM 的本地地址和待转换的 WAV文件 地址
    static Boolean turnPcmToWav(String path, String pathWav) {

        boolean turnStatus;

        try {
            FileOutputStream writer = new FileOutputStream(new File(pathWav), false);
            FileInputStream reader = new FileInputStream(new File(path));

            WavHeader wavHeader = new WavHeader(new RecorderParameter(), reader.available());
            writer.write(wavHeader.getWavHeader());

            byte[] funValue = new byte[reader.available()];
            reader.read(funValue);

            writer = new FileOutputStream(new File(pathWav), true);
            writer.write(funValue);

            turnStatus = true;
        } catch (IOException e) {
            turnStatus = false;
            e.printStackTrace();
        }

        return turnStatus;
    }

    private static class WavHeader {

        private final char[] FINAL_CHARS_R = { 'R', 'I', 'F', 'F' };
        private final char[] FINAL_CHARS_W = { 'W', 'A', 'V', 'E' };
        private final char[] FINAL_CHARS_F = { 'f', 'm', 't', ' ' };
        private final char[] FINAL_CHARS_D = { 'd', 'a', 't', 'a' };

        private static final int FORMAT = 1;
        private static final int SIZE = 16;

        private byte[] wavHeader;

        // 由录制音频的基本信息和录制音频大小构造 WAV 文件头
        WavHeader(RecorderParameter recorderParameter, int dataLength) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writChar(outputStream, FINAL_CHARS_R);
            writeInt(outputStream, dataLength);
            writChar(outputStream, FINAL_CHARS_W);
            writChar(outputStream, FINAL_CHARS_F);
            writeInt(outputStream, SIZE);
            writeShort(outputStream, FORMAT);
            writeShort(outputStream, recorderParameter.getChannel());
            writeInt(outputStream, recorderParameter.getSampleRate());
            writeInt(outputStream, recorderParameter.getBytePerSecond());
            writeShort(outputStream, recorderParameter.getBlockAlign());
            writeShort(outputStream, recorderParameter.getBitPerSample());
            writChar(outputStream, FINAL_CHARS_D);
            writeInt(outputStream, dataLength);
            outputStream.flush();
            wavHeader = outputStream.toByteArray();
            outputStream.close();
        }

        private void writeShort(ByteArrayOutputStream outputStream, int value) throws IOException {
            byte[] bytes = new byte[2];

            bytes[1] = (byte) ((value >> 16) & 0xff);
            bytes[0] = (byte) ((value >> 24) & 0xff);

            outputStream.write(bytes);
        }

        private void writeInt(ByteArrayOutputStream outputStream, int value) throws IOException {
            byte[] bytes = new byte[4];

            bytes[3] = (byte) (value & 0xff);
            bytes[2] = (byte) ((value >> 8) & 0xff);
            bytes[1] = (byte) ((value >> 16) & 0xff);
            bytes[0] = (byte) ((value >> 24) & 0xff);

            outputStream.write(bytes);
        }

        private void writChar(ByteArrayOutputStream outputStream, char[] value) {
            for (char data : value) {
                outputStream.write(data);
            }
        }

        byte[] getWavHeader() {
            return wavHeader;
        }
    }
}
