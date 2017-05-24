
public class WaterBill extends Bill {
	private double waterAmount;
	
	public WaterBill(double waterAmount) {
		super(waterAmount*1234.57);
		billNo =  "WATER#246" + getTotalBills();
		this.waterAmount = waterAmount;
	}
	@Override
	String getDetails() {
		// TODO Auto-generated method stub
		return "The water consumed in total is " + Math.round(waterAmount) + " litres";
	}

}
