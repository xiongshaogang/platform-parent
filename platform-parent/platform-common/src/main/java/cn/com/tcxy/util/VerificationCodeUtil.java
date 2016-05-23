package cn.com.tcxy.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerificationCodeUtil {
    private final static Logger log = LoggerFactory
            .getLogger(VerificationCodeUtil.class);

    private static Color[] BACKGROUND = new Color[] { new Color(132, 132, 132) };

    private static Color[] WORDS = new Color[] { new Color(255, 255, 255) };

    private static Color[] LINE = WORDS;

    public static void generateSecurityImage(OutputStream os,
            boolean needConfuse, String sRand) throws IOException {
        // 在内存中创建图象
        int width = 4 * 20, height = 25;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 生成随机类
        // Random random = new Random();

        // 设定背景色
        g.setColor(getRandColor(BACKGROUND));
        g.fillRect(0, 0, width, height + 10);

        // 设定字体
        g.setFont(new Font("Arial", Font.PLAIN, 18));

        // 画边框
        g.setColor(Color.white);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生条干扰线，使图象中的认证码不易被其它程序探测到
        Random random = new Random();
        if (needConfuse) {
            g.setColor(getRandColor(LINE));
            for (int i = 0; i < 2; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, xl, yl);
            }
        }

        char[] chars = sRand.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            // 将认证码显示到图象中
            int drawY = random.nextInt(6);
            g.setColor(getRandColor(WORDS));
            g.drawString(String.valueOf(chars[i]), 20 * i + 5, 14 + drawY);
        }
        ImageIO.write(image, "JPEG", os);
    }

    private static Color getRandColor(Color[] colorRanges) {
        int j = (int) (Math.random() * colorRanges.length);
        return colorRanges[j];
    }
}
