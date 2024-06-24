package org.example.repository

trait Repository[T] {
  def getAll: List[T]
  def insert(t: T): Boolean
  def delete(id: Int): Boolean
}