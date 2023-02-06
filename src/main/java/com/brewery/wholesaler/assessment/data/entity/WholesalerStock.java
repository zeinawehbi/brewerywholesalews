package com.brewery.wholesaler.assessment.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "WHOLESALER_STOCK")
public class WholesalerStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "STOCK")
	private Integer stock;

	@ManyToOne
	@JoinColumn(name = "BEER_ID")
	private Beer beer;

	@ManyToOne
	@JoinColumn(name = "WHOLESALER_ID")
	private Wholesaler wholesaler;
}