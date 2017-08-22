package com.crab.mybatis.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileUtil {

	public static String getFileName(String header) {
		String[] tempArr1 = header.split(";");
		String[] tempArr2 = tempArr1[2].split("=");
		// 获取文件名，兼容各种浏览器的写法
		return tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");

	}
	
	/**
	 * 读取所有文件片, 如name---0-12313，并按升序返回
	 * 0-表示第x片，123132-表示每一片大小
	 * @param path
	 * @param fileName
	 * @param breakpoint
	 * @return
	 */
	public static List<File> getAllFileName(String path, String fileName, String breakpoint) {
		Map<Integer, File> resultMap = new TreeMap<>();
		File dir = new File(path);
		for (File f : dir.listFiles()) {
			String name = f.getName();
			if (f.isFile() && name.startsWith(fileName) && name.contains(breakpoint)) {
				String[] pos = name.substring(name.indexOf(breakpoint) + 3).split("-");
				if (pos.length > 0) {
					resultMap.put(Integer.parseInt(pos[0]), f);
				}
			}
		}
		if (resultMap.size() > 0) {
			// result.stream().sorted(Comparator.comparing(File::getName));
			return new ArrayList<>(resultMap.values());
		}
		return Collections.emptyList();
	}

	public static boolean write(InputStream inputStream, File f) {
		boolean ret = false;

		try (OutputStream outputStream = new FileOutputStream(f)) {
			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			ret = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

}
