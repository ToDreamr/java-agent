package com.xlw;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 * NormalClassFileTransformer
 *
 * @author Cotton Eye Joe
 * @time 2024/9/8 20:44
 */
public class NormalClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.println("className:"+className);
//        System.out.println(protectionDomain);
//        System.out.println(loader);
//        System.out.println(Arrays.toString(classfileBuffer));
        if (!className.equalsIgnoreCase("com/pray/Dog")){
            return null;
        }
        try {
            return getBytesFromTargetClassFile("D:/JavaWork/example/target/classes/com/pray/Dog.class");
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getBytesFromTargetClassFile(String filePath) {
        File file = new File(filePath);
        try (InputStream ins = Files.newInputStream(file.toPath())) {
            //preCondition
            long fileLength = file.length();
            //读取字符
            byte[] bytes = new byte[(int) fileLength];
            int offset = 0;
            int len = 0;
            while (offset < bytes.length && (len = ins.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += len;
            }
            if (offset < bytes.length) {
                throw new IOException("读取文件失败" + file.getName());
            }
            ins.close();
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static void readClassFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            for (File files: Objects.requireNonNull(file.listFiles())){
                readFile(files);
            }
        }
        readFile(file);
    }

    private static void readFile(File file) throws IOException {
        try (InputStream ins = Files.newInputStream(file.toPath())) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(ins);

            BufferedReader reader = new BufferedReader(new InputStreamReader(ins,Charset.forName("GB2312")));
            StringBuilder result = new StringBuilder();
            while (reader.ready()){
                result.append(reader.readLine());
                result.append("\n");
            }
            PrintStream printStream = new PrintStream(System.out);
            printStream.println(result);
        }catch (AccessDeniedException exception){
            System.err.println("访问权限不足");
        }
    }

    public static void main(String[] args) throws IOException {
        NormalClassFileTransformer.readClassFile("D:\\JavaWork\\example");
    }
}
