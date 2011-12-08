
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
package org.dorest.server
package rest

import java.io._
import io.Codec
import scala.xml._
import java.nio.charset.Charset
import org.apache.commons.io.IOUtils

/**
 * Provides support for handling XML representations.
 *
 * @author Mateusz Parzonka
 */
trait BinarySupport {

  def Binary[M <: MediaType.Value](mediaType: M)(input: Option[(java.io.InputStream, Int)]) = RepresentationFactory(mediaType) {

    input match {
      case Some((in, length2)) => {
        Some(new Representation[mediaType.type] {

          def contentType = Some((mediaType, Some(Codec.UTF8)))

          def length = length2

          def write(out: OutputStream) {
            println("Writing stream of length " + length)
            IOUtils.copy(in, out)
          }
        })
      }
      case None => { println("None"); None }
    }
  }

  private[this] var _inputStream: java.io.InputStream = _
  private[this] var _bytes: Array[Byte] = _

  def BinaryIn[M <: MediaType.Value](mediaType: M): RequestBodyProcessor = new RequestBodyProcessor(
    mediaType,
    (charset: Option[Charset], in: InputStream) ⇒ {
      _bytes = IOUtils.toByteArray(in)
    })

  def inputStream = _inputStream

  def bytes = _bytes
}