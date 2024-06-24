package org.example.data

import org.example.dtos.User

object UserData {
  var users: List[User] = List(
    User(1, "user1", "user1@example.com"),
    User(2, "user2", "user2@example.com"),
    User(3, "user3", "user3@example.com")
  )
}