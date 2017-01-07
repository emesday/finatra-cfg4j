import com.google.inject.Module
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import controllers.ConfigurationController
import modules.ConfigurationModule

object Server extends HttpServer {

  override val name = "config manager"

  override val modules: Seq[Module] = Seq(ConfigurationModule)

  override def configureHttp(router: HttpRouter) {
    router
      .filter[CommonFilters]
      .add[ConfigurationController]
  }

  logger.info("started")
}
