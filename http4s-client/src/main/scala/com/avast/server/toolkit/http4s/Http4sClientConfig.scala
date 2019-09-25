package com.avast.server.toolkit.http4s

import java.util.concurrent.TimeUnit

import com.avast.server.toolkit.ssl.SslContextConfig
import org.http4s.BuildInfo
import org.http4s.client.blaze.ParserMode
import org.http4s.client.defaults
import org.http4s.headers.{`User-Agent`, AgentComment, AgentProduct}

import scala.concurrent.duration.{Duration, FiniteDuration}

final case class Http4sClientConfig(
  responseHeaderTimeout: Duration = Duration.Inf,
  idleTimeout: FiniteDuration = Duration(1, TimeUnit.MINUTES),
  requestTimeout: FiniteDuration = defaults.RequestTimeout,
  connectTimeout: FiniteDuration = defaults.ConnectTimeout,
  userAgent: `User-Agent` = `User-Agent`(AgentProduct("http4s-blaze", Some(BuildInfo.version)), List(AgentComment("Server"))),
  maxTotalConnections: Int = 10,
  maxWaitQueueLimit: Int = 256,
  maxConnectionsPerRequestkey: Int = 256,
  sslContext: Option[SslContextConfig] = None,
  checkEndpointIdentification: Boolean = true,
  maxResponseLineSize: Int = 4 * 1024,
  maxHeaderLength: Int = 40 * 1024,
  maxChunkSize: Int = Int.MaxValue,
  chunkBufferMaxSize: Int = 1024 * 1024,
  parserMode: ParserMode = ParserMode.Strict,
  bufferSize: Int = 8192
)
