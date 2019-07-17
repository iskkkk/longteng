package com.alon.common.utils.csv;

import com.alon.common.config.LtConfigParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CsvExportBatch
 * @Description  CSV导出之大量数据-导出压缩包
 * @Author zoujiulong
 * @Date 2019/7/3 14:28
 * @Version 1.0
 **/
public class CsvExportBatch{

    @Autowired
    private static LtConfigParams params;

    /**
     * 方法表述: 数据处理
     * @Author zoujiulong
     * @Date 15:09 2019/7/3
     * @param       startCount
     * @param       pagesize
     * @param       propertys
     * @param       list
     * @return java.util.List<java.util.List<java.lang.Object>>
     */
    public static <T> List<List<Object>> getNovel(int startCount, int pagesize,String[] propertys,List<T> list) {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        try {
//            for (int i = 0; i < pagesize; i++) {
//                int endCount = startCount+i;
            for(Object obj : list){
                rowList = new ArrayList<Object>();
                Field[] fields = obj.getClass().getDeclaredFields();
                for(String property : propertys){
                    for(Field field : fields){
                        //设置字段可见性
                        field.setAccessible(true);
                        if(property.equals(field.getName())){
                            if (null == field.get(obj)) {
                                rowList.add("");
                            } else {
                                rowList.add(field.get(obj).toString());
                            }
                        }
                    }
                }
                dataList.add(rowList);
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }


    /**
     * 方法表述: 下载
     * @Author zoujiulong
     * @Date 15:07 2019/7/3
     * @param       head
     * @param       row
     * @param       data
     * @param       listCount 数据总数
     * @param       pageSize 设置每一个excel文件导出的数量
     * @return void
     */
    public static <T> String download(Object[] head,String[] row,List<T> data,Integer listCount,Integer pageSize,String name) {
        OutputStream out = null;
        //设置标头
        List<Object> headList = Arrays.asList(head);
        int quotient = listCount/pageSize+(listCount%pageSize > 0 ? 1:0);
        List<File> srcfile=new ArrayList<File>();
        String downloadFilePath = "";
        for(int i=0;i<quotient;i++){
            int startCount = ((i> 0 ? i:0)*pageSize);
            if((listCount%pageSize)>0){
                if(i==(quotient-1)){
                    //余数
                    pageSize = (int)(listCount%pageSize);
                }
            }

            List<T> filterData = new ArrayList<>();
            double num = Math.ceil(data.size() / pageSize);
            //每次开始读取的下标
            int n =i*pageSize;
            if(i+1<num){
                //i+1 代表 （下一页）
                filterData = data.subList(n, n+pageSize);
            }else if(i+1==num){
                //当位于最后一页时，计算要读多少个数（ aa.size()-i*50）
                filterData =  data.subList(n, n+data.size()-i*pageSize);
            }

            List<List<Object>> dataList = getNovel(startCount, pageSize,row,filterData);
            // 导出文件路径
//            downloadFilePath = "D:" + File.separator + "swan" + File.separator + "download"+File.separator;
            downloadFilePath = params.download.concat("download/");
            File desc = new File(downloadFilePath);
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
            // 导出文件名称
            String  fileName = String.valueOf(i);
            // 导出CSV文件
            File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
            srcfile.add(csvFile);
        }
        ZipUtil.zipFiles(srcfile, new File(params.download.concat(name).concat(".zip")));
        ZipUtil.dropFolderOrFile(new File(downloadFilePath));
        return downloadFilePath;
    }

}
