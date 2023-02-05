package com.brewery.wholesaler.assessment.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BEER")
public class Beer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "ALCOHOL_CONTENT")
	private BigDecimal alcoholContent;

	@Column(name = "NAME", length = 500)
	private String name;

	@Column(name = "PRICE")
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "BREWERY_ID")
	private Brewery brewery;

	@OneToMany(mappedBy = "beer")
	private List<WholesalerStock> wholesalerStockList;

}