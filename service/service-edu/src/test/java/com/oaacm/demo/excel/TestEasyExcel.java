package com.oaacm.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //设置写入文件夹地址、文件名称
        String filename = "D:\\Workspace\\IdeaWorkspace\\write_excel\\write.xlsx";

        EasyExcel.write(filename, DataWriter.class).sheet("学生列表").doWrite(getData());

    }

    private static List<DataWriter> getData() {
        List<DataWriter> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataWriter data = new DataWriter();
            data.setSname("lucy" + i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }
}
