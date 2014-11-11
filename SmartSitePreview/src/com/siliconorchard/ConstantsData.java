package com.siliconorchard;

public class ConstantsData {

	final public static String patternfordownload=".epub|.apk|.zip|.rar|.pdf|.ppt|.pptx|.doc|.docx|.xls|.7z|.rtf|.txt|.iso|.tar|.mar|.cpio";

	final public static String[] patternfordownloadarray={".epub",".apk",".zip",".rar",".pdf",".ppt",".pptx",".doc",".docx",".xls",".7z",".rtf",".txt",".iso",".tar",".mar",".cpio"};

	
	public static boolean isdownloadable(String s)
	{
		for (String suffix : patternfordownloadarray) {
			
			if(s.endsWith(suffix))
				return true;
		}
		
		return false;
	}
}
