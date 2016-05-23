package cn.com.tcxy.payment;


/**
 * 产品类型 目前系统只有两种产品
 * 
 */
public enum GoodsType {
	xyb, // 小云币
	sp;// 服务包

	public static GoodsType getTypeFromOrderNo(String orderId) {
		for (GoodsType d : GoodsType.values()) {
			if (orderId.startsWith(d.name())) {
				return d;
			}
		}
		return sp;
	}
}
