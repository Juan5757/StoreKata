package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) {
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public float total() {
		float totalItems = 0;
		for (OrderItem item : items) {
			float totalItem=0;
			float itemAmount = calculateAmountOfItem(item);
			if (isAccessory(item)) {
				float booksDiscount = 0;
				if (itemAmountIsMajorOrEqualToOneHundred(itemAmount)) {
					booksDiscount = applyTenPercentDiscountToBooks(itemAmount);
				}
				totalItem = valueTotalOfItem(itemAmount, booksDiscount);
			}
			if (isBike(item)) {
				totalItem = applyTwentyPercentDiscountToBikes(itemAmount);
			}
			if (isCloathing(item)) {
				float cloathingDiscount = 0;
				if (quantityOfItemMajorOfTwo(item)) {
					cloathingDiscount = item.getProduct().getUnitPrice();
				}
				totalItem = valueTotalOfItem(itemAmount, cloathingDiscount);
			}
			totalItems += totalItem;
		}

		if (countryIsUSA()){
			return totalItems + totalItems * 5 / 100;
		}
		return totalItems + totalItems * 5 / 100 + 15;
	}

	private boolean countryIsUSA() {
		return this.deliveryCountry == "USA";
	}

	private boolean quantityOfItemMajorOfTwo(OrderItem item) {
		return item.getQuantity() > 2;
	}

	private boolean isCloathing(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Cloathing;
	}

	private float applyTwentyPercentDiscountToBikes(float itemAmount) {
		float totalItem;
		totalItem = itemAmount - itemAmount * 20 / 100;
		return totalItem;
	}

	private boolean isBike(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private float valueTotalOfItem(float itemAmount, float booksDiscount) {
		float totalItem;
		totalItem = itemAmount - booksDiscount;
		return totalItem;
	}

	private float applyTenPercentDiscountToBooks(float itemAmount) {
		float booksDiscount;
		booksDiscount = itemAmount * 10 / 100;
		return booksDiscount;
	}

	private boolean itemAmountIsMajorOrEqualToOneHundred(float itemAmount) {
		return itemAmount >= 100;
	}

	private boolean isAccessory(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}

	private float calculateAmountOfItem(OrderItem item) {
		float itemAmount = item.getProduct().getUnitPrice() * item.getQuantity();
		return itemAmount;
	}
}
