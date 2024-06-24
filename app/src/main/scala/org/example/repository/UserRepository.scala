package org.example.repository

import org.example.data.UserData.users
import org.example.dtos.User

object UserRepository extends Repository[User] {

  def getAll: List[User] = users

  def insert(user: User): Boolean = {
    if (users.exists(_.id == user.id)) {
      false
    } else {
      users = user :: users
      true
    }
  }

  def delete(id: Int): Boolean = {
    val initialSize = users.size
    users = users.filterNot(_.id == id)
    users.size < initialSize
  }
}