package org.gcli.course.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.gcli.caipiao.GetLotteryByURL;
import org.gcli.caipiao.Lottery;
import org.gcli.course.message.resp.TextMessage;
import org.gcli.course.util.MessageUtil;
import org.gcli.robot.Chat4XiaoI;
import org.gcli.weather.WeatherSevice;

/**
 * 核心服务类
 * 
 * @author gcli
 */
public class CoreService {
	// private static Logger log = LoggerFactory.getLogger(CoreService.class);
	private static Logger log = Logger.getLogger(CoreService.class.getName());

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		TextMessage textMessage = null;
		try {
			// xml请求解析
			log.debug("CoreService---start");
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 默认回复此文本消息
			textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			StringBuffer contentMsg = new StringBuffer();
			contentMsg.append("您好，我是机器人，请回复数字选择服务：").append("\n\n");
			contentMsg.append("1  彩票开奖信息").append("\n");
			contentMsg.append("2  天气信息").append("\n");
			contentMsg.append("3  聊天唠嗑").append("\n");

			textMessage.setContent(contentMsg.toString());
			// 将文本消息对象转换成xml字符串
			// 文本消息
			if (msgType.equals("text")) {

				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				if ("?".equals(content)||"？".equals(content)) {

				} else if ("1".equals(content) || "彩票".equals(content)) {
					contentMsg = new StringBuffer();
					GetLotteryByURL cp = new GetLotteryByURL();
					Lottery[] fc = cp.getLotterys();
					contentMsg
							.append("第" + fc[0].getExpect() + "期3D开奖结果："
									+ fc[0].getOpencode() + "。开奖时间："
									+ fc[0].getOpentime() + "。")
							.append("\n")
							.append("第" + fc[1].getExpect() + "期排列3开奖结果："
									+ fc[1].getOpencode() + "。开奖时间："
									+ fc[1].getOpentime() + "。");

				} else if ("2".equals(content) || "天气".equals(content)) {
					contentMsg = WeatherSevice.getWeather("大连");

				} else if ("3".equals(content)) {
					contentMsg = new StringBuffer();
					contentMsg
					.append("聊天唠嗑使用说明")
					.append("\n").append("闲来无聊，找我唠嗑吧。我很能聊的，有问必答！例如：")
					.append("\n").append("讲个笑话")
					.append("\n").append("大连有什么好玩的")
					.append("\n").append("特价机票");

				}else {
					contentMsg = new StringBuffer();
					contentMsg.append(Chat4XiaoI.Tess(content, "crazylee"));

				}

			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					contentMsg = new StringBuffer();
					contentMsg
							.append("亲，你咋才来呢，我想你了。我能为您提供天气，彩票，公交，聊天，笑话等服务，是您生活娱乐好帮手！")
							.append("\n").append("回复\"？\"显示主菜单");
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
			}
			textMessage.setContent(contentMsg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		respMessage = MessageUtil.textMessageToXml(textMessage);
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}