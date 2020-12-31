package com.zetool.beancopy;


import com.zetool.beancopy.bean.BeanCloner;
import com.zetool.beancopy.field.checkor.Checker;
import com.zetool.beancopy.field.cloner.Copier;
import com.zetool.beancopy.io.FlexibleClassScanner;
import com.zetool.beancopy.field.FieldHelper;
import com.zetool.beancopy.javabean.*;
import com.zetool.beancopy.util.Log;
import com.zetool.beancopy.util.TimerInterval;
import org.springframework.beans.BeanUtils;

import java.util.LinkedList;
import java.util.List;

public class App2 {
	public static void main(String[] args) {
//		Log.setLEVEL(Log.ERROR);
//		checkTest();
//
//		checkMultCopy();
//
//
//		baseTypeCopyTest();
//
//		simpleCopyTest();
//
//		copyTest();
//
//		multCopyTest();
//
//		underLineToHumpTest();
//
//		HumpToUnderLineTest();
//
//		exceptTest();

		speedTest();
	}

	public static void speedTest(){
		int n = 1000000;
		Speed speed = new Speed();
		List<Speed> speeds = new LinkedList<>();
		TimerInterval timerInterval = TimerInterval.start();
		for (int i = 0; i < n; ++i) {
//			Speed temp = Copier.copy(speed);
			BeanUtils.copyProperties(speed, speed);
//			BeanCloner.cloneSurfaceProperties(speed, speed);
		}
		Log.error(App2.class, "花费时间mm：" + (timerInterval.intervalMMTime()));
	}
	
	/**
	 * 测试 except 选项
	 */
	public static void exceptTest() {
		Default a = new Default();
		Except a2 = Copier.copy(a, Except.class);
		Log.info(App2.class, "拷贝后的a2为:" + FieldHelper.toString(a2));
	}
	
	/**
	 * 测试  驼峰映射对 下划线
	 */
	public static void HumpToUnderLineTest() {
		UnderLineToHump a = new UnderLineToHump();
		HumpToUnderLine a2 = Copier.copy(a, HumpToUnderLine.class);
		Log.info(App2.class, "拷贝后的a2为:" + FieldHelper.toString(a2));
	}
	
	/**
	 * 测试 下划线对 驼峰映射
	 */
	public static void underLineToHumpTest() {
		Default a = new Default();
		UnderLineToHump a2 = Copier.copy(a, UnderLineToHump.class);
		Log.info(App2.class, "拷贝后的a2为:" + FieldHelper.toString(a2));
	}
	
	public static void checkTest() {
		Checker.check(
						new FlexibleClassScanner().addPackageClassesByClassName(A.class.getName())
				);
	}
	
	/**
	 * 检查允许多个CopyFrom注解
	 */
	public static void checkMultCopy(){
		Checker.check(
						new FlexibleClassScanner().addPackageClassesByClassName(A.class.getName())
				);
	}

	/**
	 * 运行多个注解情况下的拷贝
	 */
	public static void baseTypeCopyTest(){
		Integer[] arr = {Integer.valueOf(1), Integer.valueOf(2)};
		Integer[] arr2 = Copier.copy(arr);
		Log.info(App2.class, "拷贝后的A为:" + FieldHelper.toString(arr2));
	}
	
	/**
	 * 运行多个注解情况下的拷贝
	 */
	public static void simpleCopyTest(){
		A a = new A();
		A a2 = Copier.copy(a);
//		a.list.add("new");// 测试通过没有修改
//		a.multArray[1][1][1] = 333;// 修改a，测试是否会导致a2也修改。。。 测试通过没有修改，证明完全克隆
//		a.listArray[1].add(3333);// 测试通过没有修改
//		Log.info(App2.class, "拷贝后的A为:" + FieldHelper.toString(a2));
	}
	
	/**
	 * 运行多个注解情况下的拷贝
	 */
	public static void multCopyTest(){
		A a = new A();
		B b = Copier.copy(a, B.class);
		Log.info(App2.class, "拷贝后的B为:" + FieldHelper.toString(b));
		C c = Copier.copy(a,  C.class);
		Log.info(App2.class, "拷贝后的C为:" + FieldHelper.toString(c));
	}
	
	public static void copyTest() {
		A a = new A();
		B b = Copier.copy(a, B.class);
		a.list.add("123");
		Log.info(App2.class, "拷贝后的B为:" + FieldHelper.toString(b));
	}
	

}
