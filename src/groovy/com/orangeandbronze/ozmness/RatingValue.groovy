package com.orangeandbronze.ozmness

enum RatingValue {
	NA(0, "N/A"),
	ONE(1, "1"),
	TWO(2, "2"),
	THREE(3, "3")
	
	int value
	String name
	
	RatingValue(int value, String name){
		this.value = value
		this.name = name
	}
	
}

