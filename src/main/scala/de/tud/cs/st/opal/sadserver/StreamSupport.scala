 
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
import java.nio.channels._
import org.apache.commons.io.IOUtils
 
/**
 * Provides support for receiving and sending InputStream or CharacterStream-based representations of binary- and text-files.
 *
 * @author Mateusz Parzonka
 */
trait StreamSupport {
 
  /**
   * Provides support for sending an inputStream as response body. The encoding is fixed to UTF-8.
   *
   * @param mediaType Specifies the content-type of the response.
   * @param input A tuple of (InputStream, length of InputStream).
   */
  def ByteStream[M <: MediaType.Value](mediaType: M)(input: Option[(java.io.InputStream, Int)]) = RepresentationFactory(mediaType) {
 
    input match {
      case Some((inputStream, streamLength)) => {
        Some(new Representation[mediaType.type] {
 
          def contentType = Some((mediaType, Some(Codec.UTF8)))
 
          def length = streamLength
 
          def write(out: OutputStream) {
            IOUtils.copy(inputStream, out)
          }
        })
      }
      case None => None
    }
  }
 
  /**
   * Provides access to the byte- or character-stream request bodies received by PUT- and POST-methods.
   *
   * @param mediaType Specifies the content-type this processor will handle.
   */
  def InputStream[M <: MediaType.Value](mediaType: M): RequestBodyProcessor = new RequestBodyProcessor(
    mediaType,
    (charset: Option[Charset], in: InputStream) ⇒ {
      _inputStream = in
      _charset = charset
    })
 
  private[this] var _inputStream: java.io.InputStream = _
  private[this] var _charset: Option[Charset] = _
 
  /**
   * Returns the inputStream.
   */
  def inputStream = _inputStream
 
  /**
   * Returns a reader on the inputStream decoding with the charset specified by the content-encoding in the header.
   */
  def reader: Reader = _charset match {
    case Some(charset) => new InputStreamReader(_inputStream, charset)
    case None => new InputStreamReader(_inputStream)
  }
 
  /**
   * Returns the inputStream as an array of bytes.
   */
  lazy val bytes = IOUtils.toByteArray(_inputStream)
 
}