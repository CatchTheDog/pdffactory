package com.majq.pdffactory;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Mr.x
 * @since 2018/11/17 13:22
 * <em>将PDF文件转换为MOBI文件</em>
 * <strong>因为亚马逊电子书的限制，使用第三方程序生成的电子书不能调整字体大小等</strong>
 * 所以采用java直接调用bat脚本的方式
 */
public class PdfToMobiUtils {
	/**
	 * 命令行空格
	 */
	private final String WHITE_SPACE = " ";
	/**
	 * KCC python脚本存放路径
	 */
	private final String KCC_EXE_PATH = System.getProperty("user.dir") + File.separator + "kcc";
	/**
	 * 调用KCC 脚本存放位置
	 */
	private final String BAT_SCRIPT_PATH = System.getProperty("user.dir") + File.separator + "call_kcc.bat";
	/**
	 * 源文件存放路径（生成的mobi文件与源文件存放于同一目录下）
	 */
	private final String srcPath;

	public PdfToMobiUtils(String srcPath) {
		this.srcPath = srcPath;
		File srcFile = new File(srcPath);
		if (!srcFile.isFile() || !srcFile.exists()) {
			throw new IllegalArgumentException("srcPath can't be null!");
		}
	}

	/**
	 * 测试
	 *
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		PdfToMobiUtils utils = new PdfToMobiUtils("C:\\Users\\HP\\Desktop\\Java核心技术卷1基础知识原书第10版_7.pdf");
		utils.generateMobi();
	}

	/**
	 * 将PDF文件转换为Mobi文件
	 * 使用调用bat脚本的方式实现
	 */
	public void generateMobi() throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(BAT_SCRIPT_PATH + WHITE_SPACE + KCC_EXE_PATH + WHITE_SPACE + this.srcPath);
		process.waitFor();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				System.out.println(str);
			}
		}
	}
}