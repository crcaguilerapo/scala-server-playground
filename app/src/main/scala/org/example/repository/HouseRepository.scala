package org.example.repository

import org.example.data.HouseData.houses
import org.example.dtos.House

object HouseRepository extends Repository[House] {

  def getAll: List[House] = houses

  def insert(house: House): Boolean = {
    if (houses.exists(_.id == house.id)) {
      false
    } else {
      houses = house :: houses
      true
    }
  }

  def delete(id: Int): Boolean = {
    val initialSize = houses.size
    houses = houses.filterNot(_.id == id)
    houses.size < initialSize
  }
}