package immigrants.people;

import java.security.cert.CertificateNotYetValidException;

import immigrants.exceptions.ImmigrantException;
import immigrants.exceptions.OutOfMoneyException;
import immigrants.exceptions.PassportException;
import immigrants.interfaces.Migrateable;
import immigrants.other.Address;
import immigrants.other.City;
import immigrants.other.Passport;

public abstract class Immigrant extends Citizen implements Migrateable {

	private boolean hasPassport;
	private Address currentAddress;
	private Immigrant[] relatives;

	public Immigrant(int money, Address address) throws OutOfMoneyException, ImmigrantException {
		this.setMoney(money);

		if (address != null) {
			this.currentAddress = address;
		} else {
			throw new ImmigrantException("No current address!");
		}

	}

	public Immigrant(Passport passport) throws PassportException {
		setPassport(passport);
	}

	public void addRelatives(Immigrant[] relatives) throws ImmigrantException {
		if (relatives != null) {
			this.relatives = relatives.clone();
		} else {
			throw new ImmigrantException("No relatives to add!");
		}
	}

	@Override
	public void migrate(City city) {
		
		//don't know how to solve infinite recursion;
		
		if (city != null) {
			this.currentAddress = null;
			
			for (int index = 0; index < this.relatives.length; index++) {
				this.relatives[index].migrate(city);
			}
			
			return;
		}
	}

	@Override
	public boolean equals(Object other) {
		return this.getPassport().getName().equals(((Immigrant) other).getPassport().getName())
				&& this.getMoney() == ((Immigrant) other).getMoney();
	}

	public boolean isHasPassport() {
		return hasPassport;
	}

	public void setHasPassport(boolean hasPassport) {
		this.hasPassport = hasPassport;
	}

	public Immigrant[] getRelatives() {
		return relatives;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void showInfo() {

		System.out.println("Current city: " + this.currentAddress.getCity());
		System.out.println("Has passport? " + this.hasPassport);
		System.out.println("Money: " + this.getMoney());
		System.out.println("Relatives: ");

		for (int index = 0; index < this.relatives.length; index++) {
			if (relatives[index] != null) {
				System.out.println(relatives[index]);
			}
		}
	}

	@Override
	public String toString() {
		if (getPassport() != null) {
			return getPassport().getName();
		}
		return "Unknown";
	}

}
