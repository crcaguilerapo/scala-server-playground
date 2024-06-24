package org.example

import com.twitter.finagle.Http
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import io.finch.syntax._
import org.example.dtos.{House, User}
import org.example.repository.{HouseRepository, UserRepository}

object App extends App {
  // Endpoint to return the list of users
  private val getUserList: Endpoint[List[User]] = get("users") {
    Ok(UserRepository.getAll)
  }

  // Endpoint to insert a new user
  private val insertUser: Endpoint[String] = post("users" :: jsonBody[User]) { user: User =>
    if (UserRepository.insert(user)) {
      Created(s"User ${user.username} added")
    } else {
      Conflict(new Exception(s"User with id ${user.id} already exists"))
    }
  }

  // Endpoint to delete a user by id
  private val deleteUserById: Endpoint[String] = delete("users" :: path[Int]) { id: Int =>
    if (UserRepository.delete(id)) {
      Ok(s"User with id $id deleted")
    } else {
      NotFound(new Exception(s"User with id $id not found"))
    }
  }

  // Endpoint to return the list of houses
  private val getHouseList: Endpoint[List[House]] = get("houses") {
    Ok(HouseRepository.getAll)
  }

  // Endpoint to insert a new house
  private val insertHouse: Endpoint[String] = post("houses" :: jsonBody[House]) { house: House =>
    if (HouseRepository.insert(house)) {
      Created(s"House added with id ${house.id}")
    } else {
      Conflict(new Exception(s"House with id ${house.id} already exists"))
    }
  }

  // Endpoint to delete a house by id
  private val deleteHouseById: Endpoint[String] = delete("houses" :: path[Int]) { id: Int =>
    if (HouseRepository.delete(id)) {
      Ok(s"House with id $id deleted")
    } else {
      NotFound(new Exception(s"House with id $id not found"))
    }
  }

  // Combined endpoint serving users and houses management
  private val api = (getUserList :+: insertUser :+: deleteUserById :+: getHouseList :+: insertHouse :+: deleteHouseById)
    .handle {
      case e: Exception => InternalServerError(e)
    }

  // Serve the combined endpoints on port 8081
  Await.ready(Http.server.serve(":8081", api.toService))
}