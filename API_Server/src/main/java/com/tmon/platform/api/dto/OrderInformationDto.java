package com.tmon.platform.api.dto;

/**
 * OrderInformationDto
 * 
 * @author 구도원
 * 
 *         TABLE JOIN
 * 
 *         ORDER_INFORMATION inner join RESERVATION_ORDER, PRODUCT 
 *         PRODUCT inner join CATEGORY
 * 
 *         o.reservation_id, 
 *         o.product_id, 
 *         o.quantity,
 * 
 *         [o.reservation_id = r.reservation_id] 
 *         r.reservation_date,
 *         r.cancel_date, 
 *         r.user_id, 
 *         r.status_id, 
 *         r.timeslot_id,
 * 
 *         [o.product_id = p.product_id] 
 *         p.product_name,
 * 
 *         [p.category_id = c.category_id] 
 *         c.category_name
 *
 */
public class OrderInformationDto {

	private int reservation_id;
	private int product_id;
	private int quantity;
	private String reservation_date;
	private String cancel_date;
	private String user_id;
	private int status_id;
	private int timeslot_id;
	private String product_name;
	private String category_name;

	public int getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getReservation_date() {
		return reservation_date;
	}

	public void setReservation_date(String reservation_date) {
		this.reservation_date = reservation_date;
	}

	public String getCancel_date() {
		return cancel_date;
	}

	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getTimeslot_id() {
		return timeslot_id;
	}

	public void setTimeslot_id(int timeslot_id) {
		this.timeslot_id = timeslot_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

}
