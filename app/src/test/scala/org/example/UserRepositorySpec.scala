package org.example

import org.example.dtos.User
import org.example.repository.UserRepository
import org.junit.runner.RunWith
import org.scalatest.Outcome
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UserRepositorySpec extends AnyFlatSpec with Matchers {

  "UserRepository" should "return all users" in {
    UserRepository.getAll should have size 3
    UserRepository.getAll should contain allOf (
      User(1, "user1", "user1@example.com"),
      User(2, "user2", "user2@example.com"),
      User(3, "user3", "user3@example.com")
    )
  }

  it should "insert a new user successfully" in {
    val newUser = User(4, "user4", "user4@example.com")
    UserRepository.insert(newUser) should be (true)
    UserRepository.getAll should contain (newUser)
  }

  it should "not insert a user with an existing id" in {
    val duplicateUser = User(1, "duplicateUser", "duplicate@example.com")
    UserRepository.insert(duplicateUser) should be (false)
    UserRepository.getAll should not contain duplicateUser
  }

  it should "delete a user successfully" in {
    val initialSize = UserRepository.getAll.size
    UserRepository.delete(1) should be (true)
    UserRepository.getAll should have size (initialSize - 1)
    UserRepository.getAll should not contain (User(1, "user1", "user1@example.com"))
  }

  it should "return false when trying to delete a non-existing user" in {
    val initialSize = UserRepository.getAll.size
    UserRepository.delete(999) should be (false)
    UserRepository.getAll should have size initialSize
  }

  // Reset the state after each test
  override def withFixture(test: NoArgTest): Outcome = {
    try test()
    finally {
      // Restore the original state of the database
      UserRepository.getAll
        .filterNot(user => List(1, 2, 3).contains(user.id))
        .foreach(user => UserRepository.delete(user.id))

      List(
        User(1, "user1", "user1@example.com"),
        User(2, "user2", "user2@example.com"),
        User(3, "user3", "user3@example.com")
      ).foreach(UserRepository.insert)
    }
  }
}
