package org.example.data

import org.example.dtos.House

object HouseData {
  var houses: List[House] = List(
    House(1, "123 Street A", "City A"),
    House(2, "456 Avenue B", "City B"),
    House(3, "789 Road C", "City C")
  )
}