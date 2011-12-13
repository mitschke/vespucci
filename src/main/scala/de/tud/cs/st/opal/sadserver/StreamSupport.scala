
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
import java.nio.charset.Charset
import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.ReaderInputStream

/**
 * Provides support for receiving and sending InputStream or CharacterStream-based representations of binary- and text-files.
 *
 * @author Mateusz Parzonka
 */
trait StreamSupport {

  protected implicit def inputStreamAndLength2someInputStreamAndLength(input: (java.io.InputStream, Int)) = Some(input)
  protected implicit def readerAndLength2someReaderAndLength(input: (java.io.Reader, Int)) = Some(input)

  protected val defaultCharset = Codec.UTF8
  
  /**
   * Provides support for sending an inputStream as response body. The encoding is set to UTF-8.
   *
   * @param mediaType
   * 	Specifies the content-type of the response.
   * @param input
   * 	A tuple of (InputStream, length of InputStream).
   */
  def ByteStream[M <: MediaType.Value](mediaType: M)(input: => Option[(java.io.InputStream, Int)]) = RepresentationFactory(mediaType) {

    input match {
      case Some((inputStream, streamLength)) => {
        Some(new Representation[mediaType.type] {

          def contentType = Some((mediaType, Some(defaultCharset)))

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
   * Provides support for sending an UTF-8 encoded characterStream as response body.
   *
   * @param mediaType 
   * 	Specifies the content-type of the response.
   * @param input 
   * 	A tuple of (java.io.Reader, length of the underlying characterStream).
   */
  def CharacterStream[M <: MediaType.Value](mediaType: M)(input: => Option[(java.io.Reader, Int)]) = RepresentationFactory(mediaType) {

    input match {
      case Some((reader, streamLength)) => {
        Some(new Representation[mediaType.type] {

          def contentType = Some((mediaType, Some(defaultCharset)))

          def length = streamLength

          def write(out: OutputStream) {
            IOUtils.copy(reader, out, defaultCharset.toString())
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
   * Returns a reader on the inputStream decoding using the charset specified by the content-encoding in the request header.
   */
  def reader: Reader = _charset match {
    case Some(charset) => new InputStreamReader(_inputStream, charset)
    case None => new InputStreamReader(_inputStream)
  }

  /**
   * Returns the inputStream as an array of bytes.
   */
  def bytes = IOUtils.toByteArray(_inputStream)

  /**
   * Returns an inputStream of UTF-8 encoded characters. When the underlying inputStream from the response has a different encoding
   * (specified in the content-encoding-header of the request) it is recoded to UTF-8.
   */
  def encodedInputStream = _charset match {
    case Some(charset) if charset == defaultCharset => _inputStream
    case _ => new ReaderInputStream(reader, defaultCharset)
  }

}