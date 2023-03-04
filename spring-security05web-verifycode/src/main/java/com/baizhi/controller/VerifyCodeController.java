package com.baizhi.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class VerifyCodeController {
    private final Producer producer;

    @Autowired
    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }

    @RequestMapping("verifyCodeImage")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        // 生成验证码
        String verifyCode = producer.createText();
        // 保存到session中
        session.setAttribute("kaptcha", verifyCode);
        // 生成图片
        BufferedImage image = producer.createImage(verifyCode);
        // 响应图片
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
    }
}
