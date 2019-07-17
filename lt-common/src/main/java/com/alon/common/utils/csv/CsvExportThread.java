package com.alon.common.utils.csv;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * CSV导出之大量数据-导出压缩包
 * eg:http://blog.csdn.net/soconglin/article/details/6297534
 */
public class CsvExportThread extends Thread {

    public static Queue<Object> queue;
    static {
        queue = new ConcurrentLinkedQueue<Object>();
    }
    private static boolean isRunning = false;
    private ServletContext context = null;
    public CsvExportThread() {
    }

    public CsvExportThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        if (!isRunning) {
            isRunning = true;
            System.out.println("开始执行查询并放入queue队列");
            try {
                // 设置表格头
                Object[] head = {"小说名称","作者"};
                String[] propertys = new String[]{"id","name"};
                List<Test> testList = new ArrayList<Test>();
                Test test = null;
                test = new Test();
                test.setId(1);
                test.setName("123");
                testList.add(test);
                test = new Test();
                test.setId(2);
                test.setName("1234");
                testList.add(test);
                download(head,propertys,testList,testList.size(),10000,"123test","/tmp");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println("查询并放入queue队列结束");
            ThreadPools.createThreadPool();//唤起线程池
            isRunning = false;
        } else {
            System.out.println("上一次任务执行还未结束");
        }
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
    public  static <T> String download(Object[] head,String[] row,List<T> data,Integer listCount,Integer pageSize,String name,String downloadPath){

        //循环次数
        int quotient = listCount/pageSize+(listCount%pageSize > 0 ? 1:0);
        //设置标头
        List<Object> headList = Arrays.asList(head);
        List<File> srcfile=new ArrayList<File>();
        String downloadFilePath = "";
        for(int i=0;i<quotient;i++){
            List<T> list = new ArrayList<T>();
            int startCount = ((i> 0 ? i:0)*pageSize);
            if((listCount%pageSize)>0){
                if(i==(quotient-1)){
                    //余数
                    pageSize = (int)(listCount%pageSize);
                }
            }

            double num = Math.ceil(data.size() / pageSize);
            //每次开始读取的下标
            int n =i*pageSize;
            if(i+1<num){
                //i+1 代表 （下一页）
                list = data.subList(n, n+pageSize);
            }else if(i+1 == num){
                //当位于最后一页时，计算要读多少个数（ aa.size()-i*50）
                list =  data.subList(n, n+data.size()-i*pageSize);
            }

            queue.offer(list);
            List<List<Object>> dataList = CsvExportBatch.getNovel(startCount, pageSize,row,list);
            downloadFilePath = downloadPath.concat("download/").concat("download/");
            File desc = new File(downloadFilePath);
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
            // 导出文件名称
            String  fileName = String.valueOf(i);
            // 导出CSV文件
            File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
            srcfile.add(csvFile);
            System.out.println(startCount+"----------------"+pageSize);
        }
        ZipUtil.zipFiles(srcfile, new File(downloadPath.concat("download/").concat(name).concat(".zip")));
        ZipUtil.dropFolderOrFile(new File(downloadFilePath));
        return downloadFilePath;
    }

    public static void main(String[] args) throws IOException {
      new CsvExportThread().start();
    }
}
