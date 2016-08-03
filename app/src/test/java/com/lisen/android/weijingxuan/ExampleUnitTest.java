package com.lisen.android.weijingxuan;

import com.lisen.android.weijingxuan.util.HttpUtils;
import com.lisen.android.weijingxuan.util.MyUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testStringToDate() throws Exception {
        Date date1 = MyUtils.stringToDate("2016-08-01 14:00:22");
        Date date2 = MyUtils.stringToDate("2016-08-01 13:28:06");
        boolean result = date1.before(date2);
        System.out.println("result = " + result);
    }
    @Test
    public void testList() throws Exception {
        List<String> list1 = new ArrayList<>();
        List<String > list2 = new ArrayList<>();
        list1.add("0");
        list1.add("1");
        System.out.println("list1: " + list1.toString());
        list2.add("3");
        list2.add("4");
        list2.addAll( list1);
        System.out.println("list2: " + list2.toString());

    }
}