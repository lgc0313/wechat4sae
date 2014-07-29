package org.gcli.caipiao;

import java.util.ArrayList;
import org.gcli.url.Currencyrate;

public class GetLotteryByURL {
	private String fc_3d = "http://www.17500.cn/3d/index.php";
	private String pl3 = "http://www.17500.cn/p3/";

	public GetLotteryByURL() {

	}

	public Lottery getLottery(String url) {
		Lottery fc = null;
		ArrayList<String> rt = Currencyrate.getHtmlList(url);
		String str = rt.get(0);
		String[] strarray = str.split("\n");
		String expect =  strarray[1];
		String opencode = strarray[3];
		String opentime = strarray[2];
		fc = new Lottery();
		fc.setExpect(expect.trim());
		fc.setOpencode(opencode.trim());
		fc.setOpentime(opentime.trim());
		return fc;

	}

	public Lottery[] getLotterys() {
		Lottery[] lottey = new Lottery[2];
		lottey[0] = this.getLottery(fc_3d);
		lottey[1] = this.getLottery(pl3);
		return lottey;
	}


}
