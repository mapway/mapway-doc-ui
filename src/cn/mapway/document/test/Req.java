package cn.mapway.document.test;

import java.util.List;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc(value = "触发条件", desc = "<p>触发条件分为两类:设备属性条件 和定时器触发条件，"
	    + "<p>定时器(虚拟设备,deviceId=0)有两个属性 1000为定时属性 1001为倒计时属性，"
	    + "<p>定时属性 value值为一个具体的时间格式为2016-05-18 13:24:03,"
	    + "<p>倒计时属性 value值为一个为一个用/隔开的字符串 其格式为 [时间/间隔值/循环的单位 ] ,"
	    + "<p>举一个例子 [ 2016-05-18 13:24:03/3/D] 表示 从 2016-05-18 13:24:03开始 每隔3天 的 13:24:03 秒执行动作,"
	    + "<p>再一个例子 [ 2016-05-18 13:24:03/2/H] 表示 从 2016-05-18 13:24:03开始 每隔2小时 的 24:03 秒执行动作,"
	    + "<p>再一个例子 [ 2016-05-18 13:24:03/30/M] 表示从 2016-05-18 13:24:03开始 每隔30分钟   03 秒执行动作."
	    + "<p>格式中的取值范围[A/B/C] A 表示一个个发的时间点其格式2016-05-18 13:24:03 C取值有三种 D天 H小时 M分钟,"
	    + "<p>当C为 D为天的时候 B取值为一个正整数  当C为 H时 B取值为[1-24],"
	    + "<p>当C为 M为天的时候 [1-59]."
	    
	    )
public class Req {

	@ApiField("int field")
	public int f1;

	@ApiField("booelan field")
	public Boolean booleaField;

	@ApiField("List test")
	public List<String> names;

	@ApiField("RET数组")
	public List<Ret> rets;

	@ApiField("TestObject in api Field")
	public TestObj testObj;

}
