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
			log.debug("fromUserName:" + fromUserName);
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

			textMessage.setContent(contentMsg.toString());
			// 将文本消息对象转换成xml字符串
			// 文本消息
			if (msgType.equals("text")) {

				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				if ("1".equals(content) || "彩票".equals(content)) {
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

					textMessage.setContent(contentMsg.toString());

				} else if ("2".equals(content) || "天气".equals(content)) {
					contentMsg = WeatherSevice.getWeather("大连");
					textMessage.setContent(contentMsg.toString());

				} else {
					String str = Chat4XiaoI.Tess(content, "crazylee");
					textMessage.setContent(str);

				}

			}
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