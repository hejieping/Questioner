package com.sitp.questioner.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * Created by qi on 2017/10/13.
 */
public class AvatarUtil {
    private static String ROOT_PATH ;
    private static String AVATAR_PATH ;
    private static String ACCESS_PREFIX; // access prefix by network
    static {
        try{
            ROOT_PATH = URLDecoder.decode(ClassUtils.getDefaultClassLoader().getResource("static")
            .getPath(), "utf-8");
            AVATAR_PATH = ROOT_PATH + File.separator + "img" + File.separator + "avatar";
            ACCESS_PREFIX = "/img/avatar/";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param avatar
     * @return the path to access the avatar
     */
    public static String saveAvatar(MultipartFile avatar){
        String originFileName = avatar.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf('.'));
        String pureFileName = originFileName.substring(0, originFileName.lastIndexOf('.'));
        String uuid = UUID.randomUUID().toString().replace("-","");
        String savedAvatarName = pureFileName + "_" + uuid + suffix ;
        String savePath = AVATAR_PATH + File.separator + savedAvatarName ;
        File avatarFile = new File(savePath);
        if(avatarFile.exists()){
            avatarFile.delete();
        }
        if(!avatarFile.getParentFile().exists()){
            avatarFile.getParentFile().mkdir();
        }
        try{
            avatar.transferTo(avatarFile);
        }
        catch (IOException e){
            System.out.println("保存头像失败！");
            e.printStackTrace();
        }
        return ACCESS_PREFIX + savedAvatarName;
    }

    public static void deleteAvatar(String avatarAccessPath, String serverUrl){
        String avatarPrefix = serverUrl + ACCESS_PREFIX ;
        String realAvatarName = avatarAccessPath.substring(avatarPrefix.length());
        String realAvatarPath = AVATAR_PATH + File.separator + realAvatarName ;
        File avatar = new File(realAvatarPath);
        if(avatar.exists()){
            avatar.delete();
        }
    }


}
