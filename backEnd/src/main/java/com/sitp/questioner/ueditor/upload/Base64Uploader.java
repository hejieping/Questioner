package com.sitp.questioner.ueditor.upload;

import com.sitp.questioner.ueditor.ConfigManager;
import com.sitp.questioner.ueditor.PathFormat;
import com.sitp.questioner.ueditor.define.AppInfo;
import com.sitp.questioner.ueditor.define.BaseState;
import com.sitp.questioner.ueditor.define.FileType;
import com.sitp.questioner.ueditor.define.State;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader {

	public static State save(HttpServletRequest request, Map<String, Object> conf) {
	    String filedName = (String) conf.get("fieldName");
		String fileName = request.getParameter(filedName);
		byte[] data = decode(fileName);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String rootPath = ConfigManager.getRootPath(request,conf);
		String physicalPath = rootPath + savePath;

		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}