package controllers

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import config.SomeConfig
import org.cfg4j.provider.ConfigurationProvider

class ConfigurationController @Inject()(
  configurationProvider: ConfigurationProvider
) extends Controller {

  val configuration = configurationProvider.bind("some", classOf[SomeConfig])

  get("/health_check.html") { _: Request =>
    response.ok.plain("ok")
  }

  get("/") { _: Request =>
    response.ok.plain(configuration.source)
  }

}
