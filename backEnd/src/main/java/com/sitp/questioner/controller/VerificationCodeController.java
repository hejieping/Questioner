package com.sitp.questioner.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.jaq.model.v20161123.AfsCheckRequest;
import com.aliyuncs.jaq.model.v20161123.AfsCheckResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qi on 2017/10/29.
 */
@RestController
@RequestMapping("/verificationCode")
public class VerificationCodeController {
    private static IAcsClient client = null;
    static {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIcBJZtnRFweBE...", "ve5XQLVVXL67dbKhbBRjjfnkz5hRuI...");
        client = new DefaultAcsClient(profile);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Jaq", "jaq.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResJsonTemplate verificationCode(@RequestParam("sessionId") String sessionId,
                                            @RequestParam("sig") String sig,
                                            @RequestParam("token") String token,
                                            @RequestParam("scene") String scene) {
        AfsCheckRequest request = new AfsCheckRequest();
        request.setPlatform(3);//必填参数，请求来源： 1：Android端； 2：iOS端； 3：PC端及其他
        request.setSession(sessionId);// 必填参数，从前端获取，不可更改
        request.setSig(sig);// 必填参数，从前端获取，不可更改
        request.setToken(token);// 必填参数，从前端获取，不可更改
        request.setScene(scene);// 必填参数，从前端获取，不可更改
        try {
            AfsCheckResponse response = client.getAcsResponse(request);
            if(response.getErrorCode() == 0 && response.getData()) {
                System.out.println("验签通过");
                return new ResJsonTemplate<>("200", true);
            } else {
                System.out.println("验签失败");
                return new ResJsonTemplate<>("200", false);
            }
            // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResJsonTemplate<>("200", false);
    }
}
