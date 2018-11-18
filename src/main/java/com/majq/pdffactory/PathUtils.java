package com.majq.pdffactory;

import java.io.File;

/**
 * @author Mr.X
 * @version 1.0.0
 * <strong>文件路径处理工具</strong>
 * @since 2018/11/16 17:06
 */
public class PathUtils {
	/**
	 * 从文件路径中获取文件名称
	 *
	 * @param filePath 文件路径
	 * @return 文件名称
	 */
	public static String getFileName(String filePath) {
		if (null == filePath || filePath.length() == 0)
			return null;
		if (filePath.indexOf(File.separator) > -1)
			return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf("."));
		else
			return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
	}
}
