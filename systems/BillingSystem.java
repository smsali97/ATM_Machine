
public class BillingSystem {
	static int[] w = {3,7,9,12,15,17,29,22,25,0};
	static int[] m = {2,6,10,13,18,28,24,1,16,19};
	//int[] e = new int[10];

	public static Bill[] bills = new Bill[30];

	public static Bill[] getBills() {
		
		
		
		
		for (int i = 0; i < bills.length; i++) {
			boolean flag = false;
			double random = Math.random()*100;
			for (int water: w) {
				if (water==i) {
					flag = true;
					bills[i] = new WaterBill(random);
				}
			}
			for (int mobile: m) {
				if (mobile==i) {
					flag = true;
					bills[i] = new MobileBill(random);
				}
			}
			if (!flag) {
				bills[i] = new ElectricityBill(random);
			}
			
		}
		return bills;
	}

}
