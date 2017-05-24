
public class ElectricityBill extends Bill {

	private double meterReading;
	
	public ElectricityBill(double meterReading) {
		super(meterReading*750.5);
		this.meterReading = meterReading;
		
		billNo =  "KElec#123" + getTotalBills();
	}
	
	@Override
	String getDetails() {
		// TODO Auto-generated method stub
		return "The meter reading of this electricity bill is " + Math.round(meterReading);
	}


}
