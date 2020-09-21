package org.mics.lang.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author mics
 * @date 2015-4-13
 * @version 1.0
 */
public class FileUtils {
	
	  /**
     * 扫描所有文件并放入datas中
     * @param file 根文件夹
     * @param datas  根文件夹下所有的文件
     */
    public static void searchDirctory(File file,List<File> datas){
    	if(file.isDirectory() ){
    			File[] files = file.listFiles();
    			for(File temp : files){
    				searchDirctory(temp,datas);
    			}
    	}else{
    		datas.add(file);
    	}
    }
    /**
     * 通配符
     * @author mics
     * @date 2019年6月19日
     * @version 1.0
     */
    public static List<String> pattern(String rootName){
    	List<String> result =  new ArrayList<String>();
    	String fileTypeName = rootName.replace(".", "/");
    	String leftName= fileTypeName.substring(0, fileTypeName.indexOf("**")-1);
    	String rightName= fileTypeName.substring(fileTypeName.indexOf("**")+3, fileTypeName.length());
    	File file = new File(Thread.currentThread().getContextClassLoader().getResource(leftName).getPath());
    	if(file.isDirectory() ){
			File[] files = file.listFiles();
			for(File temp : files){
				if(temp.isDirectory()){ //通配
					File[] files2 = temp.listFiles();
					for(File f:files2){
						if(f.isDirectory() && f.getName().equals(rightName)){
							String ss = leftName+"/"+temp.getName()+"/"+rightName;
							ss = ss.replace("/", ".");
							result.add(ss);
						}
					}
				}
			}
    	}
		return result;
    }
    
}
