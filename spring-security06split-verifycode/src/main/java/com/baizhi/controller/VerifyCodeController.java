package com.baizhi.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class VerifyCodeController {
    private final Producer producer;

    @Autowired
    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }

    @RequestMapping("/getVerifyCodeImage")
    public String getVerifyCodeImage(HttpSession session) throws IOException {
        // 生成验证码
        String verifyCode = producer.createText();
        // 保存到session中，或者redis中
        session.setAttribute("kaptcha", verifyCode);
        // 生成图片
        BufferedImage verifyCodeImage = producer.createImage(verifyCode);
        // 将图片转base64，并响应
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(verifyCodeImage, "jpg", fos);
        return Base64Utils.encodeToString(fos.toByteArray());
    }
}
