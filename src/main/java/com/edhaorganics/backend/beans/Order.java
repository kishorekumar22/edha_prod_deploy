package com.edhaorganics.backend.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "EDHA_ORDER")
@DynamicUpdate
@NamedEntityGraph(name = "order.user", attributeNodes = { @NamedAttributeNode("user") })
public class Order {

	@Id
	@GeneratedValue
	Long id;

	@ManyToOne
	@NotNull
	Customer customer;

	@ManyToOne
	@NotNull
	EdhaUser user;

	@OneToMany(cascade = CascadeType.ALL)
	List<ProductInOrder> products = new ArrayList<>();

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	private OrderStatus status;
	int discount;

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public EdhaUser getUser() {
		return user;
	}

	public void setUser(EdhaUser user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public List<ProductInOrder> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInOrder> products) {
		this.products = products;
	}
}
