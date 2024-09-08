package com.xlw;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

/**
 * AgentMain
 *
 * @author Cotton Eye Joe
 * @since  2024/9/8 20:33
 */
public class AgentMain {
    public static void premain(String args, Instrumentation instrumentation) throws IOException {
        NormalClassFileTransformer transformer = new NormalClassFileTransformer();
        instrumentation.addTransformer(transformer);
//        NormalClassFileTransformer.readClassFile("D:\\JavaWork\\example");
        System.out.println("\n");
        System.out.println("Agent Start successfully");
    }

}
