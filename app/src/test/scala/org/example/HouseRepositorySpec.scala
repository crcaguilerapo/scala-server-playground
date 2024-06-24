package org.example

import org.example.dtos.House
import org.example.repository.HouseRepository
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HouseRepositorySpec extends AnyFlatSpec with Matchers {

  // Test data
  val house1 = House(1, "123 Street A", "City A")
  val house2 = House(2, "456 Avenue B", "City B")
  val house3 = House(3, "789 Road C", "City C")

  // Test cases for HouseRepository.getAll
  "HouseRepository.getAll" should "return all houses" in {
    HouseRepository.getAll should contain allOf (house1, house2, house3)
  }

  // Test cases for HouseRepository.insert
  it should "insert a new house" in {
    val newHouse = House(4, "999 Lane D", "City D")
    HouseRepository.insert(newHouse) shouldBe true
    HouseRepository.getAll should contain(newHouse)
  }

  it should "not insert a house with existing id" in {
    HouseRepository.insert(house1) shouldBe false
    HouseRepository.getAll.count(_.id == house1.id) shouldBe 1
  }

  // Test cases for HouseRepository.delete
  it should "delete an existing house" in {
    HouseRepository.delete(house1.id) shouldBe true
    HouseRepository.getAll should not contain house1
  }

  it should "return false when deleting a non-existent house" in {
    val nonExistentId = 999
    HouseRepository.delete(nonExistentId) shouldBe false
  }
}
