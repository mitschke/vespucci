/*
   Copyright 2011 Michael Eichberg et al

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.tud.cs.st.vespucci.sadserver

import scala.collection.mutable.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap
import java.util.UUID.randomUUID

import GlobalProperties._

/**
 * Stores temporary descriptions used to implement transactional updates.
 *
 * @author Mateusz Parzonka
 */
object TempDescription {
  import scala.collection.JavaConversions._

  val map: ConcurrentMap[String, (Long, Description)] = new ConcurrentHashMap[String, (Long, Description)]()

  val lifetime = 5 * 60 * 1000 // 5 minutes

  val cleaner = new Thread() {
    override def run() {
      while (true) {
        clean()
        Thread.sleep(60000) // cleaning interval = 1 minute
      }
    }
  };
  cleaner.setDaemon(true)
  cleaner.start()

  def clean(): Unit = {
    for ((id, (time, description)) <- map if (currentTime - time) > lifetime) map.-=(id)
  }

  def currentTime = System.currentTimeMillis

}

trait TempDescription {
  import TempDescription._

  def createTemp(description: Description): String = {
    val id = randomUUID.toString
    map.put(id, (currentTime, description))
    id
  }

  def deleteTemp(id: String): Option[Description] = {
    map.remove(id).map(_._2)
  }

  def getTemp(id: String): Option[Description] = {
    map.get(id).map(_._2)
  }

}

