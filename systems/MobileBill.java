
public class MobileBill extends Bill {

	private double mins;
	
	public MobileBill(double mins) {

		super(mins*500);
		billNo =  "MOBIL#021" + getTotalBills();
		this.mins = mins;
	}
	
	@Override
	String getDetails() {
		// TODO Auto-generated method stub
		return "The no. of minutes talked is " + Math.round(mins);
	}
	
	
	

}
