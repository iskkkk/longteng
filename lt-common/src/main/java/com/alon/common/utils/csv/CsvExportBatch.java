package com.alon.common.utils.csv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * CSV导出之大量数据-导出压缩包
 * 
 */
public class CsvExportBatch{

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        // 设置表格头
        Object[] head = {"序号","小说名称","作者","出版日期"};
        List<Object> headList = Arrays.asList(head);
        // 设置数据
        int listCount = 16510000;
        //导出6万以上数据。。。
        int pageSize= 50000;//设置每一个excel文件导出的数量
        int quotient = listCount/pageSize+(listCount%pageSize > 0 ? 1:0);//循环次数
        List<File> srcfile=new ArrayList<File>();
        for(int i=0;i<quotient;i++){
            int startCount = ((i> 0 ? i:0)*pageSize);
            if((listCount%pageSize)>0){
                if(i==(quotient-1)){
                    pageSize = (int)(listCount%pageSize);//余数
                }
            }
            List<List<Object>> dataList = getNovel(startCount, pageSize);
            // 导出文件路径
            String downloadFilePath = "C:" + File.separator + "cap4j" + File.separator + "download"+File.separator;
            // 导出文件名称
            String  fileName = String.valueOf(i);
            // 导出CSV文件
            File csvFile = CSVUtils.createCSVFile(headList, dataList, downloadFilePath, fileName);
            srcfile.add(csvFile);
        }
        ZipUtil.zipFiles(srcfile, new File("C:\\cap4j\\download.zip"));
        ZipUtil.dropFolderOrFile(new File("C:\\cap4j\\download"));
        long endTime = System.currentTimeMillis();
        System.out.println("分批CSV导出"+(endTime-startTime));
    }

    private static List<List<Object>> getNovel(int startCount,int pagesize) {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for (int i = 0; i < pagesize; i++) {
            rowList = new ArrayList<Object>();
            Object[] row = new Object[4];
            int endCount = startCount+i;
            row[0] = endCount;
            row[1] = "风云第一刀"+endCount+"";
            row[2] = "古龙"+endCount+"";
            row[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            for(int j=0;j<row.length;j++){
                rowList.add(row[j]);
            }
            dataList.add(rowList);
        }
        return dataList;
    }

    public static void insert(Connection conn) {
        // 开始时间
        Long begin = System.currentTimeMillis();
        // sql前缀
        String prefix = "INSERT INTO coupon_receive_record ( `serial_no`, `activity_no`, `third_activity_no`, `netflow_id`, `netflow_serial_no`, `netflow_name`, `netflow_type`, `merchant_id`, `merchant_name`, `brand_merchant_id`, `brand_merchant_name`, `ad_space_id`, `province`, `city`, `district`, `detail`, `card_id`, `coupon_id`, `coupon_title`, `coupon_code`, `valid_start_time`, `valid_end_time`, `merchant_user`, `netflow_user`, `current_user`, `status`, `receive_time`, `create_time`, `modifed_time`, `coupon_status`, `activity_name`, `use_range`, `ad_place`, `validity`) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 100; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                for (int j = 1; j <= 1000; j++) {
                    // 构建SQL后缀
                    String b = "'"+Math.random()*100+"'";
                    String a = "("+j+",'123456','654321','"+j+"','023154852','乘车码','"+j+"','"+j+"','永辉','"+j+"','伊利','"+j+"','广东','深圳',NULL,NULL,'"+j+"','1','乘车福利中心','TX123456','2019-06-20 19:42:27','2019-06-30 19:42:29','M1','N1','SWAN1','1','2019-06-20 19:43:06','2019-06-20 19:43:10','2019-06-20 19:43:24','2','888立减','2','乘车码顶部','2019-07-03 21:19:01')";
                    suffix.append(a);
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = System.currentTimeMillis();
        // 耗时
        System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }
}
